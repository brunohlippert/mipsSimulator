public class Controle {
    private static Controle instance;

    public String PCEscCond;
    public String PCEsc;
    public String louD;
    public String LerMem;
    public String EscMem;
    public String MemParaReg;
    public String IREsc;
    public String FontePC;
    public String ULAOp;
    public String ULAFonteB; // MUX
    public String ULAFonteA; // MUX
    public String EscReg;
    public String RegDst;

    public enum estados {
        BUSCA("BUSCA"),
        DECODE("DECODE"),
        EXEC("EXEC"),
        MEMORIA("MEMORIA"),
        WRITE("WRITE");
    }

    public enum instrucoes {
        Tipo_R("000000"),
        Lw("100011"),
        Sw("101100"),
        Beq("000100"),
        Jump("000010"),
        Ori("001101"),
        Lui("001111"),
        Addiu("001001"),
    }
    public String estadoAtual;
    public String proximoEstado;
    public String instrucaoAtual;
    
    private Controle() {
        desativaTodos();
        proximoEstado = estados.BUSCA;
    }

    public void buscaInstrucao(String instrucao){
        instrucaoAtual = instrucoes.valueOf(instrucao);
        PCEscCond = "0";
        PCEsc = "1";
        louD = "0";
        LerMem = "1";
        EscMem = "0";
        MemParaReg = "0";
        IREsc = "1";
        FontePC = "00";
        ULAOp = "000";
        ULAFonteB = "01";
        ULAFonteA = "0";
        EscReg = "0";
        RegDst = "0";
    }

    public void entraDecode(){
        PCEscCond = "0";
        PCEsc = "0";
        louD = "0";
        LerMem = "0";
        EscMem = "0";
        MemParaReg = "0";
        IREsc = "0";
        FontePC = "00";
        ULAOp = "000";
        ULAFonteB = "11";
        ULAFonteA = "0";
        EscReg = "0";
        RegDst = "0";
    }

    public boolean entraExec(){
        //proximoEstado = estados.EXEC;
        
        return false;
    }

    public boolean avancaEstado(String instrucao){
        estadoAtual = proximoEstado;
        switch (estadoAtual) {
            case estados.BUSCA:
                buscaInstrucao(instrucao);
                proximoEstado = estados.DECODE;
                return false;
            case estados.DECODE:
                entraDecode();
                proximoEstado = estados.EXEC;
                return false;
            case estados.EXEC:
                return entraExec();
            default:
                break;
        }
    }

    public void desativaTodos() {
        PCEscCond = "0";
        PCEsc = "0";
        louD = "0";
        LerMem = "0";
        EscMem = "0";
        MemParaReg = "0";
        IREsc = "0";
        FontePC = "00";
        ULAOp = "000";
        ULAFonteB = "00";
        ULAFonteA = "0";
        EscReg = "0";
        RegDst = "0";
    }

    public static Controle getInstance() {
        if (instance == null)
            instance = new Controle();
        return instance;
    }
}