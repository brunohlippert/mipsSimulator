public class Mips{
    private Memoria memoria;
    private Controle controle;
    private BlocoRegistradores bcRegistradores;
    private ULA ula;

    //Registradores intermediarios
    private String regInstrucao;
    private String regDadosMemoria;
    private String regA;
    private String regB;
    private String regUlaSaida;

    public Mips(){
        this.memoria = Memoria.getInstance();
        this.controle = Controle.getInstance();
        this.bcRegistradores = BlocoRegistradores.getInstance();
        this.ula = ULA.getInstance();
    }

    public void carregaArquivo(){

    }

    public void avancaClock(){

    }
}