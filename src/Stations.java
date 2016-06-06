import java.util.ArrayList;

/**
 * Created by a3908 on 11/03/2016.
 */
public class Stations {
    public static ArrayList<Station> stations;
    private static int current;


    public static void init(int size){
        stations = new ArrayList<Station>(size);
    }

    public static void add(int index, int price, String name){
        stations.add(new Station(index, price, name));
    }

    public static void addCurrent(int index, int price, String name){
        current = index;
        stations.add(new Station(index, price, name));
    }

    public Station getCurrent(){
        return stations.get(current);
    }

    public Station getStation(int index){
        return stations.get(index);
    }

    public static String stationName(int current){return Stations.stations.get(current).getName();}

    public int getStationNumber(){return Stations.stations.indexOf(current) +1;}


}
