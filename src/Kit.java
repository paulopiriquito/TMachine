/**
 * Created by Nuno Conceiçao on 14/03/2016.
 */



import isel.leic.utils.*;
import isel.leic.UsbPort;



public class Kit {

    public static int currentOutput = 0x00;/*Estado actual dos bits de saída */


    public static void sleep(long milis) {
        Time.sleep(milis);
    }


    //inverte a entrada
    public static int in(){
        return ~UsbPort.in();
    }


    //ínverte a saida
    public static void out(int value){
        currentOutput = value;
        UsbPort.out(~value);
    }




    public static void init(){
        out(0);
    }


    // retorna true se o bit for '1'
    public static boolean isBit(int mask){
        int a = in() & mask;
        return a != 0;
    }


    // retorna o valor dos bits representados por mask presentes no porto de input do UsbPort
    public static int readBits(int mask){
        return in() & mask;

    }

    // escreve nos bits representados por mask o valor de value
    public static void writeBits(int mask, int value){
        out ((mask & value) | (~mask & currentOutput));
    }


    // coloca os bits representados por mask no valor lógico '1'
    public static void setBits(int mask){
        out( currentOutput | mask);
    }


    // coloca os bits representados por mask no valor lógico '0'
    public static void clrBits(int mask){
        out(currentOutput & ~mask);
    }
}
