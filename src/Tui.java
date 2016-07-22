public class Tui {
    protected static boolean roundTrip = false; //Flag ida e volta
    protected static int lastDigit = 0; //Ultimo digito inserido no teclado
    protected static double timeout; //tempo em que se atinge um timeout

    /**
     * Retorna o identificador da estação que corresponde ao valor inserido
     * pelo utilizador utilizando as teclas numéricas
     * @param key
     * @param max
     * @return
     */
    protected static int getInt(char key, int max) {
        int id = 0;
        if (Character.isDigit(key)) {//Se key for tecla numérica
            int value = Character.getNumericValue(key); //Converter valor da tecla para inteiro
            if(value == 0 && lastDigit ==0){//Se for inserido o valor 00, dar referencia para a primeira estação
                lastDigit = value;
                return 1;
            }
            if(lastDigit*10 + value > max || lastDigit == 0) {//Se a combinação ultrapassar o id maximo
                lastDigit = value;
                return value;
            }
            else {
                id = lastDigit*10 + value; //definir id como a combinação entre o digito anterior e o actual
                lastDigit = value;
            }
        }
        return id;
    }

    /**
     * Retorna a estação resultante do comando de navegação
     * @param key
     * @param selected
     * @param maxStation
     * @return
     */
    protected static Station readStation(char key, Station selected, int maxStation){
        switch (key){
            case 'O': lastDigit = 0; break;
            case 'C': lastDigit = 0; selected = getUpStation(selected, maxStation); break;//Seta para cima
            case 'B': lastDigit = 0; selected = getDownStation(selected, maxStation); break;//Seta para baixo
            default:
                selected = Stations.getStationById(getInt(key, maxStation));break;
        }
        return selected;
    }

    /**
     * Retorna a estação a seguir à selecionada
     * @param selected
     * @param maxStation
     * @return
     */
    private static Station getUpStation(Station selected, int maxStation){
        if (selected.getIndex()+1 < maxStation)
            return Stations.getStation(selected.getIndex()+1);
        else {
            return Stations.getStation(0);
        }
    }

    /**
     * Retorna a estação a anterior à selecionada
     * @param selected
     * @param maxStation
     * @return
     */
    private static Station getDownStation(Station selected, int maxStation){
        if (selected.getIndex()-1 < 0)
            return Stations.getStation(maxStation-1);
        else {
            return Stations.getStation(selected.getIndex()-1);
        }
    }

    /**
     * Actualiza a flag de ida e volta consoante o toogle da tecla 'O'
     * @param key
     */
    protected static void readRoundTrip(char key){
        if(key == 'O') {
            roundTrip = !roundTrip;
        }
    }

    //Retorna true se o comando for de anulação
    protected static boolean readAbort(char key){
        return key == 'A';
    }

    //Esconde o cursor do mostrador do lcd
    protected static void hideCursor(){
        Lcd.setCursor(Lcd.LINES,Lcd.COLS+1);
    }

    /**
     * Escreve em toda a linha superior do lcd uma mensagem centrada
     * @param header mensagem
     */
    protected static void writeHeader(String header){
        Lcd.returnHome();
        int position = (Lcd.COLS/2) - (header.length()/2);
        int i = 1;
        for (; i < position; i++) //Fill before
            Lcd.write(' ');
        for (int j = 0; j < header.length(); ++i, ++j) //Write text
            Lcd.write(header.charAt(j));
        for (; i < Lcd.COLS; ++i) //Fill after
            Lcd.write(' ');
    }

    /**
     * Escreve em toda a linha inferior do lcd uma mensagem centrada
     * @param floor mensagem
     */
    protected static void writeFloor(String floor){
        Lcd.setCursor(Lcd.LINES,0);
        int position = (Lcd.COLS/2) - (floor.length()/2);
        int i = 1;
        for (; i < position; i++) //Fill before
            Lcd.write(' ');
        for (int j = 0; j < floor.length(); ++i, ++j) //Write text
            Lcd.write(floor.charAt(j));
        for (; i < Lcd.COLS+1; ++i) //Fill after
            Lcd.write(' ');
    }

    /**
     * Mostra o indicador do tipo de viagem consoante o roundTrip
     * @param roundTrip
     */
    protected static void writeSign(boolean roundTrip){
        Lcd.setCursor(Lcd.LINES,0);
        if (roundTrip)
            Lcd.write("<->");
        else
            Lcd.write(" ->");
    }

    /**
     * Escreve o id da estação ao centro da linha inferior do lcd, deixando o cursor em blink
     * no ultimo digito
     * @param number
     */
    protected static void writeStationNumber(int number){
        Lcd.setCursor(Lcd.LINES,(Lcd.COLS/2)-1);
        Lcd.write(String.format("%02d", number));
        Lcd.setCursor(Lcd.LINES,(Lcd.COLS/2));
    }

    /**
     * Escreve o valor em euros e cêntimos no canto inferior direito,
     * deixando o cursor em blink no caracter da unidade monetária
     * @param coins
     */
    protected static void writePrice(int coins){
        coins *= 50;
        int eur = coins / 100;
        int cents = 0;
        if (coins%100 != 0)
            cents = 50;

        Lcd.setCursor(2,Lcd.COLS-4);
        Lcd.write(String.format("%d$%02d", eur, cents));
        Lcd.setCursor(Lcd.LINES,Lcd.COLS-3);
    }

    /**
     * Escreve a mensagem de pagamento para um dado valor
     * @param coins
     */
    protected static void writePayment(int coins){
        Lcd.setCursor(Lcd.LINES,0);
        Lcd.write("Pagamento");
        writePrice(coins);
    }

    /**
     * Escreve a mensagem de apresentação de um contador no lcd
     * @param name
     * @param value
     */
    protected static void writeCounter(String name, int value){
        writeHeader("Contadores");
        writeFloor(" ");
        Lcd.setCursor(Lcd.LINES,0);
        Lcd.write(name + "=" + value);
        hideCursor();
    }

    /**
     * Escreve as informações da estação seleccionada como destino
     * @param selected
     */
    protected static void showDestination(Station selected){
        writeHeader(selected.getName());
        writeFloor(" ");
        writeSign(roundTrip);
        int price = selected.getPrice();
        if (roundTrip)
            price *=2;
        writePrice(price);
        writeStationNumber(selected.getIndex()+1);
    }

    /**
     * Faz set de um tempo de timeout de dados segundos para posterior verificação
     * @param seconds
     */
    protected static void timeout(int seconds){
        timeout = System.currentTimeMillis() + (seconds*1000);
    }
}






