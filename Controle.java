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
        public String valorCarta;
        estados(String valor) {
            valorCarta = valor;
        }
    }

    public enum instrucoes {
        Tipo_R("000000"),
        Lw("100011"),
        Sw("101011"),
        Beq("000100"),
        Jump("000010"),
        Ori("001101"),
        Lui("001111"),
        Addiu("001001");

        public String valorCarta;
        instrucoes(String valor) {
            valorCarta = valor;
        }
    }

    public estados estadoAtual;
    public estados proximoEstado;
    public instrucoes instrucaoAtual;
    
    private Controle() {
        desativaTodos();
        proximoEstado = estados.BUSCA;
    }

    public boolean avancaEstado(){
        estadoAtual = proximoEstado;
        switch (estadoAtual) {
            case BUSCA:
                proximoEstado = estados.DECODE;
                return buscaInstrucao();
            case DECODE:
                proximoEstado = estados.EXEC;
                return entraDecode();
            case EXEC:
                //Decide prox estado no metodo
                return entraExec();
            case MEMORIA:
                //Decide prox estado no metodo
                return entraMemoria();
            case WRITE:
                proximoEstado = estados.BUSCA;
                return entraWriteBack();
            default:
                throw new IllegalArgumentException("");
        }
    }

    public void addOpcode(String opCode){
        if(opCode.equals("001001")){
            instrucaoAtual = instrucoes.Addiu;
        } else if(opCode.equals("000000")){
            instrucaoAtual = instrucoes.Tipo_R;
        } else if(opCode.equals("100011")){
            instrucaoAtual = instrucoes.Lw;
        } else if(opCode.equals("101011")){
            instrucaoAtual = instrucoes.Sw;
        } else if(opCode.equals("000100")){
            instrucaoAtual = instrucoes.Beq;
        } else if(opCode.equals("000010")){
            instrucaoAtual = instrucoes.Jump;
        } else if(opCode.equals("001101")){
            instrucaoAtual = instrucoes.Ori;
        } else if(opCode.equals("001111")){
            instrucaoAtual = instrucoes.Lui;
        } else{
            throw new IllegalArgumentException("opCode invalido");
        }
    }

    public instrucoes getInstrucaoAtual(){
        return instrucaoAtual;
    }

    public estados getEstadoAtual(){
        return estadoAtual;
    }

    //
    //  MAQUINA DE ESTADOS
    //

    public boolean buscaInstrucao(){
        PCEscCond = "0";
        PCEsc = "1"; //Em 1
        louD = "0"; //Em 0
        LerMem = "1"; //Em 1
        EscMem = "0";
        MemParaReg = "0";
        IREsc = "1"; //Em 1
        FontePC = "00"; //Em 00
        ULAOp = "000"; //Em 00
        ULAFonteB = "01"; //Em 01
        ULAFonteA = "0"; //Em 0
        EscReg = "0";
        RegDst = "0";
        return false;
    }

    public boolean entraDecode(){
        PCEscCond = "0";
        PCEsc = "1";
        louD = "0";
        LerMem = "1";
        EscMem = "0";
        MemParaReg = "0";
        IREsc = "1";
        FontePC = "00";
        ULAOp = "000"; //Em 00
        ULAFonteB = "11"; //Em 11
        ULAFonteA = "0"; //Em 0
        EscReg = "0";
        RegDst = "0";
        proximoEstado = estados.EXEC;
        return false;
    }

    public boolean entraExec(){
        switch (this.instrucaoAtual){
            case Jump:
                PCEscCond = "0";
                PCEsc = "1";//Em 1
                louD = "0";
                LerMem = "0";
                EscMem = "0";
                MemParaReg = "0";
                IREsc = "0";
                FontePC = "10";//Em 10
                ULAOp = "000";
                ULAFonteB = "00";
                ULAFonteA = "0";
                EscReg = "0";
                RegDst = "0";
                proximoEstado = estados.BUSCA;
                return true;
            case Beq:
                PCEscCond = "1"; //Em 1
                PCEsc = "0"; //Em 0
                louD = "0";
                LerMem = "0";
                EscMem = "0";
                MemParaReg = "0";
                IREsc = "0";
                FontePC = "01"; //Em 01
                ULAOp = "001"; //Em 01
                ULAFonteB = "00"; //Em 00
                ULAFonteA = "1"; //Em 1
                EscReg = "0";
                RegDst = "0";
                proximoEstado = estados.BUSCA;
                return true;
            case Tipo_R:
                PCEscCond = "0";
                PCEsc = "0";
                louD = "0";
                LerMem = "0";
                EscMem = "0";
                MemParaReg = "0";
                IREsc = "0";
                FontePC = "00";
                ULAOp = "010"; //Em 10
                ULAFonteB = "00"; //Em 00
                ULAFonteA = "1"; //Em 1
                EscReg = "0";
                RegDst = "0";
                proximoEstado = estados.MEMORIA;
                return false;
            case Ori:
                PCEscCond = "0";
                PCEsc = "0";
                louD = "0";
                LerMem = "0";
                EscMem = "0";
                MemParaReg = "0";
                IREsc = "0";
                FontePC = "00";
                ULAOp = "111"; //Em 10
                ULAFonteB = "00"; //Em 00
                ULAFonteA = "1"; //Em 1
                EscReg = "0";
                RegDst = "0";
                proximoEstado = estados.MEMORIA;
                return false;
            case Lui:
                PCEscCond = "0";
                PCEsc = "0";
                louD = "0";
                LerMem = "0";
                EscMem = "0";
                MemParaReg = "0";
                IREsc = "0";
                FontePC = "00";
                ULAOp = "011"; //Em 10
                ULAFonteB = "10"; //Em 00
                ULAFonteA = "1"; //Em 1
                EscReg = "0";
                RegDst = "0";
                proximoEstado = estados.MEMORIA;
                return false;
            case Addiu:
                PCEscCond = "0";
                PCEsc = "1";
                louD = "0";
                LerMem = "1";
                EscMem = "0";
                MemParaReg = "0";
                IREsc = "1";
                FontePC = "00";
                ULAOp = "101"; //Em 10
                ULAFonteB = "00"; //Em 00
                ULAFonteA = "1"; //Em 1
                EscReg = "0";
                RegDst = "0";
                proximoEstado = estados.MEMORIA;
                return false;
            case Lw:
            case Sw:
                PCEscCond = "0";
                PCEsc = "0";
                louD = "0";
                LerMem = "0";
                EscMem = "0";
                MemParaReg = "0";
                IREsc = "0";
                FontePC = "00";
                ULAOp = "000"; //Em 00
                ULAFonteB = "10"; //Em 10
                ULAFonteA = "1"; //Em 1
                EscReg = "0";
                RegDst = "0";
                proximoEstado  = estados.MEMORIA;
                return false;
            default:
                throw new IllegalArgumentException("");
        }
    }

    public boolean entraMemoria(){
        switch(instrucaoAtual){
            //Poderia ser o deafult
            case Tipo_R: 
                PCEscCond = "0";
                PCEsc = "0";
                louD = "0";
                LerMem = "0";
                EscMem = "0";
                MemParaReg = "0"; //Em 0
                IREsc = "0";
                FontePC = "00";
                ULAOp = "000";
                ULAFonteB = "00";
                ULAFonteA = "0";
                EscReg = "1"; //Em 1
                RegDst = "1"; //Em 1
                proximoEstado = estados.BUSCA;
                return true;
            case Ori: 
                PCEscCond = "0";
                PCEsc = "0";
                louD = "0";
                LerMem = "0";
                EscMem = "0";
                MemParaReg = "0"; //Em 0
                IREsc = "0";
                FontePC = "00";
                ULAOp = "000";
                ULAFonteB = "00";
                ULAFonteA = "0";
                EscReg = "1"; //Em 1
                RegDst = "0";//Em 0
                proximoEstado = estados.BUSCA;
                return true;
            case Lui: 
                PCEscCond = "0";
                PCEsc = "0";
                louD = "0";
                LerMem = "0";
                EscMem = "0";
                MemParaReg = "0"; //Em 0
                IREsc = "0";
                FontePC = "00";
                ULAOp = "000";
                ULAFonteB = "00";
                ULAFonteA = "0";
                EscReg = "1"; //Em 1
                RegDst = "0";//Em 0
                proximoEstado = estados.BUSCA;
                return true;
            case Addiu: 
                PCEscCond = "0";
                PCEsc = "0";
                louD = "0";
                LerMem = "0";
                EscMem = "0";
                MemParaReg = "0"; //Em 0
                IREsc = "0";
                FontePC = "00";
                ULAOp = "000";
                ULAFonteB = "00";
                ULAFonteA = "0";
                EscReg = "1"; //Em 1
                RegDst = "0";//Em 0
                proximoEstado = estados.BUSCA;
                return true;
            case Sw:
                PCEscCond = "0";
                PCEsc = "0";
                louD = "1"; //Em 1
                LerMem = "0";
                EscMem = "1"; //Em 1
                MemParaReg = "0";
                IREsc = "0";
                FontePC = "00";
                ULAOp = "000";
                ULAFonteB = "00";
                ULAFonteA = "0";
                EscReg = "0";
                RegDst = "0";
                proximoEstado = estados.BUSCA;
                return true;
            case Lw:
                PCEscCond = "0";
                PCEsc = "0";
                louD = "1";  //Em 1
                LerMem = "1"; //Em 1
                EscMem = "0";
                MemParaReg = "0";
                IREsc = "0";
                FontePC = "00";
                ULAOp = "000";
                ULAFonteB = "00";
                ULAFonteA = "0";
                EscReg = "0";
                RegDst = "0";
                proximoEstado = estados.WRITE;
                return false;
            default:
                throw new IllegalArgumentException("");
        }
    }

    public boolean entraWriteBack(){
        PCEscCond = "0";
        PCEsc = "1";
        louD = "0";
        LerMem = "1";
        EscMem = "0";
        MemParaReg = "1"; //Em 1
        IREsc = "1";
        FontePC = "00";
        ULAOp = "000";
        ULAFonteB = "01";
        ULAFonteA = "0";
        EscReg = "1"; //Em 1
        RegDst = "0"; //Em 0]
        proximoEstado = estados.BUSCA;
        return true;
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