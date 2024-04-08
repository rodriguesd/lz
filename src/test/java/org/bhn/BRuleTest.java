package org.bhn;

import junit.framework.TestCase;
import org.junit.Test;

public class BRuleTest extends TestCase {

    @Test
    public void testBRule1()
    {

        String value = BRule.BRule("+(b-c)");
        assert("+b-c".equals(value));
    }


    @Test
    public void testBRule2()
    {
        String value = BRule.BRule("+(h+i)");
        assert("+h+i".equals(value));
    }

    @Test
    public void testBRule3()
    {
        String value = BRule.BRule("-(d-e)");
        System.out.print(value);
        assert("-d+e".equals(value));
    }

    @Test
    public void testBRule4()
    {
        String value = BRule.BRule("-(f+g)");
        System.out.print(value);
        assert("-f-g".equals(value));
    }


    @Test
    public void testBRule5()
    {
        String value = BRule.BRule("a+(b - c) - (d - e) - (f+g)+ (h+i)");
        System.out.print(value);
        assert("a+b-c-d+e-f-g+h+i".equals(value));
    }

    @Test
    public void testBRule6()
    {
        String value = BRule.BRule("-(d-e)");
        System.out.print(value);
        assert("-d+e".equals(value));
    }



}
