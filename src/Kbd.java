import isel.leic.utils.Time;

/**
 * Ler teclas, Métodos retornam '0'..'9', 'A'..'F', ou NONE
 */
public class Kbd {
    public static final char NONE = 0;
      private static final char[] translate={'1','4','7','*','2','5','8','0','3','6','9','#','A','B','C','D'};

    /**
     * Inicia a classe
     */
    public static void init(){
        Kit.clrBits(Pin.K_ACK);
    }

    /**
     * Retorna de imediato a tecla premida ou NONE se não há tecla premida
     * @return caracter correspondente à tecla premida, ou NONE
     */
    public static char getKey(){
        char key = NONE;
        if(Kit.isBit(Pin.K_VAL)){
            key = translate[Kit.readBits(Pin.K_DATA)];
            Kit.setBits(Pin.K_ACK);
            while (Kit.isBit(Pin.K_VAL))
                ;
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
        char key;

        do
            key = getKey();
        while (Time.getTimeInMillis() <= timeout && key == NONE);

        return key;
    }

    public static void main(String[] args) {
        char key;
        Kit.init();
        Kbd.init();
        do{
            key = getKey();
            if (key != NONE)
                System.out.println(key);
        }
        while (key != 'D');
    }
}
