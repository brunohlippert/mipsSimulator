public class BlocoRegistradores{
    private String[] registradores;
    private final int NUM_REGS = 32;
    private static BlocoRegistradores instance;

    private BlocoRegistradores(){
        registradores = new String[NUM_REGS];
        for(int i = 0; i < NUM_REGS; i++){
            registradores[i] = "00000000000000000000000000000000";
        }
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
}   