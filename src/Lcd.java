/**
 * Created by a3908 on 11/03/2016.
 */

/**
 * Escreve no LCD usando a interface de 8 bits
 */
public class Lcd {
    private static final int LINES = 2, COLS = 16; //Dimens?o do display

    /**
     * Envia a sequ?ncia de inicio do LCD
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

        //Special Characters
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
        /*writeCMD(0b01000010); //�, on 0x02 address
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
        writeDATA(0b00000000);*/


        writeCMD(0b00001111);


    }

    public static void setCursor(int line, int col){
        if (line > LINES || col > COLS)
            throw new IndexOutOfBoundsException();
        if (line == 2)
            col += 64;
        moveCursor(col);
    }

    /**
     * Escreve um caracter na posi??o atual do cursor
     * @param c Caracter a escrever
     */
    public static void write(char c){
        System.out.print(c);
        switch (c){
            case '�':
                writeDATA(0);
                break;
            case '�':
                writeDATA(1);
                break;
            case '�':
                writeDATA(2);
                break;
            case '�':
                writeDATA(3);
                break;
            case '�':
                writeDATA(4);
                break;
            default: writeDATA(c);
        }

    }

    /**
     * Escreve uma string na posi??o atual do cursor
     */
    public static void write(String txt){
        for (int i = 0; i < txt.length(); i++) {
            write(txt.charAt(i));
        }
    }

    public static void writeHeader(String header){
        returnHome();
        int position = (COLS/2) - (header.length()/2);
        int i = 1;
        for (; i < position; i++) //Fill before
            write(' ');
        for (int j = 0; j < header.length(); ++i, ++j) //Write text
            write(header.charAt(j));
        for (; i < COLS; ++i) //Fill after
            write(' ');
    }

    public static void writeFloor(String floor){
        setCursor(LINES,0);
        int position = (COLS/2) - (floor.length()/2);
        int i = 1;
        for (; i < position; i++) //Fill before
            write(' ');
        for (int j = 0; j < floor.length(); ++i, ++j) //Write text
            write(floor.charAt(j));
        for (; i < COLS; ++i) //Fill after
            write(' ');
    }

    public static void writePrice(int eur, int cents){
        setCursor(2,COLS-3);
        write(String.format("%d�%02d", eur, cents));
        setCursor(LINES,COLS-2);
    }

    public static void writeSign(boolean returning){
        setCursor(LINES,1);
        if (returning)
            write("<->");
        else
            write(" ->");
    }

    public static void writeStationNumber(int number){
        setCursor(LINES,(COLS/2)-1);
        write(String.format("%02d", number));
        setCursor(LINES,(COLS/2));
    }

    public static void writePayment(int eur, int cents){
        setCursor(LINES,1);
        write("Pagamento");
        writePrice(eur, cents);
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

    private static void returnHome(){
        writeCMD(0b00000010);
    }

    private static void moveCursor(int shift){
        writeCMD(128 | (shift-1));
    }


    public static void main(String[] args) {
        init();
        writeHeader("�");
        writeSign(true);
        writePrice(0,50);
        writeStationNumber(0);
    }

}
