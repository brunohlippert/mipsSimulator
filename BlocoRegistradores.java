import java.util.HashMap;
public class BlocoRegistradores{
    private String[] registradores;
    private final int NUM_REGS = 32;
    private static BlocoRegistradores instance;

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

    private BlocoRegistradores(){
        registradores = new String[NUM_REGS];
        for(int i = 0; i < NUM_REGS; i++){
            registradores[i] = "00000000000000000000000000000000";
        }
        registradores[29] = "00000000000011110100001001000000";
    }

    public static BlocoRegistradores getInstance(){
        if(instance == null)
            instance = new BlocoRegistradores();
        return instance;
    }

    public void escreve(int reg, String data){
        if(Controle.getInstance().EscReg.equals("1"))
            registradores[reg] = data;
    }

    public String le(int reg){
        return registradores[reg];
    }

    public void printaRegs(){
        System.out.println("------------ Registradores -----------------");
        for(int i = 0; i < NUM_REGS; i++){
            System.out.println("Registrador "+i+": "+toHex(registradores[i]));
        }
    }

    public String toHex(String binario32){
        return "0x" + tabelaHex.get(binario32.substring(0, 4)) + tabelaHex.get(binario32.substring(4, 8)) +
        tabelaHex.get(binario32.substring(8, 12)) + tabelaHex.get(binario32.substring(12, 16)) + tabelaHex.get(binario32.substring(16, 20)) +
        tabelaHex.get(binario32.substring(20, 24)) + tabelaHex.get(binario32.substring(24, 28)) + tabelaHex.get(binario32.substring(28, 32));
    }
}   