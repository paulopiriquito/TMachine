import java.io.*;
import java.util.Scanner;

/**
 * Created by a39081 on 16/05/2016.
 */
public class FileAccess {
    public static final String REGISTER = "resources/register.txt";
    public static final String STATIONS = "resources/stations.txt";
    public static int maxStations;

    public static void loadStations(String fileName){
        try{
            Scanner file = new Scanner(new BufferedReader(new FileReader(fileName)));
            maxStations = file.nextInt();
            Stations.init(maxStations);

            for (int i = 0; i < maxStations; i++) {
                int price = 0;

                if (file.hasNext("-")){
                    file.next("-");
                    String name = file.nextLine().replaceFirst(" ","");
                    Stations.addHome(i, name);
                }
                else{
                    price = file.nextInt();
                    String name = file.nextLine().replaceFirst(" ","");
                    Stations.add(i, price, name);
                }

            }
        }
        catch (IOException e){
            System.out.println("Error reading stations");
        }
    }

    public static int loadCoins(String filename){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            return Integer.valueOf(br.readLine().split("[;]")[1]);
        }
        catch(Exception e){
            System.out.println("Loading empty coin register");
            return 0;
        }
    }

    public static int loadTickets(String filename){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            return Integer.valueOf(br.readLine().split("[;]")[0]);
        }
        catch(Exception e){
            System.out.println("Loading empty ticket register");
            return 0;
        }
    }

    public static void saveReg(String filename, int ticketCount, int coinDeposit){
        try{
            FileWriter fw = new FileWriter(filename, false);
            fw.write(ticketCount + ";" + coinDeposit);
            fw.close();
        }
        catch (IOException e){
            System.out.println("Error saving register");
        }
    }
}
