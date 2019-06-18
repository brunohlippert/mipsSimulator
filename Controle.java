public class Controle{
    public boolean PCEscCond;
    public boolean PCEsc;
    public boolean louD;
    public boolean LerMem;
    public boolean EscMem;
    public boolean MemParaReg;
    public boolean IREsc;
    public boolean FontePC;
    public String ULAOp;
    public boolean ULAFonteB;
    public boolean ULAFonteA;
    public boolean EscReg;
    public boolean RegDst;
    
    private static Controle instance;

    private Controle(){
        desativaTodos();
    }

    public static Controle getInstance(){
        if(instance == null)
            instance = new Controle();
        return instance;
    }

    public void desativaTodos(){
        PCEscCond = false;
        PCEsc = false;
        louD = false;
        LerMem = false;
        EscMem = false;
        MemParaReg = false;
        IREsc = false;
        FontePC = false;
        ULAOp = "000";
        ULAFonteB = false;
        ULAFonteA = false;
        EscReg = false;
        RegDst = false;
    }
}