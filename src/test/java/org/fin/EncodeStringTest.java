package org.fin;

import junit.framework.TestCase;
import org.junit.Test;

public class EncodeStringTest extends TestCase {


    @Test
    public void testOne(){
        String items =  EncodeString.encode("aaaaa");
        assertTrue("a5".equals(items));

    }

    @Test
    public void testTwo(){
        String items =  EncodeString.encode("aabb");
        assertTrue("a2b2".equals(items));
    }


    @Test
    public void testThree(){
        String items =  EncodeString.encode("bbhh");
        assertTrue("b2h2".equals(items));
    }

    @Test
    public void testFour(){
        String items =  EncodeString.encode("ffffffffssssss");
        assertTrue("f8s6".equals(items));


    }

    @Test
    public void testFive(){
        String items =  EncodeString.encode("aassssss");
        assertTrue("a2s6".equals(items));

    }

    @Test
    public void testSix(){
        String items =  EncodeString.encode("a");
        assertTrue("a1".equals(items));

        items =  EncodeString.encode("aaabbb");
        assertTrue("a3b3".equals(items));
        items =  EncodeString.encode("ab");
        assertTrue("a1b1".equals(items));
    }
}
