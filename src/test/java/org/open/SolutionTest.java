package org.open;

import junit.framework.TestCase;

public class SolutionTest extends TestCase {

    public void testOne() {
        String html = "<html><body><div></div><a></a></body></html>";
        Solution solution = new Solution();
        Solution.HtmlNode htmlNode = solution.parse(html);
        assertTrue(htmlNode.getParentTag().equals("html"));
        htmlNode.print();

//        html = "<html></html>";
//        htmlNode = solution.parse(html);
//        assertTrue(htmlNode.getParentTag().equals("html"));
//        htmlNode.print();



    }
}
