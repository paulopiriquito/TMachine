/**
 * Created by a3908 on 11/03/2016.
 */

/**
 * Escreve no LCD usando a interface de 8 bits
 */
public class Lcd {
    private static final int LINES = 2, COLS = 16; //Dimensão do display

    /**
     * Envia a sequência de inicio do LCD
     */
    public static void init(){
        writeCMD(0b00110000);
        Kit.sleep(5);
        writeCMD(0b00110000);
        writeCMD(0b00110000);
        writeCMD(0b00111000);
        writeCMD(0b00001000);
        writeCMD(0b00000001);
        writeCMD(0b00000111);
        writeCMD(0b00001111);
    }

    /**
     * Escreve um comando/dados no LCD
     * @param rs bit de controlo true:dados false:comando
     * @param data informação de dados/comando
     */
    private static void writeByte(boolean rs, int data){
        data <<= 1;
        if(rs)
            data |= 1;
        SerialEmitter.send(true, data);
    }

    /**
     * Envia um comando para o LCD
     * @param data codigo do comando
     */
    private static void writeCMD(int data){
        writeByte(false, data);
    }

    /**
     * Envia dados de escrita no LCD
     * @param data código do caracter ASCII
     */
    private static void writeDATA(int data){
        writeByte(true, data);
    }

    /**
     * Escreve um caracter na posição atual do cursor
     * @param c Caracter a escrever
     */
    public static void write(char c){
        writeDATA(c);
    }

    /**
     * Escreve uma string na posição atual do cursor
     * @param txt String a escrever
     */
    public static void write(String txt){
        for (int i = 0; i < txt.length(); i++) {
            write(txt.charAt(i));
        }
    }

    public static void main(String[] args) {
        init();
        write("LIC");
    }

}
