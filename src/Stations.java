import java.util.ArrayList;

/**
 * Created by a3908 on 11/03/2016.
 */
public class Stations {
    public static ArrayList<Station> stations;
    private static int homeIndex;


    public static void init(int size){
        stations = new ArrayList<Station>(size);
    }

    public static void add(int index, int price, String name){
        stations.add(new Station(index+1, price, name));
    }

    public static void addHome(int index, String name){
        homeIndex = index;
        stations.add(new Station(index, 0, name));
    }

    public static Station getHome(){
        return stations.get(homeIndex);
    }

    public static Station getStation(int index){
        if(index <= 0)
            return stations.get(0);
        if(index == homeIndex)
            return stations.get(index+1);
        return stations.get(index);
    }

    public static boolean isHome(Station station){
        return station.getIndex() == homeIndex;
    }
}
