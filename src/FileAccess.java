import java.io.*;
import java.util.Scanner;

public class FileAccess {
    public static final String REGISTER = "resources/register.txt"; //Nome do ficheiro de registo
    public static final String STATIONS = "resources/stations.txt"; //Nome do ficheiro de estações
    public static int maxStations; //Número de estações a ler do ficheiro

    /**
     * Carrega as estações do ficheiro, adicionando-as à classe Stations
     * @param fileName
     */
    public static void loadStations(String fileName){
        try{
            Scanner file = new Scanner(new BufferedReader(new FileReader(fileName)));
            maxStations = file.nextInt();
            Stations.init(maxStations);

            for (int i = 0; i < maxStations; i++) {
                int price;

                if (file.hasNext("-")){ //Se for encontrado o marcador da estação de partida
                    file.next("-");
                    String name = file.nextLine().replaceFirst(" ",""); //Retira o espaço em branco no inicio do name
                    Stations.addHome(i, name);//Adiciona a estação como home station
                }
                else{
                    price = file.nextInt();
                    String name = file.nextLine().replaceFirst(" ",""); //Retira o espaço em branco no inicio do name
                    Stations.add(i, price, name);//Adiciona a estação como possível destino
                }

            }
        }
        catch (IOException e){
            System.out.println("Error reading stations");
        }
    }

    /**
     * Retorna a quantidade de moedas no cofre guardado no registo
     * @param filename register
     * @return coin counter
     */
    public static int loadCoins(String filename){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            return Integer.valueOf(br.readLine().split("[;]")[1]);//Utilizamos regex para obter o parâmetro correto
        }
        catch(Exception e){
            System.out.println("Loading empty coin register");
            return 0;
        }
    }

    /**
     * Retorna a quantidade de bilhetes emitidos guardada no registo
     * @param filename register
     * @return ticket counter
     */
    public static int loadTickets(String filename){
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            return Integer.valueOf(br.readLine().split("[;]")[0]);//Utilizamos regex para obter o parâmetro correto
        }
        catch(Exception e){
            System.out.println("Loading empty ticket register");
            return 0;
        }
    }

    /**
     * Guarda os valores dos contadores de moedas e bilhetes numa linha do registo
     * separados por ';'
     * @param filename
     * @param ticketCount
     * @param coinDeposit
     */
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
