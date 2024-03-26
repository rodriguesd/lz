package org.lz;

import junit.framework.TestCase;
import org.junit.Test;
import org.lz.family.*;

import java.util.ArrayList;
import java.util.List;

public class FamiliesAndSizesTest extends TestCase {
    @Test
    public  void testSampleCase()
    {
        List<String> family = new ArrayList<>();
        family.add("A");
        family.add("B");
        family.add("C");
        family.add("B");
        family.add("B");
        family.add("A");
        family.add("C");

        List<Integer> sizes = new ArrayList<>();
        sizes.add(5);
        sizes.add(5);
        sizes.add(2);
        sizes.add(10);
        sizes.add(4);
        sizes.add(6);
        sizes.add(7);

        String result = FamiliesAndSizes.familiesAndSizes(family, sizes);
        assertTrue("A 39".equals(result));

    }

    @Test
    public void testCannotSolve()
    {
        List<String> family = new ArrayList<>();
        family.add("A");
        family.add("A");
        List<Integer> sizes = new ArrayList<>();
        sizes.add(5);
        sizes.add(5);
        String result = FamiliesAndSizes.familiesAndSizes(family, sizes);
        assertTrue("CANNOT SOLVE".equals(result));

    }

    @Test
    public void testCannotSolve2()
    {
        List<String> family = new ArrayList<>();
        family.add("A");
        family.add("B");
        List<Integer> sizes = new ArrayList<>();
        sizes.add(5);
        sizes.add(5);
        String result = FamiliesAndSizes.familiesAndSizes(family, sizes);
        assertTrue("CANNOT SOLVE".equals(result));

    }


    @Test
    public void  testCannotSolveAternating()
    {
        List<String> family = new ArrayList<>();
        family.add("A");
        family.add("B");
        family.add("A");
        family.add("B");
        List<Integer> sizes = new ArrayList<>();
        sizes.add(5);
        sizes.add(6);
        sizes.add(7);
        sizes.add(8);
        String result = FamiliesAndSizes.familiesAndSizes(family, sizes);
        assertTrue("CANNOT SOLVE".equals(result));


    }

    public void  testOkAternating()
    {
        List<String> family = new ArrayList<>();
        family.add("A");
        family.add("B");
        family.add("A");
        family.add("B");
        List<Integer> sizes = new ArrayList<>();
        sizes.add(5);
        sizes.add(6);
        sizes.add(8);
        sizes.add(7);
        String result = FamiliesAndSizes.familiesAndSizes(family, sizes);
        System.out.println(result);
        assertTrue("A 26".equals(result));


    }
}