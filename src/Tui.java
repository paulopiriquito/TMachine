/**
 * Created by a3908 on 11/03/2016.
 */

public class Tui {
    protected static boolean roundTrip = false;

    protected static int readStationNumber(int current, int max, char key) { //TODO refazer
        if (current == Stations.getHome().getIndex())
            current = 0;
        switch (key) {
            case 'O': return current;
            case 'K': return current;
            case 'B':
                if (current == max)
                    current = 1;
                else
                    ++current;
                break;
            case 'C':
                if (current == 1)
                    current = max;
                else
                    --current;
                break;
            default:
                if (Character.isDigit(key)) {
                    current *= 10;
                    current += Character.getNumericValue(key)-1;
                    current %= 100;
                    if (current > max)
                        current = 1;
                }
                return current;
        }
        return current;
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

    protected static void writeFloor(String floor){ //TODO review
        Lcd.setCursor(Lcd.LINES,0);
        int position = (Lcd.COLS/2) - (floor.length()/2);
        int i = 1;
        for (; i < position; i++) //Fill before
            Lcd.write(' ');
        for (int j = 0; j < floor.length(); ++i, ++j) //Write text
            Lcd.write(floor.charAt(j));
        for (; i < Lcd.COLS; ++i) //Fill after
            Lcd.write(' ');
    }

    protected static void writeSign(boolean roundTrip){
        Lcd.setCursor(Lcd.LINES,1);
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

        Lcd.setCursor(2,Lcd.COLS-3);
        Lcd.write(String.format("%d$%02d", eur, cents));
        Lcd.setCursor(Lcd.LINES,Lcd.COLS-2);
    }

    protected static void writePayment(int coins){
        Lcd.setCursor(Lcd.LINES,1);
        Lcd.write("Pagamento");
        writePrice(coins);
    }

}






