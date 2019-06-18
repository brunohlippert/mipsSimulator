public class Memoria {
    private String[] memoria;
    // 1 MB
    private final int MEMORY_SIZE = 250000;
    private static Memoria instance;

    // Ponteiros de '.text' e '.data' (localizacao na memoria,inicio deles)
    private boolean wroteData;
    private int initData;
    private int fimData;

    private boolean wroteText;
    private int initText;
    private int fimText;

    private Memoria() {
        memoria = new String[MEMORY_SIZE];
        wroteData = false;
        wroteText = false;
    }

    public static Memoria getInstance() {
        if (instance == null)
            instance = new Memoria();
        return instance;
    }

    // Adiciona dados na memoria(Dados como 'V1: 123, 444, 555...')
    public void addData(String info) {
        this.initData = 0;
        this.wroteData = true;
        this.memoria[fimData++] = info; // adiciona e incrementa fim dos dados
    }

    // Adiciona 'text' na memoria(CODIGO!!!)
    public void addText(String info) {
        if (wroteData == false) {
            System.out.println("PRIMEIRO TEM DE ESCREVER DADOS!");
            return;
        }
        this.wroteText = true;
        this.initText = fimText = fimData + 1; // APARTIR DO FIM DOS DADOS!
        this.memoria[fimText++] = info;
    }

    public int getFimTextPointer() {
        return this.fimText;
    }
}