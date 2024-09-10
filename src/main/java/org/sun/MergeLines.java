package org.sun;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MergeLines {

    public static List<List<Integer>> randomInterval(List<Integer> items) throws Exception {


        boolean oddList = items == null || items.size() % 2 > 0;

        if (oddList) {
            throw new Exception("Invalid list size");
        }


        List<List<Integer>> pairs = new ArrayList<>();
        if (items.size() > 0) {
            for (int i = 0; i < items.size(); i = i + 2) {
                List<Integer> pair = new ArrayList<>();
                pair.add(items.get(i));
                pair.add(items.get(i + 1));
                pairs.add(pair);
            }
        }
        Collections.sort(pairs, Comparator.comparingInt(a -> a.get(0)));
        int current = 0;
        int next = 1;
        while (next < pairs.size()) {

            List<Integer> it2 = pairs.get(next);
            List<Integer> it1 = pairs.get(current);
            if (it2.get(0) <= it1.get(1)) {
                //overlap //merge
                int max = Math.max(it2.get(1), it1.get(1));
                it1.set(1, max);
                pairs.remove(next);

            } else {
                current = next;
                next++;
            }
        }


        return pairs;

    }
}
