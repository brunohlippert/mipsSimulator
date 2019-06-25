
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

    public String getDado(int pos) {
        return this.memoria[pos];
    }

    public void escreveDadoString(int pos, String dado) {
        if (pos < fimText)
            throw new IllegalAccessError("Tentando acessar posição de memória restrita");
        memoria[pos] = dado;
    }

    //
    // FUNCOES DE LOAD
    //

    // Adiciona dados na memoria(Dados como 'V1: 123, 444, 555...')
    public void addData(String info) {
        this.memoria[fimData++] = info;
    }

    // Adiciona 'text' na memoria(CODIGO!!!)
    public void addText(String info) {
        this.memoria[fimText++] = info;
        this.initData = fimData = fimText;
        this.initText = 0;
    }

    public int getFimTextPointer() {
        return this.fimText;
    }

    public int getInitTextPointer() {
        return this.initText;
    }

    public int getInitDataPointer() {
        return this.initData;
    }

    public int getFimDataPoiner() {
        return this.fimData;
    }

    public void printMem() {
        System.out.println("Memoria: ");
        for (int i = 0; i < fimText; i++) {
            System.out.println(i + ", " + ((memoria[i] == null) ? "0x00000000" : memoria[i]));
        }
    }
}