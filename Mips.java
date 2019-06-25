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
    }

    public void carregaArquivo(){

    }

    public void avancaClock(){
        this.controle.avancaEstado();
        switch (this.controle.getEstadoAtual()) {
            case BUSCA:
                estapaUm();
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
            // case this.controle.instrucoes.BUSCA:
            //     estapaUm();
            //     break;
            default:
                throw new IllegalAccessError(); 
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
    public void estapaUm(){
        this.regInstrucao = memoria.getDado(this.PC);
        if(this.controle.PCEsc.equals("1")){
            this.PC++;//ULA FAZ PC++ 
        }
    }
}