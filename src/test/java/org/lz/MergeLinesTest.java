package org.lz;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import org.sun.*;

public class MergeLinesTest extends TestCase {

    @Test
    public void testThreelines() {
        List<Integer> items = new ArrayList<>();


        items.add(1);
        items.add(4);

        items.add(8);
        items.add(9);

        items.add(3);
        items.add(6);


        List<List<Integer>> pairs = MergeLines.randomInterval(items);



        assertTrue(pairs.size() == 2);


        assertTrue(pairs.get(0).get(0) == 1);
        assertTrue(pairs.get(0).get(1) == 6);

        assertTrue(pairs.get(1).get(0) == 8);
        assertTrue(pairs.get(1).get(1) == 9);

    }

    @Test
    public void testTwolines() {
        List<Integer> items = new ArrayList<>();


        items.add(1);
        items.add(10);

        items.add(9);
        items.add(10);
        List<List<Integer>> pairs = MergeLines.randomInterval(items);
        assertTrue(pairs.size() == 1);


        assertTrue(pairs.get(0).get(0) == 1);
        assertTrue(pairs.get(0).get(1) == 10);


    }

    @Test
    public void testOneline() {
        List<Integer> items = new ArrayList<>();


        items.add(1);
        items.add(10);

        List<List<Integer>> pairs = MergeLines.randomInterval(items);
        assertTrue(pairs.size() == 1);


        assertTrue(pairs.get(0).get(0) == 1);
        assertTrue(pairs.get(0).get(1) == 10);


    }


    @Test
    public void testTwoNoOverLap()
    {
        List<Integer> items = new ArrayList<>();
        items.add(1);
        items.add(10);
        items.add(11);
        items.add(14);

        List<List<Integer>> pairs = MergeLines.randomInterval(items);
        assertTrue(pairs.size() == 2);


        assertTrue(pairs.get(0).get(0) == 1);
        assertTrue(pairs.get(0).get(1) == 10);

        assertTrue(pairs.get(1).get(0) == 11);
        assertTrue(pairs.get(1).get(1) == 14);
    }

    @Test
    public void testThreeNoOverLap()
    {
        List<Integer> items = new ArrayList<>();
        items.add(1);
        items.add(10);
        items.add(11);
        items.add(14);

        items.add(16);
        items.add(17);

        List<List<Integer>> pairs = MergeLines.randomInterval(items);
        assertTrue(pairs.size() == 3);


        assertTrue(pairs.get(0).get(0) == 1);
        assertTrue(pairs.get(0).get(1) == 10);

        assertTrue(pairs.get(1).get(0) == 11);
        assertTrue(pairs.get(1).get(1) == 14);

        assertTrue(pairs.get(2).get(0) == 16);
        assertTrue(pairs.get(2).get(1) == 17);
    }

}
