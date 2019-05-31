public class Controle{
    static boolean PCEscCond;
    static boolean PCEsc;
    static boolean louD;
    static boolean LerMem;
    static boolean EscMem;
    static boolean MemParaReg;
    static boolean IREsc;
    static boolean FontePC;
    static String ULAOp;
    static boolean ULAFonteB;
    static boolean ULAFonteA;
    static boolean EscReg;
    static boolean RegDst;
    
    public Controle(){
        desativaTodos();
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
        ULAOp = "00";
        ULAFonteB = false;
        ULAFonteA = false;
        EscReg = false;
        RegDst = false;
    }
}