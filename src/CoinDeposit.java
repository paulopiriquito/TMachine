/**
 * Created by a3908 on 11/03/2016.
 */
public class CoinDeposit {
    private static int coinCounter;
    private static final double CTYPE = 0.5;

    public static void reset(){
        coinCounter = 0;
    }

    public static double getValue(){
        return coinCounter * CTYPE;
    }

    public static void addCoin(){
        ++coinCounter;
    }

    public static void removeCoin(){
        --coinCounter;
    }
}
