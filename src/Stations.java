import java.util.ArrayList;


/**
 *Classe responsável por guardar as várias estações e devolver informação sobre as mesmas
 */
public class Stations {
    public static ArrayList<Station> stations; //Estrutura de dados onde são guardadas as estações
    private static int homeIndex; //Indice da estação de partida


    public static void init(int size){
        stations = new ArrayList<Station>(size);
    }

    /**
     * Adiciona um destino à colecção de estações
     * @param index
     * @param price
     * @param name
     */
    public static void add(int index, int price, String name){
        stations.add(new Station(index, price, name));
    }

    /**
     * Adiciona a estação de partida à colecção de estações
     * @param index
     * @param name
     */
    public static void addHome(int index, String name){
        homeIndex = index;
        stations.add(new Station(index, 0, name));
    }

    /**
     * Retorna uma referência para a estação de partida
     * @return
     */
    public static Station getHome(){
        return stations.get(homeIndex);
    }

    /**
     * Retorna uma referência para a estação com o dado indice
     * @param index
     * @return
     */
    public static Station getStation(int index){
        return stations.get(index);
    }

    /**
     * Retorna uma referência para a estação com o dado identificador (numero escolhido através do teclado)
     * @param id
     * @return
     */
    public static Station getStationById(int id){
        if (id-1 < 0 || id-1 >= stations.size())
            return getStation(0);
        return getStation(id -1);
    }

    /**
     * Retorna true se uma dada estação é a estação de partida
     * @param station
     * @return
     */
    public static boolean isHome(Station station){
        return station.getIndex() == homeIndex;
    }
}
