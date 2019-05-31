public class BlocoRegistradores{
    private String[] registradores;
    private final int NUM_REGS = 32;
    
    public BlocoRegistradores(){
        registradores = new String[NUM_REGS];
        for(int i = 0; i < NUM_REGS; i++){
            registradores[i] = "00000000000000000000000000000000";
        }
    }

    public void escreve(int reg, String data){
        if(Controle.EscReg)
            registradores[reg] = data;
    }

    public String le(int reg){
        return registradores[reg];
    }
}   