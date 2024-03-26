package org.lz.sort;
import java.util.*;

public class SortUtil {

    public  static int getLargestValue(List<Integer> items )
    {
        if(items != null &&
                items.size() > 0)
        {
            Collections.sort(items);
            return items.get(items.size() -1);
        }
        return Integer.MIN_VALUE;
    }
}
