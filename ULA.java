public class ULA {
    private static ULA instance;

    private ULA() {

    }

    public static ULA getInstane() {
        if (instance == null)
            instance = new ULA();
        return instance;
    }

    // entB pode ser shamt
    public String calcula(String entA, String entB, String func) {
        int valueA = Integer.parseInt(entA, 2);
        int valueB = Integer.parseInt(entB, 2);
        int result;
        if (Controle.getInstane().ULAOp.equals("00")) {// ADDU/ADDIU//lw/sw
            result = valueA + valueB;
        } else if (Controle.getInstane().ULAOp.equals("01")) {// SUB/beq
            result = valueA - valueB;
        } else if (Controle.getInstane().ULAOp.equals("10")) {// ORI
            String strResult = "";
            String[] A_array = entA.split("");
            String[] B_array = entB.split("");
            for (int i = 0; i < 32; i++) {
                if(A_array[i].equals("1") || B_array[i].equals("1")){
                    strResult += "1";
                }else{
                    strResult += "0";
                }
            }
            return strResult;
        } else if (Controle.getInstane().ULAOp.equals("11")) {// lui
            String res = entB.sub(15);
            return res+"0000000000000000";
        } else {// tipo R: slt, and, sll, srl.
            if(func.equals("100100")){//and
                
            }
        }
        return "";
    }
}