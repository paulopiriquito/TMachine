/**
 * Created by a3908 on 11/03/2016.
 */

public class Tui {
    protected static boolean roundTrip = false;
    protected static int lastDigit = 0;

    protected static int getInt(int lastID, int max, char key) {
        switch (key) {
            case 'C':
                if (lastID+1 == max)
                    lastID = 1;
                else
                    ++lastID;
                return lastID;
            case 'B':
                if (lastID-1 == 1)
                    lastID = max;
                else
                    --lastID;
                return lastID;
            default:
                if (Character.isDigit(key)) {
                    int value = Character.getNumericValue(key);
                    if(lastDigit*10 + value > max || lastDigit == 0) {
                        lastDigit = value;
                        return value;
                    }
                    lastID = lastDigit*10 + value;
                    lastDigit = value;
                }
        }
        return lastID;
    }

    protected static int readStationID(char key, Station current){
        int id = Stations.getHome().getIndex();
        if(key == 'O' || key == 'K' || key == 'D') {
            if (Stations.isHome(current)) {
                return Stations.getStation(current.getIndex() + 1).getIndex();
            }
        }
        else{
            if(!readAbort(key)){
                id = getInt(current.getIndex(), FileAccess.maxStations, key);
            }
        }
        if (id <= 1)
            id = 0;
        if (id == Stations.getHome().getIndex())
            id++;
        return id;
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
        Lcd.setCursor(Lcd.LINES,1);
        Lcd.write("Pagamento");
        writePrice(coins);
    }

}






