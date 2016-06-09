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
            BUSY = 0x20, //   |**+*||****|
            COIN = 0x40, //   |*+**||****|
            M = 0x80       // |+***||****|
    ;

    /**
     * Output pin masks
     */
    public static final int
            K_ACK = 0x01, //  |****||***+|
            SCLK = 0x02, //   |****||**+*|
            SDX = 0x04,   //  |****||*+**|
            IOSsel = 0x08, // |****||+***|
            ACCEPT = 0x10, // |***+||****|
            COLLECT = 0x20,// |**+*||****|
            RETURN = 0x40  // |*+**||****|
            //K_OE = 0x80, // |+***||****|
    ;
}
