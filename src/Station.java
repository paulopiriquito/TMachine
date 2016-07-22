/**
 * Created by JoaopaulodacostaFran on 23/05/2016.
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

    public int getPrice(){
        return price;
    }

    public int getIndex(){
        return index;
    }

    public boolean equals(Station obj) {
        return this.index == obj.getIndex();
    }
}
