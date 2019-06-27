import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class LeitorArquivoMips {

    private String arqPath;
    private Memoria mem = Memoria.getInstance();
    final HashMap<String, String> tabelaHex = new HashMap<String, String>() {
        {
            put("0", "0000");
            put("1", "0001");
            put("2", "0010");
            put("3", "0011");
            put("4", "0100");
            put("5", "0101");
            put("6", "0110");
            put("7", "0111");
            put("8", "1000");
            put("9", "1001");
            put("A", "1010");// 10
            put("B", "1011");// 11
            put("C", "1100");// 12
            put("D", "1101");// 13
            put("E", "1110");// 14
            put("F", "1111");// 15
            put("a", "1010");// 10
            put("b", "1011");// 11
            put("c", "1100");// 12
            put("d", "1101");// 13
            put("e", "1110");// 14
            put("f", "1111");// 15
        }
    };

    public LeitorArquivoMips() {
        arqPath = "arquivo.mips";
    }

    public LeitorArquivoMips(String path) {
        this.arqPath = path;
    }

    public void leArquivo() {
        try {
            Scanner scanner = new Scanner(new File(arqPath));
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.length() == 0) {
                    continue;
                } // Skip linhas em branco!
                if (!line.equalsIgnoreCase(".text")) {
                    System.out.println("ERRO .ASM DEVE COMECAR COM '.text' ");
                    break;
                } else if (line.trim().equalsIgnoreCase(".text")) {
                    while (!line.equalsIgnoreCase(".data")) {
                        line = scanner.nextLine();
                        if (line.trim().equalsIgnoreCase(".data")) {
                            break;
                        }
                        if (line.length() == 0) {
                            continue;
                        } // Skip linhas em branco!
                        String result = decodeHexLine(line);
                        mem.addText(result);
                    }

                }
                if (line.equalsIgnoreCase(".data")) {

                    while (scanner.hasNextLine()) {
                        line = scanner.nextLine();
                        if (line.length() == 0) {
                            continue;
                        } // Skip linhas em branco!
                        //Adiciona somente os valores sem label, e se for array, adiciona
                        //os valores sequencialmente na memoria
                        String txtDados = line.split(".word")[1];//so os dados
                        String[] dadosWord = txtDados.trim().split(" ");
                        for (String dato : dadosWord) {
                            String result = decodeHexLine(dato);
                            mem.addData(result);
                        }
                    }
                    break;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String decodeHexLine(String line) {
        line = line.substring(2);
        StringBuilder strBuilder = new StringBuilder();
        String linha = line.replaceAll(" ", "");// Remove espaco em branco
        for (int i = 0; i < linha.length(); i++) {
            strBuilder.append(tabelaHex.get(linha.charAt(i) + ""));
        }
        return strBuilder.toString();
    }
}