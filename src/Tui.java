/**
 * Created by a3908 on 11/03/2016.
 */

public class Tui {
    String localStation = Stations.stationName(FileAccess.local);
    String welcome = localStation + "bem vindo";

    public int getInt(int current, int max) {
        char key = Kbd.getKey();
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
            //  case 'A':
            //    Lcd.write(welcome);
            //    break;
            //  case 'O':
            //     Lcd.write(Stations.stationName(current) + "/n ->   " + current + Stations.stations.get(current).getReturnPrice() );
            //     break;
            // case 'k':

            default:
                if (Character.isDigit(key)) {
                    current *= 10;
                    current += Character.getNumericValue(key);
                    current %= 100;
                    if (current > max)
                        current = 1;
                }
                break;
        }
        return current;
         }
    }






