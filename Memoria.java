public class Memoria{
    private String[] memoria;
    //1 MB
    private final int MEMORY_SIZE = 250000;
    private static Memoria instance;


    private Memoria(){
        memoria = new String[MEMORY_SIZE];
    }

    public static Memoria getInstance(){
        if(instance == null)
            instance = new Memoria();
        return instance;
    }


}