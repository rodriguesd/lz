package org.bhn;

import org.apache.commons.lang3.StringUtils;

import java.util.Stack;

public class BRule {

    public static String BRule(String str) {

        if (str == null) {
            return "";
        }

        String values[] = str.split("");
        String toReturn = "";
        Stack<String> items = new Stack<String>();
        int i = 0;
        while (i < values.length) {

            String value = values[i];
            if (StringUtils.isNotBlank(value)) {
                if (value.equals("-")) {
                    items.push(value);
                } else if (value.equals("(")) {
                    boolean ok = items.size() > 0;
                    if (ok &&
                            items.size() > 0 &&
                            "-".equals(items.peek())) {


                        String pre = items.pop();
                        while (i < values.length && !values[i].equals(")")) {
                            if (StringUtils.isNotBlank(values[i])) {
                                if (values[i].equals("+")) {
                                    toReturn = toReturn + "-";
                                } else if (values[i].equals("-")) {
                                    toReturn = toReturn + "+";
                                } else {

                                    if(!"(".equals( values[i]) && !")".equals( values[i]))
                                    {
                                        toReturn = toReturn  + pre+ values[i];
                                        pre = "";
                                    }

                                }
                            }
                            i++;
                        }
                    }
                } else {

                    if(!value.equals("(") && !value.equals(")"))
                    {
                        boolean ok  = items.size() > 0;
                        String minus = "";
                        if (ok && "-".equals(items.peek())) {

                            minus = items.pop();
                        }
                        toReturn = toReturn  + minus + value;
                    }


                }
            }
            i++;
        }

        return toReturn;


    }

}