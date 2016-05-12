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
            K_ACK = 0x01, //  |****||***+|
            K_OE = 0x10, //   |***+||****|
            SCLK = 0x02, //   |****||**+*|
            SDX = 0x04,   //  |****||*+**|
            IOSsel = 0x08 //  |****||+***|
    ;
}
