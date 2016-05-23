/**
 * Created by JoãopaulodacostaFran on 23/05/2016.
 */
public class Station {
    private int index, price;
    private String name;

    public Station(int index, int price, String name){
        this.index = index;
        this.price = price;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public int getCoinsAmout(){
        return price;
    }

    public int getIndex(){
        return index;
    }

    public String getSinglePrice(){
        return String.format("%f", price*0,5);
    }

    public String getReturnPrice(){
        return String.format("%f", (price*2)*0,5);
    }
}
