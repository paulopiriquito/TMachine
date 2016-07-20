/**
 * Created by a3908 on 11/03/2016.
 */
public class CoinDeposit {
    private static int coinCounter = 0;
    private static final double CTYPE = 0.5;

    public static void reset(){
        coinCounter = 0;
    }

    public static double getValue(){
        return coinCounter * CTYPE;
    }

    public static void addCoins(int c){
        coinCounter += c;
    }

    public static void removeCoins(int c){
        coinCounter -= c;
    }

}
