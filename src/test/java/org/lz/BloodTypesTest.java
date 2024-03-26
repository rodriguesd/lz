package org.lz;

import junit.framework.TestCase;
import org.junit.Test;
import org.lz.blood.BloodTypes;

import java.util.List;

public class BloodTypesTest extends TestCase {


    @Test
    public void  testBlood()
    {

        String parent1 = "AB";
        String parent2 = "A";
        String child =  "A";
        List<List<String>>  items =  BloodTypes.getBloodTypes(parent1, parent2, child);
        assertTrue(items.size() ==2 );
        assertTrue(items.get(0).size() == 2);
        String q1 = "";
        assertTrue(items.get(0).get(0).equals("AA") || items.get(0).get(1).equals("AB"));
        assertTrue(items.get(1).get(0).equals("AO") || items.get(1).get(1).equals("AB"));









        parent1 = "O";
        parent2 = "AB";
        child =  "O";
        items =  BloodTypes.getBloodTypes(parent1, parent2, child);
        assertTrue(items.size() ==1 );
        assertTrue(items.get(0).size() == 2);
        assertTrue(items.get(0).get(0).equals("--"));
        assertTrue(items.get(0).get(1).equals("--"));





    }


}
