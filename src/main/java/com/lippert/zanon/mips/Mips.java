package com.lippert.zanon.mips;
import java.math.BigInteger;
import java.util.HashMap;

import com.lippert.zanon.mips.Controle.estados;

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

    final HashMap<String, String> tabelaHex = new HashMap<String, String>() {
        {
            put("0000", "0");
            put("0001", "1");
            put("0010", "2");
            put("0011", "3");
            put("0100", "4");
            put("0101", "5");
            put("0110", "6");
            put("0111", "7");
            put("1000", "8");
            put("1001", "9");
            put("1010", "A");// 10
            put("1011", "B");// 11
            put("1100", "C");// 12
            put("1101", "D");// 13
            put("1110", "E");// 14
            put("1111", "F");// 15
        }
    };

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

    public boolean avancaClock(){
        if(this.memoria.getFimTextPointer() - 1 < this.PC && this.controle.getProximoEstado() == estados.BUSCA)
            return true;
        this.controle.avancaEstado();
        switch (this.controle.getEstadoAtual()) {
            case BUSCA:
                etapaUm();
                break;
            case DECODE:
                etapaDois();
                break;
            case EXEC:
                etapaTres();
                break;
            case MEMORIA:
                etapaQuatro();
                break;
            case WRITE:
                etapaCinco();
                break;
            default:
                throw new IllegalAccessError(); 
        }
        return false;
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
        this.regInstrucao = memoria.getInstrucao(this.PC);
        String resultPC = this.ula.calcula(Integer.toString(this.PC, 2), Integer.toString(1, 2), "000000");
        this.regUlaSaida = resultPC;
        if(this.controle.PCEsc.equals("1")){
            this.PC = new BigInteger(resultPC, 2).intValue();
        }
    }

    /**
    Ativos(Por ordem):
        1 - RegInstrucao
        2 - Controle
        3 - Banco de registradores
        4 - Extensao de sinal
        5 - descloc << 2
        6 - regA
        7 - regB
        8 - PC
        9 - 2 mux ula
        10 - ula
        11 - ula saida
    */
    public void etapaDois(){
        this.controle.addOpcode(regInstrucao.substring(0, 6));
        this.regA = this.bcRegistradores.le(new BigInteger(regInstrucao.substring(6, 11), 2).intValue());
        this.regB = this.bcRegistradores.le(new BigInteger(regInstrucao.substring(11, 16), 2).intValue());
        String extensao = regInstrucao.substring(16);
        extensao = extendeSinal(extensao);
        //extensao = extensao.substring(2) + "00"; nao pulamos de  4 em 4 apenas de 1 em 1 na memoria
        String resultUlaPC_Ex = this.ula.calcula(Integer.toString(this.PC, 2), extensao, "000000");
        this.regUlaSaida = resultUlaPC_Ex;
    }

    public void etapaTres(){
        if(this.controle.getInstrucaoAtual().name() == "Lw" || 
        this.controle.getInstrucaoAtual().name() == "Sw"){
             /**
                Ativos(Por ordem):
                1 - extensao de sinal
                2 - regA
                3 - 2 mux ula
                4 - ula
                5 - ula saida
             */
            String extensao = regInstrucao.substring(16);
            extensao = extendeSinal(extensao);
            String resultUlaPC_Ex = this.ula.calcula(this.regA, extensao, "000000");
            this.regUlaSaida = resultUlaPC_Ex;
        } else if(this.controle.getInstrucaoAtual().name() == "Tipo_R"){
            /**
                Ativos(Por ordem):
                1 - RegA e RegB
                2 - 2 mux ula
                3 - ula
                4 - ula saida
             */
            //caso seja sll ou srl
            if(this.regInstrucao.substring(26).equals("000000") || 
                this.regInstrucao.substring(26).equals("000010")){
                    String resultUlaPC_Ex = this.ula.calcula(this.regB, this.regInstrucao.substring(21, 26), this.regInstrucao.substring(26));
                    this.regUlaSaida = resultUlaPC_Ex;
            }else {//outros tipo R
                String resultUlaPC_Ex = this.ula.calcula(this.regA, this.regB, this.regInstrucao.substring(26));
                this.regUlaSaida = resultUlaPC_Ex;
            }
            
        } else if(this.controle.getInstrucaoAtual().name() == "Beq"){
            /**
                Ativos(Por ordem):
                1 - regA e regB
                2 - 2 mux ula
                3 - Ula
                4 - Ula saida > mux pc
                5 - mux pc
                6 - pc
             */
            String resultUlaPC_Ex = this.ula.calcula(this.regA, this.regB, "000000");
            if(this.ula.getFlagZero()){
                this.PC = new BigInteger(this.regUlaSaida, 2).intValue();
            }
        } else if(this.controle.getInstrucaoAtual().name() == "Jump"){
            /**
                Ativos(Por ordem):
                1 - PC > desloca 2
                2 - descloca 2
                3 - mux pc
                4 - pc
             */
            String pcAux = Integer.toString(this.PC, 2);
            pcAux = pcAux.replace("-", "");
            int aux = 32 - pcAux.length();
            for (int i = 0; i < aux;i++) {
                pcAux = "0"+pcAux;
            }
            String endAux = pcAux.substring(0, 13) + this.regInstrucao.substring(13);
            this.PC = new BigInteger(endAux, 2).intValue();
        } else if(this.controle.getInstrucaoAtual().name() == "Ori" || 
        this.controle.getInstrucaoAtual().name() == "Addiu" || 
        this.controle.getInstrucaoAtual().name() == "Lui"){
             /**
                Ativos(Por ordem):
                1 - RegA e extensao de sinal
                2 - 2 mux ula
                3 - ula
                4 - ula saida
             */
            String extensao = regInstrucao.substring(16);
            extensao = extendeSinal(extensao);
            String resultUlaPC_Ex = this.ula.calcula(this.regA, extensao, "000000");
            this.regUlaSaida = resultUlaPC_Ex;
        } 
    }

    public void etapaQuatro(){
        if(this.controle.getInstrucaoAtual().name() == "Lw"){
            /**
            Ativos(Por ordem):
                1 - Ula saida
                2 - mux memoria
                3 - memoria
                4 - reg de dados
            */
            this.regDadosMemoria = this.memoria.getDado(new BigInteger(this.regUlaSaida.substring(16), 2).intValue());
        } else if(this.controle.getInstrucaoAtual().name() == "Sw"){
            /**
            Ativos(Por ordem):
                1 - Ula saida
                2 - B
                3 - mux memoria
                4 - memoria
            */
            this.memoria.escreveDadoString(new BigInteger(this.regUlaSaida.substring(16), 2).intValue(), this.regB);
        } else if(this.controle.getInstrucaoAtual().name() == "Tipo_R"){
            /**
            Ativos(Por ordem):
                1 - Ula saida
                2 - reg instrucao
                3 - 2 mux bc regs
                4 - bc regs
            */
            this.bcRegistradores.escreve(new BigInteger(this.regInstrucao.substring(16, 21), 2).intValue(), this.regUlaSaida);
        } else if(this.controle.getInstrucaoAtual().name() == "Ori" || 
        this.controle.getInstrucaoAtual().name() == "Addiu" ||
        this.controle.getInstrucaoAtual().name() == "Lui"){
            /**
            Ativos(Por ordem):
                1 - Ula saida
                2 - reg instrucao
                3 - 2 mux bc regs
                4 - bc regs
            */
            this.bcRegistradores.escreve(new BigInteger(this.regInstrucao.substring(11, 16), 2).intValue(), this.regUlaSaida);
        }
    }

    /**
    Ativos(Por ordem):
        1 - reg Dados memoria e reg IR
        2 - 2 mux bc regs
        3 - bc regs
    */
    public void etapaCinco(){
        this.bcRegistradores.escreve(new BigInteger(regInstrucao.substring(11, 16), 2).intValue(), this.regDadosMemoria);
    }

    //Temporario
    public void printaDados(){
        System.out.println("----- Dados gerais -----");
        System.out.println("Estado atual: "+this.controle.getEstadoAtual().name());
        System.out.println("PC: "+ PC);
        System.out.println("regInstrucao: "+ toHex(regInstrucao));
        System.out.println("regDadosMemoria: "+ toHex(regDadosMemoria));
        System.out.println("RegA: "+toHex(regA));
        System.out.println("RegB: "+toHex(regB));
        System.out.println("RegUlaSaida: "+toHex(regUlaSaida));
        this.bcRegistradores.printaRegs();
        System.out.println("----- FIM -----");
    }

    public String toHex(String binario32){
        return "0x" + tabelaHex.get(binario32.substring(0, 4)) + tabelaHex.get(binario32.substring(4, 8)) +
        tabelaHex.get(binario32.substring(8, 12)) + tabelaHex.get(binario32.substring(12, 16)) + tabelaHex.get(binario32.substring(16, 20)) +
        tabelaHex.get(binario32.substring(20, 24)) + tabelaHex.get(binario32.substring(24, 28)) + tabelaHex.get(binario32.substring(28, 32));
    }
    

    public String extendeSinal(String val){
        if(this.controle.getInstrucaoAtual().name() == "Ori" ||
        this.controle.getInstrucaoAtual().name() == "Lui"){
            //Logico
            return "0000000000000000"+val;
        } else {
            //Aritimetico
            char c = val.charAt(0);
            for(int i = 0; i < 16; i++){
                val = c+val;
            }
            return val;
        }
    }

    public String getRegInstrucao() {
        return regInstrucao;
    }

    public String getRegDadosMemoria() {
        return regDadosMemoria;
    }


    public String getRegA() {
        return regA;
    }


    public String getRegB() {
        return regB;
    }

    public String getRegUlaSaida() {
        return regUlaSaida;
    }

    public int getPC() {
        return PC;
    }


    
}