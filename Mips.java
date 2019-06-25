import java.math.BigInteger;

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
    private int PC;

    public Mips(){
        this.memoria = Memoria.getInstance();
        this.controle = Controle.getInstance();
        this.bcRegistradores = BlocoRegistradores.getInstance();
        this.ula = ULA.getInstance();
        this.PC = memoria.getInitTextPointer();

        this.regInstrucao = "00000000000000000000000000000000";
        this.regDadosMemoria = "00000000000000000000000000000000";
        this.regA = "00000000000000000000000000000000";
        this.regB = "00000000000000000000000000000000";
        this.regUlaSaida = "00000000000000000000000000000000";
    }

    public void carregaArquivo(){

    }

    public void avancaClock(){
        this.controle.avancaEstado();
        switch (this.controle.getEstadoAtual()) {
            case BUSCA:
                etapaUm();
                break;
            case DECODE:
                etapaDois();
                break;
            // case this.controle.instrucoes.BUSCA:
            //     estapaUm();
            //     break;
            // case this.controle.instrucoes.BUSCA:
            //     estapaUm();
            //     break;
            // case this.controle.instrucoes.BUSCA:
            //     estapaUm();
            //     break;
            default:
                throw new IllegalAccessError(); 
        }
        printaDados();
    }
    
    /**
    Ativos(Por ordem):
        1 - Mux entrada 1 ula
        2 - Mux entrada 2 ula
        3 - Ula
        4 - mux entrada em pc
        5 - PC
        6 - Mux de endereco da memoria
        7 - Memoria
        8 - regInstrucao
    */
    public void etapaUm(){
        this.regInstrucao = memoria.getDado(this.PC);
        String resultPC = this.ula.calcula(Integer.toString(this.PC, 2), Integer.toString(1, 2), "000000");
        this.regUlaSaida = resultPC;
        if(this.controle.PCEsc.equals("1")){
            this.PC = new BigInteger(resultPC, 2).intValue();
        }
    }

    /**
    Ativos(Por ordem):
        1 - Mux entrada 1 ula
        2 - Mux entrada 2 ula
        3 - Ula
        4 - mux entrada em pc
        5 - PC
        6 - Mux de endereco da memoria
        7 - Memoria
        8 - regInstrucao
    */
    public void etapaDois(){
        this.regA = this.regInstrucao.substring(6, 12);
        this.regB = this.regInstrucao.substring(13, 19);
        String resultPC = this.ula.calcula(Integer.toString(this.PC, 2), Integer.toString(1, 2), "000000");
        this.regUlaSaida = resultPC;
        if(this.controle.PCEsc.equals("1")){
            this.PC = new BigInteger(resultPC, 2).intValue();
        }
    }


    //Temporario
    public void printaDados(){
        System.out.println("----- Dados gerais -----");
        System.out.println("Estado atual: "+this.controle.getEstadoAtual().name());
        System.out.println("regInstrucao: "+ regInstrucao);
        System.out.println("regDadosMemoria: "+ regDadosMemoria);
        System.out.println("RegA: "+regA);
        System.out.println("RegB: "+regB);
        System.out.println("RegUlaSaida: "+regUlaSaida);
        System.out.println("----- FIM -----");
    }
}