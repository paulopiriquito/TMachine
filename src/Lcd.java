/**
 * Created by a3908 on 11/03/2016.
 */

/**
 * Escreve no LCD usando a interface de 8 bits
 */
public class Lcd {
    public static final int LINES = 2, COLS = 16; //Dimens?o do display

    /**
     * Envia a sequ�ncia de inicio do LCD
     * Inicia na mem�ria de caracteres as configura��es dos caracteres especiais
     */
    public static void init(){
        writeCMD(0b00110000);
        Kit.sleep(5);
        writeCMD(0b00110000);
        writeCMD(0b00110000);
        writeCMD(0b00111000);
        writeCMD(0b00001000);
        writeCMD(0b00000001);
        writeCMD(0b00000110);

        /*Special Characters
        writeCMD(0b01000000); // �, on 0x00 address
        writeDATA(0b00000110);
        writeDATA(0b00001001);
        writeDATA(0b00011100);
        writeDATA(0b00001000);
        writeDATA(0b00011100);
        writeDATA(0b00001001);
        writeDATA(0b00000110);
        writeCMD(0b01000001); // �, on 0x01 address
        writeDATA(0b00001101);
        writeDATA(0b00010010);
        writeDATA(0b00001110);
        writeDATA(0b00000001);
        writeDATA(0b00001111);
        writeDATA(0b00010001);
        writeDATA(0b00001111);
        writeCMD(0b01000010); //�, on 0x02 address
        writeDATA(0b00000000);
        writeDATA(0b00001110);
        writeDATA(0b00010000);
        writeDATA(0b00010000);
        writeDATA(0b00001110);
        writeDATA(0b00000100);
        writeDATA(0b00001100);
        writeDATA(0b00000000);
        writeCMD(0b01000011); //�, on 0x03 address
        writeDATA(0b00000100);
        writeDATA(0b00001010);
        writeDATA(0b00001110);
        writeDATA(0b00000001);
        writeDATA(0b00001111);
        writeDATA(0b00010001);
        writeDATA(0b00001111);
        writeDATA(0b00000000);
        writeCMD(0b01000100); //�, on 0x04 address
        writeDATA(0b00000000);
        writeDATA(0b00000000);
        writeDATA(0b00000000);
        writeDATA(0b00000000);
        writeDATA(0b00000000);
        writeDATA(0b00000000);
        writeDATA(0b00000001);
        writeDATA(0b00000010);
        writeDATA(0b00000100);
        writeDATA(0b00001110);
        writeDATA(0b00010001);
        writeDATA(0b00011111);
        writeDATA(0b00010000);
        writeDATA(0b00001110);
        writeDATA(0b00000000);
        */

        writeCMD(0b00001111);
    }

    /**
     * Escreve um caracter na posi��o atual do cursor
     * @param c Caracter a escrever
     */
    public static void write(char c){
        /*switch (c){
            case '€':
                writeDATA(0);
                break;
            case 'ã':
                writeDATA(1);
                break;
            case 'ç':
                writeDATA(2);
                break;
            case 'â':
                writeDATA(3);
                break;
            case 'é':
                writeDATA(4);
                break;
            default: writeDATA(c);
        }*/

        writeDATA(c);

    }

    /**
     * Escreve uma string na posi�ao atual do cursor
     */
    public static void write(String txt){
        for (int i = 0; i < txt.length(); i++) {
            write(txt.charAt(i));
        }
    }

    /**
     * Escreve um comando/dados no LCD
     * @param rs bit de controlo true:dados false:comando
     * @param data informa??o de dados/comando
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
     * @param data c�digo do caracter ASCII
     */
    private static void writeDATA(int data){
        writeByte(true, data);
    }

    /**
     * Retorna o cursor ao endere�o 0
     */
    public static void returnHome(){
        writeCMD(0b00000010);
    }

    /**
     * Define o endere�o do cursor com a posi��o desejada
     * @param line
     * @param col
     */
    public static void setCursor(int line, int col){
        if (line == 2)
            col += 64;
        writeCMD(128 | (col));
    }


    /**
     * M�todo de teste do funcionamento do lcd
     * @param args
     */
    public static void main(String[] args) {
        init();
        returnHome();
        write("Testing ����");
    }

}
