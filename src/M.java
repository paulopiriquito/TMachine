/**
 * Created by a3908 on 11/03/2016.
 */
public class M extends Tui{

    public static boolean shutdown = false;
    private static Station selected;
    private static char lastCtrl;

    public static boolean onManteinance(){
        return Kit.isBit(Pin.M);
    }

    public static boolean runManteinance(){
        selected = Stations.getStation(Stations.getHome().getIndex()+1);
        while (onManteinance() && !shutdown){
            showDestination(selected);
            do {
                lastCtrl = Kbd.getKey();
                if (!onManteinance())
                    break;
            }
            while (lastCtrl == Kbd.NONE);
            functionSelect(lastCtrl);
        }
        return false;
    }

    private static void functionSelect(char ctrl){
        switch (ctrl){
            case 'A': listFunction();break;
            case 'D': shutdownFunction();break;
            case 'K': printFunction();break;
            default:
                selected = readStation(ctrl, selected, FileAccess.maxStations);
                readRoundTrip(ctrl);
                break;
        }
    }

    private static void listFunction(){
        int key = Kbd.NONE;
        boolean coinsNotTickets = true;
        while (key != 'A'){
            if (coinsNotTickets)
                writeCounter("Moedas",CoinDeposit.getValue());
            else
                writeCounter("Bilhetes", App.getTicketsCounter());
            key = Kbd.waitKey(5000);
            if (key == Kbd.NONE)
                return;
            if (key == 'K'){
                writeHeader(" ");
                writeFloor("Iniciar todos?");
                key = Kbd.waitKey(5000);
                if (key == 'K')
                    App.resetCounters();
                return;
            }
            if (key == 'C' || key == 'B')
                coinsNotTickets = !coinsNotTickets;
        }
    }

    private static void shutdownFunction(){
        writeHeader(" ");
        writeFloor("Desligar?");
        int key = Kbd.waitKey(5000);
        if (key == 'K')
            shutdown = true;
    }

    public static void printFunction(){
        writeHeader(selected.getName());
        writeFloor("Retire o bilhete");
        TicketPrinter.print(selected.getIndex(), roundTrip);
        Kit.sleep(2000);
    }
}

