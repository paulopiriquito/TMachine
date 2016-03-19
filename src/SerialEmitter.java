/**
 * Envia tramas para o módulo Serial Receiver
 */
public class SerialEmitter {
    private static final int DATA_LENGTH = 9;

    /**
     * Inicia a classe
     */
    public static void init(){
        //noinspection StatementWithEmptyBody
        while (isBusy()) //Espera até o ios estar disponivel para transmissão
            ;
        Kit.clrBits(Pin.SCLK);
        Kit.clrBits(Pin.SDX);
    }

    /**
     * Envia uma trama com bit 'addr' e os bits de 'data'
     * @param addr Endereço true:LCD false:Ticket Printer
     * @param data Data
     */
    public static void send(boolean addr, int data){
        init();  //Prepara o inicio do envio
        Kit.clrBits(Pin.IOSsel); //Liga a recepção IOS //TODO check if ios_sel is inverted

        if(addr) //Selecciona o dispositivo receptor (LnT)
            Kit.setBits(Pin.SDX);

        for (int i = 0; i < DATA_LENGTH; i++) {
            Kit.setBits(Pin.SCLK);  //Envia o bit
            Kit.writeBits(Pin.SDX, data%2);  //Set do próximo bit
            data >>= 1;
            Kit.clrBits((Pin.SCLK));
        }

        Kit.setBits(Pin.SCLK);   //Termina o envio da trama
        Kit.clrBits((Pin.SCLK)); //Termina o envio da trama
        Kit.setBits(Pin.IOSsel); //Desliga a recepção do IOS //TODO check if ios_sel is inverted
    }

    /**
     * Verifica se o IOS está ocupado
     * @return True se ocupado
     */
    public static boolean isBusy(){
        return Kit.isBit(Pin.BUSY);
    }

}
