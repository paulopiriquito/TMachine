/**
 * Kit pin IO interface masks
 */
public class Pin {

    /**
     * Input pin masks
     */
    public static final int
            K_DATA = 0x0f, // |****||++++|
            K_VAL = 0x10, //  |***+||****|
            BUSY = 0x20 //    |**+*||****|
    ;

    /**
     * Output pin masks
     */
    public static final int
            K_OE = 0x01, //   |****||***+|
            K_ACK = 0x02, //  |****||**+*|
            SCLK = 0x04, //   |****||*+**|
            SDX = 0x08,   //  |****||+***|
            IOSsel = 0x10 //  |***+||****|
    ;
}
