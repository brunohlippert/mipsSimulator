public class Controle {
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

    private static Controle instance;

    private Controle() {
        desativaTodos();
    }

    public static Controle getInstance() {
        if (instance == null)
            instance = new Controle();
        return instance;
    }

    public void desativaTodos() {
        PCEscCond = "0";
        PCEsc = "0";
        louD = "0";
        LerMem = "0";
        EscMem = "0";
        MemParaReg = "0";
        IREsc = "0";
        FontePC = "0";
        ULAOp = "000";
        ULAFonteB = "0";
        ULAFonteA = "0";
        EscReg = "0";
        RegDst = "0";
    }
}