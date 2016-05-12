/**
 * Created by a3908 on 11/03/2016.
 */
public class Tui {


    public int getInt(int current, int max){
        char key = Kbd.getKey();
        if(key != 'C' && key != 'D')
            current*= 10;
            int keyn = key;
            current+= keyn;
            current %=100;
            if(current > max)
                current = 1;

        else 
			if(key == 'C'){
            if(current == max) current = 1;
            else --current;
        }
        else {
            if (current == 1) current = max;
            else ++current;
        }
        return current;
    }


}
