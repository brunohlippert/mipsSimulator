public class App {
    public static void main(String[] agrs) {
        Mips mips = new Mips();
        LeitorArquivoMips leitor = new LeitorArquivoMips("teste.asm");
        Memoria mem = Memoria.getInstance();
        leitor.leArquivo();
        boolean result = false;
        do{
            result = mips.avancaClock();
            
        }while(result == false);

        mips.printaDados();
    }
}