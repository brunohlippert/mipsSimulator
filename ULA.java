import java.math.BigInteger;

public class ULA {
    private static ULA instance;

    private ULA() {

    }

    public static ULA getInstance() {
        if (instance == null)
            instance = new ULA();
        return instance;
    }

    // entB pode ser shamt
    public String calcula(String entA, String entB, String func) {
        int valueA = new BigInteger(entA, 2).intValue();
        int valueB = new BigInteger(entB, 2).intValue();
        int result;
        if (Controle.getInstance().ULAOp.equals("000")) {// ######## ADDU/ADDIU//lw/sw #######
            result = valueA + valueB;
        } else if (Controle.getInstance().ULAOp.equals("001")) {// ######## SUB/beq ######
            result = valueA - valueB;
        } else if (Controle.getInstance().ULAOp.equals("010")) {// ######### ORI ########
            String strResult = "";
            String[] A_array = entA.split("");
            String[] B_array = entB.split("");
            for (int i = 0; i < 32; i++) {
                if (A_array[i].equals("1") || B_array[i].equals("1")) {
                    strResult += "1";
                } else {
                    strResult += "0";
                }
            }
            return strResult;
        } else if (Controle.getInstance().ULAOp.equals("011")) {// ############# lui ##############
            String res = entB.substring(15);
            return res + "0000000000000000";
        } else {// ############## tipo R: slt, and, sll, srl. ################ ULAOp 001
            if (func.equals("100100")) { // ############ and #############
                String strResult = "";
                String[] A_array = entA.split("");
                String[] B_array = entB.split("");
                for (int i = 0; i < 32; i++) {
                    if (A_array[i].equals("1") && B_array[i].equals("1")) {
                        strResult += "1";
                    } else {
                        strResult += "0";
                    }
                }
                return strResult;
            } else if (func.equals("101010")) {// ############## slt ##############
                if (valueA < valueB) {
                    return "00000000000000000000000000000001";
                } else {
                    return "00000000000000000000000000000000";
                }
            } else if (func.equals("000000")) {//########### sll ###############
                result = valueA;
                for (int i = 0; i < valueB; i++) {
                    result *= 2;
                }
            } else if(func.equals("000010")){//########### slr ###############
                result = valueA;
                for (int i = 0; i < valueB; i++) {
                    result /= 2;
                }
            }
        }
        String res = Integer.toString(result, 2);
        res = res.replace("-", "");
        for (int i = 0; i < 32 - res.length();i++) {
            res = "0"+res;
        }
        return res;
    }
}