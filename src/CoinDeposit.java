/**
 * Created by a3908 on 11/03/2016.
 */
public class CoinDeposit {
    private static int coinCounter = 0;

    public static void reset(){
        coinCounter = 0;
    }

    public static int getValue(){
        return coinCounter;
    }

    public static void addCoins(int c){
        coinCounter += c;
    }

}
