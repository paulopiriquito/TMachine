/**
 * Created by a3908 on 11/03/2016.
 */
public class CoinAcceptor {
    public static int acceptorCount = 0;

    //Inicia a classe
    public static void init(){
        Kit.clrBits(Pin.ACCEPT);
        Kit.clrBits(Pin.RETURN);
        Kit.clrBits(Pin.COLLECT);
    }
    //Retorna !=0 se foi introduzida uma nova moeda, restantes valores identificam o tipo
    public static int hasCoin(){
        if(Kit.isBit(Pin.COIN))
            return 1;
        return 0;
    }
    //Informa o moedeiro que a moeda foi contabilizada.
    public static void acceptCoin(){
        Kit.sleep(1000);
        Kit.setBits(Pin.ACCEPT);
        Kit.sleep(1000);
        Kit.clrBits(Pin.ACCEPT);
        ++acceptorCount;
    }
    //Devolve as moedas que estão no moedeiro.
    public static void ejectCoins(){
        Kit.setBits(Pin.RETURN);
        Kit.sleep(1000);
        Kit.clrBits(Pin.RETURN);
        acceptorCount = 0;
    }
    //Recolhe as moedas que estão no moedeiro.
    public static void collectCoins(){
        Kit.setBits(Pin.COLLECT);
        Kit.sleep(1000);
        Kit.clrBits(Pin.COLLECT);
        acceptorCount = 0;
    }
}
