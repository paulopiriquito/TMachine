/**
 * Created by a39081 on 11/03/2016.
 */
public class App extends Tui{
    private static int ticketsSold = 0, maxStations;
    private static boolean manteinance = false;
    private static char lastCtrl = Kbd.NONE;
    private static Station selectedStation;

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

    private static void run(){
        while (!M.shutdown) {
            lastCtrl = welcomeMsg();
            lastDigit = 0;
            if (manteinance)
                manteinance = M.runManteinance();
            else {
                roundTrip = false;
                ticketSelect(lastCtrl);
            }
        }
    }

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

    private static void emitTicket(){
        boolean onTimeout = false, aborted = false;
        int price = selectedStation.getPrice();
        if (roundTrip)
            price *= 2;
        writePayment(price - CoinAcceptor.acceptorCount);
        timeout(15);
        while (CoinAcceptor.acceptorCount < price && !aborted && !onTimeout){
            if (CoinAcceptor.hasCoin() != 0){
                CoinAcceptor.acceptCoin();
                writePayment(price - CoinAcceptor.acceptorCount);
                timeout(15);
            }
            lastCtrl = Kbd.getKey();
            aborted = readAbort(lastCtrl);
            if (System.currentTimeMillis() > timeout)
                onTimeout = true;
        }
        if (aborted || onTimeout) {
            writeFloor("Compra anulada");
            CoinAcceptor.ejectCoins();
            Kit.sleep(3000);
        }
        else {
            CoinDeposit.addCoins(CoinAcceptor.acceptorCount);
            CoinAcceptor.collectCoins();
            ++ticketsSold;
            writeFloor("Retire o bilhete");
            TicketPrinter.print(selectedStation.getIndex()+1,roundTrip);
            while (SerialEmitter.isBusy())
                ;
            writeHeader(" ");
            writeFloor("Obrigado");
            Kit.sleep(2000);
        }
    }


    private static char welcomeMsg(){
        char ctrl = Kbd.NONE;
        writeHeader(Stations.getHome().getName());
        writeFloor("bem vindo");
        hideCursor();
        while (ctrl == Kbd.NONE || readAbort(ctrl)){
            if (M.onManteinance()) {
                manteinance = true;
                return ctrl;
            }
            ctrl = Kbd.getKey();
        }
        return ctrl;
    }

    public static void resetCounters(){
        ticketsSold = 0;
        CoinDeposit.reset();
    }

    public static int getTicketsCounter(){
        return ticketsSold;
    }

    private static void shutdown(){
        FileAccess.saveReg(FileAccess.REGISTER,ticketsSold,CoinDeposit.getValue());
        Kit.sleep(3000);
        System.exit(0);
    }
}
