import java.util.HashMap;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class LeitorArquivoMips {

    private String arqPath;
    private Memoria mem = Memoria.getInstance();
    final HashMap<String, String> tabelaHex = new HashMap<>() {
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
                if (!line.equalsIgnoreCase(".text")) {
                    System.out.println("ERRO TEXTO DEVE COMECAR COM '.text' ");
                    break;
                } else if (line.equalsIgnoreCase(".text")) {

                    System.out.println(" '.text' encontrado!");
                    while (!line.equalsIgnoreCase(".data")) {
                        line = scanner.nextLine();
                        System.out.println("Linha lida: " + line);
                        decodeHexLine(line, "text");
                    }

                }
                if (line.equalsIgnoreCase(".data")) {

                    System.out.println(" '.data' encontrado!");
                    while (scanner.hasNextLine()) {
                        line = scanner.nextLine();
                        System.out.println(line);
                        decodeHexLine(line, "data");
                    }
                    break;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void decodeHexLine(String line, String isDataOrText) {
        StringBuilder strBuilder = new StringBuilder();
        String linha = line.replaceAll(" ", "");// Remove espaco em branco
        for (int i = 0; i < linha.length(); i++) {
            strBuilder.append(tabelaHex.get(linha.charAt(i) + ""));
        }
        if (isDataOrText.equals("data")) {
            mem.addData(linha);
        } else {
            mem.addText(linha);
        }
    }
}