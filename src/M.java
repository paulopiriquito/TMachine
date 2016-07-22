public class M extends Tui{

    public static boolean shutdown = false; //Flag de shutdown
    private static Station selected; //Estação seleccionada
    private static char lastCtrl; //Último controlo recebido do utilizador

    /**
     * Retorna o estado da manutenção
     * @return
     */
    public static boolean onMaintenance(){
        return Kit.isBit(Pin.M);
    }

    /**
     * Executa a rotina de manutenção
     * @return Estado da manutenção após executar a rotina
     */
    public static boolean runMaintenance(){
        selected = Stations.getStation(Stations.getHome().getIndex()+1);
        while (onMaintenance() && !shutdown){ //Enquanto estiver em manutenção e não for pedido para desligar
            showDestination(selected);//Mostra a estação seleccionada actualmente
            do {
                lastCtrl = Kbd.getKey();
                if (!onMaintenance())
                    break;
            }
            while (lastCtrl == Kbd.NONE);
            functionSelect(lastCtrl); //Executa a função de manutenção correspondente ao controlo enviado
        }
        return false;
    }

    /**
     * Redirecciona a rotina de manutenção para a função requisitada
     * @param ctrl
     */
    private static void functionSelect(char ctrl){
        switch (ctrl){
            case 'A': listFunction();break; //Função listagem de contadores
            case 'D': shutdownFunction();break; //Função desligar
            case 'K': printFunction();break; //Função imprimir bilhete seleccionado
            default: //Navegar nas estações destino actualizando a estação seleccionada
                selected = readStation(ctrl, selected, FileAccess.maxStations);
                readRoundTrip(ctrl); //Actualiza opção ida/ida-volta
                break;
        }
    }

    /**
     * Apresenta os contadores de moedas no cofre e de bilhetes emitidos
     * Permite reiniciar os contadores
     */
    private static void listFunction(){
        int key = Kbd.NONE;
        boolean coinsNotTickets = true;
        while (key != 'A'){//Enquanto nao for pedido o abort
            if (coinsNotTickets)//Altera entre mostrar um ou outro contador dependendo da flag
                writeCounter("Moedas",CoinDeposit.getValue());
            else
                writeCounter("Bilhetes", App.getTicketsCounter());
            key = Kbd.waitKey(5000); //É permitido um tempo de resposta de 5s antes de retornar ã rotina principal
            if (key == Kbd.NONE)
                return;
            if (key == 'K'){// Se for premida a tecla OK duas vezes, há reset dos contadores
                writeHeader(" ");
                writeFloor("Iniciar todos?");
                key = Kbd.waitKey(5000);
                if (key == 'K')
                    App.resetCounters();
                return;
            }
            if (key == 'C' || key == 'B')// Altera a flag do contador a mostrar navegando nas setas de selecção
                coinsNotTickets = !coinsNotTickets;
        }
    }

    /**
     * Função encerrar
     * Pede a confirmação para desligar a máquina, e actualiza a flag de shutdown de acordo com a resposta
     */
    private static void shutdownFunction(){
        writeHeader(" ");
        writeFloor("Desligar?");
        int key = Kbd.waitKey(5000);
        if (key == 'K')
            shutdown = true;
    }

    /**
     * Imprime um bilhete para a estação seleccionada sem efectuar pagamento e sem contar como bilhete emitido
     */
    public static void printFunction(){
        writeHeader(selected.getName());
        writeFloor("Retire o bilhete");
        TicketPrinter.print(selected.getIndex(), roundTrip);
        Kit.sleep(2000);
    }
}

