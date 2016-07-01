/**
 * Created by a3908 on 11/03/2016.
 */

public class Tui {
    //protected static String localStation = Stations.getLocalStation();
    protected static boolean doubleTicket = false;

    protected static int readStationNumber(int current, int max, char key) {
        switch (key) {
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
                    current += Character.getNumericValue(key);
                    current %= 100;
                    if (current > max)
                        current = 1;
                }
                return current;
        }
        writeStationNumber(current);
        return current;
    }

    protected boolean readDoubleTicket(char key){
        if(key == 'O') {
            doubleTicket = !doubleTicket;
            writeSign(doubleTicket);
        }
        return doubleTicket;
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
        for (; i < Lcd.COLS; ++i) //Fill after
            Lcd.write(' ');
    }

    protected static void writeSign(boolean returning){
        Lcd.setCursor(Lcd.LINES,1);
        if (returning)
            Lcd.write("<->");
        else
            Lcd.write(" ->");
    }

    protected static void writeStationNumber(int number){
        Lcd.setCursor(Lcd.LINES,(Lcd.COLS/2)-1);
        Lcd.write(String.format("%02d", number));
        Lcd.setCursor(Lcd.LINES,(Lcd.COLS/2));
    }

    protected static void writePrice(int eur, int cents){
        Lcd.setCursor(2,Lcd.COLS-3);
        Lcd.write(String.format("%d$%02d", eur, cents));
        Lcd.setCursor(Lcd.LINES,Lcd.COLS-2);
    }

    protected static void writePayment(int eur, int cents){
        Lcd.setCursor(Lcd.LINES,1);
        Lcd.write("Pagamento");
        writePrice(eur, cents);
    }
}






