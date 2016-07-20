/**
 * Created by a3908 on 11/03/2016.
 */
public class M extends Tui{ //TODO modo de manutenção

    public static boolean readManteinance(){
        return Kit.isBit(Pin.M);
    }
}

