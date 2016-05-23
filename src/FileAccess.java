import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by JoãopaulodacostaFran on 16/05/2016.
 */
public class FileAccess {

    public static void read(String fileName){
        try{
            Scanner file = new Scanner(new BufferedReader(new FileReader(fileName)));
            int size = file.nextInt();
            Stations.init(size);

            for (int i = 0; i < size; i++) {
                int price = 0;

                if (file.hasNext("-"))
                    file.next("-");
                else
                    price = file.nextInt();
                String name = file.nextLine();
                Stations.add(i, price, name);
            }
        }
        catch (IOException e){
            System.out.println("Erro na leitura das estações");
        }
    }

    public static void main(String[] args) {
        read("resources/stations.txt");
        for (Station e : Stations.stations){
            System.out.println(e.getCoinsAmout() + e.getName());
        }
    }
}
