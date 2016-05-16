import isel.leic.utils.Time;

/**
 * Ler teclas, Métodos retornam '0'..'9', 'A'..'F', ou NONE
 */
public class Kbd {
    public static final char NONE = 0;

    //Array para relação entre codigo das teclas e o caracter correspondente
    private static final char[] translate={'0','4','8','A','1','5','9','O','2','6','█','D','3','7','▲','▼'};

    /**
     * Inicia a classe
     * Define que quando o programa é iniciado K_ack encontra-se a low
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
        if(Kit.isBit(Pin.K_VAL)){  //Se houver tecla premida
            key = translate[Kit.readBits(Pin.K_DATA)];
            Kit.setBits(Pin.K_ACK);
            while (Kit.isBit(Pin.K_VAL))  //Espera que o Key Decode retire o K_val
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

    /**
     * Método de teste da leitura de caracteres do teclado
     * Imprime na consola o caracter das teclas premidas, termina a execução quando é premida a tecla 'D'
     * @param args
     */
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
