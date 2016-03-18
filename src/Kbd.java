import isel.leic.utils.Time;

/**
 * Ler teclas, Métodos retornam '0'..'9', 'A'..'F', ou NONE
 */
public class Kbd {
    public static final char NONE = 0;
    private static final char[] translate={'D','0','*','#','4','6','5','B','1','3','2','A','7','9','8','C'};

    /**
     * Inicia a classe
     */
    public static void init(){
        Kit.clrBits(Pin.K_OE);
        Kit.clrBits(Pin.K_ACK);
    }

    /**
     * Retorna de imediato a tecla premida ou NONE se não há tecla premida
     * @return caracter correspondente à tecla premida, ou NONE
     */
    public static char getKey(){
        char key = NONE;
        if(Kit.isBit(Pin.K_VAL)){
            Kit.setBits(Pin.K_OE);
            Kit.sleep(10);
            key = translate[Kit.readBits(Pin.K_DATA)];
            Kit.clrBits(Pin.K_OE);
            Kit.setBits(Pin.K_ACK);
            Kit.sleep(10);
            Kit.clrBits(Pin.K_ACK);
        }
        return key;
    }

    /**
     * Retorna a tecla quando for premida ou NONE se não houve tecla após decorridos 'timeout' milisegundos
     * @param timeout Tempo de espera em milisegundos
     * @return Tecla premida ou NONE
     */
    public static char waitKey(long timeout){
        timeout += Time.getTimeInMillis();
        char key = NONE;
        do{
            key = getKey();

        }
        while (Time.getTimeInMillis() <= timeout && key == NONE);
        return key;
    }

    public static void main(String[] args) {
        Kit.init();
        Kbd.init();
        while (Kit.isBit(0x10)){
            char key = waitKey(500);
            if (key == NONE){
                System.out.println("NONE");
            }
            else
                System.out.println(key);
        }
    }
}
