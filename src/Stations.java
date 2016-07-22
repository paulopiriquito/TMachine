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
        stations.add(new Station(index, price, name));
    }

    public static void addHome(int index, String name){
        homeIndex = index;
        stations.add(new Station(index, 0, name));
    }

    public static Station getHome(){
        return stations.get(homeIndex);
    }

    public static Station getStation(int index){
        return stations.get(index);
    }

    public static Station getStationById(int id){
        if (id-1 < 0 || id-1 >= stations.size())
            return getStation(0);
        return getStation(id -1);
    }

    public static boolean isHome(Station station){
        return station.getIndex() == homeIndex;
    }
}
