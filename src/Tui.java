/**
 * Created by a3908 on 11/03/2016.

public class Tui {

    public int getInt(int current, int max){
        char key = Kbd.getKey();
        switch (key){
            case '?':
                if(current == max)
                    current = 1;
                else
                    --current;
                break;
            case '?':
                if (current == 1)
                    current = max;
                else
                    ++current;
                break;
            default:
                if (Character.isDigit(key)){
                    current*= 10;
                    current+= Character.getNumericValue(key);
                    current %=100;
                    if(current > max)
                        current = 1;
                }
                break;
        }
        return current;
    }
}
 */