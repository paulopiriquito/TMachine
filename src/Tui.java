/**
 * Created by a39081 on 11/03/2016.
 */

public class Tui {
    protected static boolean roundTrip = false;
    protected static int lastDigit = 0;
    protected static double timeout;

    protected static int getInt(char key, int max) {
        int id = 0;
        if (Character.isDigit(key)) {
            int value = Character.getNumericValue(key);
            if(value == 0 && lastDigit ==0){
                lastDigit = value;
                return 1;
            }
            if(lastDigit*10 + value >= max || lastDigit == 0) {
                lastDigit = value;
                return value;
            }
            else {
                id = lastDigit*10 + value;
                lastDigit = value;
            }
        }
        return id;
    }

    protected static Station readStation(char key, Station selected, int maxStation){
        switch (key){
            case 'O': lastDigit = 0; break;
            case 'C': lastDigit = 0; selected = getUpStation(selected, maxStation); break;
            case 'B': lastDigit = 0; selected = getDownStation(selected, maxStation); break;
            default:
                selected = Stations.getStationById(getInt(key, maxStation));break;
        }
        return selected;
    }

    private static Station getUpStation(Station selected, int maxStation){
        if (selected.getIndex()+1 < maxStation)
            return Stations.getStation(selected.getIndex()+1);
        else {
            return Stations.getStation(0);
        }
    }

    private static Station getDownStation(Station selected, int maxStation){
        if (selected.getIndex()-1 < 0)
            return Stations.getStation(maxStation-1);
        else {
            return Stations.getStation(selected.getIndex()-1);
        }
    }

    protected static void readRoundTrip(char key){
        if(key == 'O') {
            roundTrip = !roundTrip;
        }
    }

    protected static boolean readAbort(char key){
        return key == 'A';
    }

    protected static void hideCursor(){
        Lcd.setCursor(1,17);
    }

    protected static void writeHeader(String header){
        Lcd.returnHome();
        int position = (Lcd.COLS/2) - (header.length()/2);
        int i = 1;
        for (; i < position; i++) //Fill before
            Lcd.write(' ');
        for (int j = 0; j < header.length(); ++i, ++j) //Write text
            Lcd.write(header.charAt(j));
        for (; i < Lcd.COLS; ++i) //Fill after
            Lcd.write(' ');
    }

    protected static void writeFloor(String floor){
        Lcd.setCursor(Lcd.LINES,0);
        int position = (Lcd.COLS/2) - (floor.length()/2);
        int i = 1;
        for (; i < position; i++) //Fill before
            Lcd.write(' ');
        for (int j = 0; j < floor.length(); ++i, ++j) //Write text
            Lcd.write(floor.charAt(j));
        for (; i < Lcd.COLS+1; ++i) //Fill after
            Lcd.write(' ');
    }

    protected static void writeSign(boolean roundTrip){
        Lcd.setCursor(Lcd.LINES,0);
        if (roundTrip)
            Lcd.write("<->");
        else
            Lcd.write(" ->");
    }

    protected static void writeStationNumber(int number){
        Lcd.setCursor(Lcd.LINES,(Lcd.COLS/2)-1);
        Lcd.write(String.format("%02d", number));
        Lcd.setCursor(Lcd.LINES,(Lcd.COLS/2));
    }

    protected static void writePrice(int coins){
        coins *= 50;
        int eur = coins / 100;
        int cents = 0;
        if (coins%100 != 0)
            cents = 50;

        Lcd.setCursor(2,Lcd.COLS-4);
        Lcd.write(String.format("%d$%02d", eur, cents));
        Lcd.setCursor(Lcd.LINES,Lcd.COLS-3);
    }

    protected static void writePayment(int coins){
        Lcd.setCursor(Lcd.LINES,0);
        Lcd.write("Pagamento");
        writePrice(coins);
    }

    protected static void writeCounter(String name, int value){
        writeHeader("Contadores");
        writeFloor(" ");
        Lcd.setCursor(Lcd.LINES,0);
        Lcd.write(name + "=" + value);
        hideCursor();
    }

    protected static void showDestination(Station selected){
        writeHeader(selected.getName());
        writeFloor(" ");
        writeSign(roundTrip);
        int price = selected.getPrice();
        if (roundTrip)
            price *=2;
        writePrice(price);
        writeStationNumber(selected.getIndex()+1);
    }

    protected static void timeout(int seconds){
        timeout = System.currentTimeMillis() + (seconds*1000);
    }

}






