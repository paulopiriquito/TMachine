import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Jo�opaulodacostaFran on 16/05/2016.
 */
public class FileAccess {
    public static int size;
    public static int local;

    public static void read(String fileName){
        try{
            Scanner file = new Scanner(new BufferedReader(new FileReader(fileName)));
            size = file.nextInt();
            Stations.init(size);

            for (int i = 0; i < size; i++) {
                int price = 0;

                if (file.hasNext("-")){
                    local = i + 1;
                    file.next("-");
                }
                else{
                    price = file.nextInt();
                    String name = file.nextLine();
                    Stations.add(i, price, name);
                }

            }
        }
        catch (IOException e){
            System.out.println("Error reading stations");
        }
    }

    public static void main(String[] args) {
        Stations station = new Stations();
        read("resources/stations.txt");
        for (Station e : Stations.stations){
            System.out.println(e.getCoinsAmout() + e.getName());
        }
        System.out.println(station.stationName(3));
    }
}
