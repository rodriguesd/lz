package org.fin;

public class EncodeString {

    public static String encode(String encode) {

        if (encode == null ||
                encode.length() == 0) {
            return "";
        }
        StringBuilder val = new StringBuilder();
        String[] values = encode.split("");
        int index1 = 0;
        while (index1 < values.length) {
            String items = values[index1];
            int index2 = index1 + 1;
            while (index2 < values.length) {
                if (!items.equals(values[index2])) {
                    break;
                }
                index2++;
            }

            int delta = index2 - index1;
            val.append(items).append(delta);
            index1 = index2;

        }

        return val.toString();

    }

}
