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
        memoria = new Memoria();
        controle = new Controle();
        bcRegistradores = new BlocoRegistradores();
        ula = new ULA();

        regInstrucao;
        regDadosMemoria;
        regA;
        regB;
        regUlaSaida;
    }
}