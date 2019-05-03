package com.dataprom.guardianautenticador;

public class HexaByte {

    private static int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if(digit == -1) {
            throw new IllegalArgumentException(
                    "Invalid Hexadecimal Character: "+ hexChar);
        }
        return digit;
    }

    public static byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    public static void main(String args[]) {
        hexToByte("0E");

      String[] mes = {"0E D8 7A B2 D0 6C 65 CF 5B EC AF 52 22 B6 60 AB",
                "BC 04 0A F5 75 F1 99 B1 BC 04 0A F5 75 F1 99 B1 ",
                "BC 04 0A F5 75 F1 99 B1 BC 04 0A F5 75 F1 99 B1",
                "BC 04 0A F5 75 F1 99 B1 BC 04 0A F5 75 F1 99 B1",
                "BC 04 0A F5 75 F1 99 B1 BC 04 0A F5 75 F1 99 B1",
                "BC 04 0A F5 75 F1 99 B1 BC 04 0A F5 75 F1 99 B1",
                "BC 04 0A F5 75 F1 99 B1 BC 04 0A F5 75 F1 99 B1",
                "BC 04 0A F5 75 F1 99 B1 BC 04 0A F5 75 F1 99 B1"};

        for(int i =0; i < mes.length; i++) {
            String hexaLinha = mes[i];
            String[] hexaLinhaEach = hexaLinha.split(" ");
            for(String h : hexaLinhaEach ) {
                System.out.println(hexToByte(h));
            }
        }

    }

}
