public class Memoria {
    private String[] memoria;
    // 1 MB
    private final int MEMORY_SIZE = 250000;
    private static Memoria instance;

    // Ponteiros de '.text' e '.data' (localizacao na memoria,inicio deles)
    private int initData;
    private int fimData;

    private int initText;
    private int fimText;

    private Memoria() {
        memoria = new String[MEMORY_SIZE];
    }

    public static Memoria getInstance() {
        if (instance == null)
            instance = new Memoria();
        return instance;
    }

    // Adiciona dados na memoria(Dados como 'V1: 123, 444, 555...')
    public void addData(String info) {
        this.initData = 0;
        this.memoria[fimData++] = info; // adiciona e incrementa fim dos dados
        this.initText = fimText = fimData; // APARTIR DO FIM DOS DADOS!
    }

    // Adiciona 'text' na memoria(CODIGO!!!)
    public void addText(String info) {
        this.memoria[fimText++] = info;
    }

    public int getFimTextPointer() {
        return this.fimText;
    }

    public int getInitTextPointer(){
        return this.initText;
    }

    public int getInitDataPointer(){
        return this.initData;
    }

    public int getFimDataPoiner(){
        return this.fimText;
    }

    public void printMem(){
        System.out.println("Memoria: ");
        for(int i=0; i<fimText; i++){
            System.out.println(i+", "+memoria[i]);
        }
    }
}