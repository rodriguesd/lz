package org.lz.blood;

import java.util.*;

public class BloodTypes {

    public static List<List<String>> getBloodTypes(String parentA, String parentB, String child)
    {
        Map<String,List<String>> typesP = new HashMap<>();
        typesP.put("A", new ArrayList<>());
        typesP.get("A").add("A-A");
        typesP.get("A").add("A-O");
        typesP.put("B", new ArrayList<>());
        typesP.get("B").add("B-B");
        typesP.get("B").add("B-O");
        typesP.put("O", new ArrayList<>());
        typesP.get("O").add("O-O");
        typesP.put("AB", new ArrayList<>());
        typesP.get("AB").add("A-B");




        Map<String,List<String>> typesB = new HashMap<>();
        typesB.put("A-A", new ArrayList<>());
        typesB.get("A-A").add("A");

        typesB.put("A-O", new ArrayList<>());
        typesB.get("A-O").add("A");

        typesB.put("B-B", new ArrayList<>());
        typesB.get("B-B").add("B");

        typesB.put("B-O", new ArrayList<>());
        typesB.get("B-O").add("B");

        typesB.put("O-O", new ArrayList<>());
        typesB.get("O-O").add("O");


        typesB.put("A-B", new ArrayList<>());
        typesB.get("A-B").add("AB");







        List<String> bloodTypesA =  typesP.get(parentA);
        List<String> bloodTypesB =  typesP.get(parentB);
        List<List<String>> unique = new ArrayList<>();

        for(int o = 0; o < bloodTypesB.size();o++) {
            String bloodType1 = bloodTypesB.get(o);
            for (int i = 0; i < bloodTypesA.size(); i++)
            {
                String bloodType2 = bloodTypesA.get(i);
                List<String> items =  getBloodTypes(bloodType1, bloodType2, typesB, child);
                if(items != null &&
                        items.size() ==2)
                {
                    unique.add(items);
                }

            }
        }
        if(unique.size() == 0)
        {
            List<String> noValues = new ArrayList<>();
            noValues.add("--");
            noValues.add("--");
            unique.add(noValues);

        }


        return unique;

    }

    private static  List<String> getBloodTypes( String bloodType1, String bloodType2,  Map<String,List<String>> typesB, String target)
    {
        List<String> all= new ArrayList<>();
        String values1[] = bloodType1.split("-");
        String values2[] = bloodType2.split("-");
        List<String > types = new ArrayList<>();
        for(int i = 0; i < values1.length;i++)
        {

            String value1 = values1[i];
            for (int i2 = 0; i2 < values2.length; i2++)
            {
                types.clear();
                types.add(value1);
                types.add(values2[i2]);
                Collections.sort(types);
                String type = types.get(0) + "-" + types.get(1);

                List<String> values =  typesB.get(type);
                if(values != null)
                {
                    for(String value:values)
                    {
                        if(target.equals(value))
                        {
                            all.add(bloodType1.replace("-",""));
                            all.add(bloodType2.replace("-", ""));
                            return all;
                        }

                    }

                }

            }
        }

        return all;

    }
}
