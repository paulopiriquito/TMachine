public class TicketPrinter {

    /**
     * Inicia a classe, estabelecendo os valores iniciais.
     */
    public static void init(){
        SerialEmitter.init();
    }

    /**
     * Envia comando para imprimir e dispensar um bilhete para a estação toId, roundTrip a true se
     * o bilhete for de ida/volta, espera que o bilhete seja retirado da impressora antes de avançar
     */
    public static void print(int toId, boolean roundTrip){
        toId <<= 1;
        if (roundTrip)
            toId |= 1;
        SerialEmitter.send(false, toId);
        while (SerialEmitter.isBusy())
            ;
    }
}
