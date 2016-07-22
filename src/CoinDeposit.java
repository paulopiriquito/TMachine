/**
 * Guarda e actualiza a contagem de moedas no cofre
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
