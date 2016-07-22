/**
 * Aplicação principal
 * Extende a classe Tui para interface hardware-aplicação
 */
public class App extends Tui{
    private static int ticketsSold = 0; //Contador de bilhetes emitidos pela aplicação
    private static int maxStations; //Quantidade de estações presente em Stations
    private static boolean maintenance = false; //true se o modo de manutenção for activado
    private static char lastCtrl = Kbd.NONE; //Último comando recebido do utilizador
    private static Station selectedStation; //Estação actualmente seleccionada na aplicação

    /**
     * Método de entrada do programa
     * Faz a inicialização dos contadores, carrega a estações do ficheiro, e prepara o hardware
     * Em seguida inicia a rotina da aplicação e realiza o escape da aplicação quando for desligada
     * @param args
     */
    public static void main(String[] args) {
        CoinDeposit.addCoins(FileAccess.loadCoins(FileAccess.REGISTER));
        ticketsSold = FileAccess.loadTickets(FileAccess.REGISTER);
        FileAccess.loadStations(FileAccess.STATIONS);
        maxStations = FileAccess.maxStations;

        Kit.init();
        SerialEmitter.init();
        Kbd.init();
        Lcd.init();
        CoinAcceptor.init();

        run();
        writeHeader(" ");
        writeFloor("A desligar...");
        shutdown();
    }

    /**
     * Rotina da aplicação
     * Enquanto não for encerrada, recebe um comando do utilizador (manutenção ou teclado)
     * e toma a acção correspondente
     */
    private static void run(){
        while (!M.shutdown) {
            lastCtrl = welcomeMsg();
            lastDigit = 0;
            if (maintenance)
                maintenance = M.runMaintenance();
            else {
                roundTrip = false;
                ticketSelect(lastCtrl);
            }
        }
    }

    /**
     * Método de seleçao de bilhete
     * Recebe o comando do utilizador, atualiza a estaçao seleccionada consoante, mostra a estaçao no lcd, ou
     * prossegue para a emissão de um bilhete para a estação seleccionada.
     * Durante 5 segundo o utilizador pode navegar nas estações disponíveis e escolher uma ou retornar ao ecran
     * de boas vindas
     * @param ctrl Comando do utilizador
     */
    private static void ticketSelect(char ctrl){
        readRoundTrip(ctrl);
        selectedStation = readStation(ctrl, Stations.getStation(Stations.getHome().getIndex()), maxStations);
        if (Stations.isHome(selectedStation))
            return;
        showDestination(selectedStation);
        timeout(5);
        while (System.currentTimeMillis() < timeout){
            lastCtrl = Kbd.getKey();
            if (lastCtrl != Kbd.NONE ) {
                if(readAbort(lastCtrl))
                    return;
                if (lastCtrl != 'K') {
                    readRoundTrip(lastCtrl);
                    selectedStation = readStation(lastCtrl, selectedStation, maxStations);
                    if (Stations.isHome(selectedStation)) {
                        writeHeader(Stations.getHome().getName());
                        writeFloor("bem vindo");
                        hideCursor();
                    }
                    else
                        showDestination(selectedStation);
                    timeout(5);
                }
                else {
                    if (!Stations.isHome(selectedStation)) {
                        emitTicket();
                        return;
                    }
                }
            }
        }
    }

    /**
     * Método responsável pela emissao do bilhete actualmente selecionado
     * Mostra a mensagem de pagamento e pede a recolha das moedas, imprimindo o bilhete quando o pagamento
     * for efetuado, ou em caso de abort ou timeout, devolve as moedas e apresenta a mensagem de anulamento
     */
    private static void emitTicket(){
        boolean onTimeout = false, aborted = false;
        int price = selectedStation.getPrice();
        if (roundTrip)
            price *= 2;
        writePayment(price - CoinAcceptor.acceptorCount);
        timeout(15);//Define o tempo para realizar o pagamento para 15 segundos
        while (CoinAcceptor.acceptorCount < price && !aborted && !onTimeout){
            if (CoinAcceptor.hasCoin() != 0){
                CoinAcceptor.acceptCoin();
                writePayment(price - CoinAcceptor.acceptorCount);
                timeout(15); //O utilizador volta a ter 15 segundos disponíveis para inserir a próxima moeda
            }
            lastCtrl = Kbd.getKey();
            aborted = readAbort(lastCtrl);
            if (System.currentTimeMillis() > timeout)
                onTimeout = true;
        }
        if (aborted || onTimeout) {
            writeFloor("Compra anulada");
            CoinAcceptor.ejectCoins();
            Kit.sleep(2000);//Fixa a mensagem durante pelo menos 2 segundos
        }
        else {
            CoinDeposit.addCoins(CoinAcceptor.acceptorCount);
            CoinAcceptor.collectCoins();
            ++ticketsSold;
            writeFloor("Retire o bilhete");
            TicketPrinter.print(selectedStation.getIndex()+1,roundTrip);
            writeHeader(" ");
            writeFloor("Obrigado");
            Kit.sleep(2000);//Fixa a mensagem durante pelo menos 2 segundos
        }
    }

    /**
     * Ecran de boas vindas
     * Neste método é apresentada a mensagem de boas vindas e a aplicaçao fica a espera de um
     * comando no teclado ou do pedido para entrar em manutenção
     * @return comando do teclado detectado
     */
    private static char welcomeMsg(){
        char ctrl = Kbd.NONE;
        writeHeader(Stations.getHome().getName());
        writeFloor("bem vindo");
        hideCursor();
        while (ctrl == Kbd.NONE || readAbort(ctrl)){
            if (M.onMaintenance()) {
                maintenance = true;
                return ctrl;
            }
            ctrl = Kbd.getKey();
        }
        return ctrl;
    }

    /**
     * Faz reset aos contadores de moedas e de bilhetes vendidos
     */
    public static void resetCounters(){
        ticketsSold = 0;
        CoinDeposit.reset();
    }

    /**
     * @return quantidade de bilhetes vendidos
     */
    public static int getTicketsCounter(){
        return ticketsSold;
    }

    /**
     * Desliga a aplicação
     * Guarda os contadores no ficheiro de registo e encerra o processo da aplicação
     */
    private static void shutdown(){
        FileAccess.saveReg(FileAccess.REGISTER,ticketsSold,CoinDeposit.getValue());
        Kit.sleep(3000);
        System.exit(0);
    }
}
