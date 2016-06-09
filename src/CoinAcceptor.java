/**
 * Created by a3908 on 11/03/2016.
 */
public class CoinAcceptor {
    //Inicia a classe
    public static void init(){
        Kit.clrBits(Pin.ACCEPT);
        Kit.clrBits(Pin.RETURN);
        Kit.clrBits(Pin.COLLECT);
        CoinDeposit.reset();
    }
    //Retorna !=0 se foi introduzida uma nova moeda, restantes valores identificam o tipo
    public static int hasCoin(){
        if(Kit.isBit(Pin.COIN))
            return 1;
        return 0;
    }
    //Informa o moedeiro que a moeda foi contabilizada.
    public static void acceptCoin(){
        Kit.setBits(Pin.ACCEPT);
    }
    //Devolve as moedas que estão no moedeiro.
    public static void ejectCoins(){
        Kit.setBits(Pin.RETURN);
        Kit.sleep(1000);
        Kit.clrBits(Pin.RETURN);
    }
    //Recolhe as moedas que estão no moedeiro.
    public static void collectCoins(){
        Kit.setBits(Pin.COLLECT);
        Kit.sleep(1000);
        Kit.clrBits(Pin.COLLECT);
    }
}
