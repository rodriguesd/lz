package org.lz;

import junit.framework.TestCase;
import org.junit.Test;
import org.lz.sort.SortUtil;

import java.util.ArrayList;
import java.util.List;

public class SortUtilTest extends TestCase {

    @Test
    public void testSort()
    {
        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);
        values.add(3);
       int value = SortUtil.getLargestValue(values);
       assertTrue( value == 3);

    }
}
