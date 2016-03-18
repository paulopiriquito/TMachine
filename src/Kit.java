import isel.leic.usbio.UsbPort;
import isel.leic.utils.Time;

/**
 * Virtualiza o acesso ao UsbPort
 */
public class Kit {
    private static int currentOutValue;


    /**
     * Inicia a classe
     */
    public static void init(){
        clear();
    }

    /**
     * Limpar a saída do USBPort
     */
    private static void clear() {
        clrBits(0xFF);
    }

    /**
     * Espera por 'milis' tempo
     * @param milis tempo a esperar
     */
    public static void sleep(long milis) {
        Time.sleep(milis);
    }

    /**
     * Faz a negação do porto de input, para resolver o facto de estar negado em hardware
     */
    private static int in() {
        return ~UsbPort.in();
    }

    /**
     * Faz a escrita negada no porto de output
     * @param outValue valor a enviar
     */
    private static void out(int outValue) {
        UsbPort.out(~outValue);
    }

    /**
     * Retorna true se o bit tiver o valor lógico 1
     * @param mask Máscara de bits a ler
     * @return true if bit is 1
     */
    public static boolean isBit(int mask){
        int data = in();
        data &= mask;
        return data != 0x00;
    }

    /**
     * Retorna os valores dos bits representados por mask presentes no UsbPort
     * @param mask Máscara de bits a ler
     * @return Valor dos bits
     */
    public static int readBits(int mask){
        int data = in();
        data &= mask;
        return data;
    }

    /**
     * Escreve nos bits representados por mask o valor de value
     * @param mask Máscara de bits a escrever
     * @param value Valor a escrever
     */
    public static void writeBits(int mask, int value){
        int result = mask & value;
        currentOutValue &= result;
        out(currentOutValue);
    }

    /**
     * Coloca os bits representados por mask no valor lógico 1
     * @param mask Máscara de bits
     */
    public static void setBits(int mask){
        currentOutValue |= mask;
        out(currentOutValue);
    }

    /**
     * Coloca os bits representados por mask a 0
     * @param mask Máscara de bits
     */
    public static void clrBits(int mask){
        currentOutValue &= ~mask;
        out(currentOutValue);
    }

    /**
     * Método de teste
     * @param args Argumentos
     */
    public static void main(String[] args) {
        Kit.init();
        for (int visual = 1; Kit.isBit(0x08); visual <<= 1){
            while (!Kit.isBit(0x04)){

            }
            Kit.setBits(visual);
            Time.sleep(500);
            if(visual == 0x80){
                Kit.clrBits(0xff);
                visual = 1;
            }
        }
    }
}
