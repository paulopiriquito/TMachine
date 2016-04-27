/**
 * Envia tramas para o módulo Serial Receiver
 */
public class SerialEmitter {
    private static final int FRAMESIZE = 10;
    private static final boolean LCD = true, TPrinter = false;

    /**
     * Inicia a classe
     */
    public static void init(){
        Kit.clrBits(Pin.SCLK);
        Kit.clrBits(Pin.SDX);
        Kit.setBits(Pin.IOSsel); //Desliga a recepção do IOS
    }

    /**
     *
     * @param frame
     * @param size
     */
    private static void send(int frame, int size){
        Kit.clrBits(Pin.SCLK);
        Kit.clrBits(Pin.SDX);
        Kit.clrBits(Pin.IOSsel); //Liga a recepção IOS

        if(frame%2 == 1)//Set do primeiro bit
            Kit.setBits(Pin.SDX);
        else
            Kit.clrBits((Pin.SDX));
        frame >>= 1;

        for (int i = 1; i < size; i++) {
            Kit.setBits(Pin.SCLK);
            if(frame%2 == 1) //Set do próximo bit
                Kit.setBits(Pin.SDX);
            else
                Kit.clrBits((Pin.SDX));
            frame >>= 1;
            Kit.clrBits((Pin.SCLK));
        }
        Kit.setBits(Pin.IOSsel); //Desliga a recepção do IOS
    }

    /**
     * Envia uma trama com bit 'addr' e os bits de 'data'
     * @param addr Endereço true:LCD false:Ticket Printer
     * @param data Data
     */
    public static void send(boolean addr, int data){
        while (isBusy()) //Espera até o ios estar disponivel para transmissão
            ;
        //Selecciona o dispositivo receptor (LnT)
        data <<=1;
        if(addr) //LCD
            data |= 1;
        send(data, FRAMESIZE);
    }

    /**
     * Verifica se o IOS está ocupado
     * @return True se ocupado
     */
    public static boolean isBusy(){
        return Kit.isBit(Pin.BUSY);
    }

    public static void main (String[] args){
        init();
        send(LCD, 0b101010101);
    }

}
