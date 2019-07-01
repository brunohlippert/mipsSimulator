package com.lippert.zanon.mips;
import java.util.HashMap;
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

    final HashMap<String, String> tabelaHex = new HashMap<String, String>() {
        {
            put("0000", "0");
            put("0001", "1");
            put("0010", "2");
            put("0011", "3");
            put("0100", "4");
            put("0101", "5");
            put("0110", "6");
            put("0111", "7");
            put("1000", "8");
            put("1001", "9");
            put("1010", "A");// 10
            put("1011", "B");// 11
            put("1100", "C");// 12
            put("1101", "D");// 13
            put("1110", "E");// 14
            put("1111", "F");// 15
        }
    };

    private Memoria() {
        memoria = new String[MEMORY_SIZE];
    }

    public static Memoria getInstance() {
        if (instance == null)
            instance = new Memoria();
        return instance;
    }

    public String getDado(int pos) {
        pos = (pos / 4) + initData;
        if(this.memoria[pos] == null){
            return "00000000000000000000000000000000";
        }
        return this.memoria[pos];
    }

    public String getInstrucao(int pos){
        return this.memoria[pos];
    }

    public void escreveDadoString(int pos, String dado) {
        pos = (pos / 4) + initData;
        if (pos < fimText)
            throw new IllegalAccessError("Tentando acessar posição de memória restrita");
        memoria[pos] = dado;
    }

    public String[] getDadosHex(){
        String[] dados = new String[MEMORY_SIZE];
        int j = 0;
        for (int i = initData; i < MEMORY_SIZE; i++, j++) {
            dados[j] = toHex(getDadoAux(i));
        }

        for(; j< MEMORY_SIZE; j++){
            dados[j] = "0x00000000";
        }

        return dados;
    }

    public String getDadoAux(int pos) {
        if(this.memoria[pos] == null){
            return "00000000000000000000000000000000";
        }
        return this.memoria[pos];
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
        System.out.println("--- INSTRUCOES ---");
        for (int i = 0; i < fimData; i++) {
            if(i == initData)
                System.out.println("--- DATA ---");
            System.out.println(i + ": " + ((memoria[i] == null) ? "0x00000000" : toHex(memoria[i])));
        }
    }

    public String toHex(String binario32){
        return "0x" + tabelaHex.get(binario32.substring(0, 4)) + tabelaHex.get(binario32.substring(4, 8)) +
        tabelaHex.get(binario32.substring(8, 12)) + tabelaHex.get(binario32.substring(12, 16)) + tabelaHex.get(binario32.substring(16, 20)) +
        tabelaHex.get(binario32.substring(20, 24)) + tabelaHex.get(binario32.substring(24, 28)) + tabelaHex.get(binario32.substring(28, 32));
    }
}