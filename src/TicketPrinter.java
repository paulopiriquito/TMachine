/**
 * Created by a3908 on 11/03/2016.
 */
public class TicketPrinter {//TODO
    /**
     * Inicia a classe, estabelecendo os valores iniciais.
     */
    public static void init(){
        SerialEmitter.init();
    }

    /**
     * Envia comando para imprimir e dispensar um bilhete para a estação toId, roundTrip a true se
     * o bilhete for de ida/volta
     */
    public static void print(int toId, boolean roundTrip){
        toId <<= 1;
        if (roundTrip)
            toId |= 1;
        SerialEmitter.send(false, toId);
    }

    public static boolean busy(){
        return Kit.isBit(Pin.BUSY);
    }
}
