import java.io.FileWriter;

/**
 * Created by a3908 on 11/03/2016.
 */
public class App extends Tui{


    private static int ticketsSold = 0;

    private static double endTime;
    private static boolean manteinance = false;
    private static char lastCtrl = Kbd.NONE;
    private static Station selected;

    public static void main(String[] args) {
        CoinDeposit.addCoins(FileAccess.loadCoins(FileAccess.REGISTER));
        ticketsSold = FileAccess.loadTickets(FileAccess.REGISTER);
        FileAccess.loadStations(FileAccess.STATIONS);

        Kit.init();
        SerialEmitter.init();
        Kbd.init();
        Lcd.init();
        CoinAcceptor.init();

        run();
    }

    private static void run(){
        while (lastCtrl != 'S') {
            lastCtrl = welcomeMsg();
            if (manteinance) {

            }
            ticketSelect(lastCtrl);
        }
    }

    private static void ticketSelect(char ctrl){
        readRoundTrip(ctrl);
        Station previous = Stations.getHome();

        selected = Stations.getStation(readStationNumber(previous.getIndex(), FileAccess.maxStations, ctrl));
        if (Stations.isHome(selected.getIndex()))
            selected = Stations.getStation(selected.getIndex()+1);
        previous = selected;
        showDestination();
        timeout(5);
        while (System.currentTimeMillis() < endTime){
            lastCtrl = Kbd.getKey();
            if(readAbort(lastCtrl))
                return;
            if (lastCtrl != Kbd.NONE && lastCtrl != 'K'){
                readRoundTrip(lastCtrl);
                selected =  Stations.getStation(readStationNumber(previous.getIndex(), FileAccess.maxStations, ctrl));
                showDestination();
                timeout(5);
            }
            if (lastCtrl == 'K') {
                emitTicket();
                return;
            }
        }
    }

    private static void emitTicket(){
        boolean timeout = false, aborted = false;
        int price = selected.getPrice();
        if (roundTrip)
            price *= 2;
        timeout(15);
        writePayment(price - CoinAcceptor.acceptorCount);
        while (CoinAcceptor.acceptorCount < price && !aborted && !timeout){
            if (CoinAcceptor.hasCoin() != 0){
                CoinAcceptor.acceptCoin();
                writePayment(price - CoinAcceptor.acceptorCount);
                timeout(15);
            }
            lastCtrl = Kbd.getKey();
            aborted = readAbort(lastCtrl);
            if (System.currentTimeMillis() > endTime)
                timeout = true;
        }
        if (aborted || timeout) {
            CoinAcceptor.ejectCoins();
            writeFloor("Compra anulada");
            Kit.sleep(3000);
        }
        else {
            CoinDeposit.addCoins(CoinAcceptor.acceptorCount);
            CoinAcceptor.collectCoins();
            ++ticketsSold;
            //TicketPrinter.print(selected.getIndex(),roundTrip);
            writeFloor("Retire o bilhete");
        }
    }

    private static void timeout(int seconds){
        endTime = System.currentTimeMillis() + (seconds*1000);
    }

    private static char welcomeMsg(){
        char ctrl = Kbd.NONE;
        writeHeader(Stations.getHome().getName());
        writeFloor("bem vindo");
        hideCursor();
        while (ctrl == Kbd.NONE || readAbort(ctrl)){
            if (M.readManteinance()) {
                manteinance = true;
                return ctrl;
            }
            ctrl = Kbd.getKey();
        }
        return ctrl;
    }

    private static void showDestination(){
        writeHeader(selected.getName());
        writeFloor(" ");
        writeSign(roundTrip);
        writePrice(selected.getPrice());
        writeStationNumber(selected.getIndex());
    }
}
