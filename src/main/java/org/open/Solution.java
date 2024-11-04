package org.open;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class Solution {
    public class HtmlNode {

        private String parentTag;
        private List<HtmlNode> children = new ArrayList<>();

        public HtmlNode(String parentTag, List<HtmlNode> children) {
            this.parentTag = parentTag;
            this.children = children;
        }

        public void addChild(HtmlNode node) {
            if (node != null) {
                children.add(node);
            }
        }

        public String getParentTag() {
            return parentTag;
        }

        public void print() {
            print(0);
        }
        private void print(int indent) {

            StringBuilder indentStr = new StringBuilder("  ");
            for(int i = 0; i < indent;i++)
            {
                indentStr.append("    ");
            }

            System.out.println(indentStr.toString() + "<" + parentTag + ">");
            for(HtmlNode node:children)
            {
                node.print(indent + 1);
            }

            System.out.println(indentStr.toString() +"<\\" + parentTag + ">");

        }
    }

    public String getTag(String html) {
        int value = html.indexOf("<");
        int value2 = html.indexOf(">");
        if (value > -1 && value2 > -1) {
            return html.substring(value + 1, value2);
        }
        return "";

    }

    public String getHtml(String html) {

        int value2 = html.indexOf(">");
        if (value2 > -1) {
            return html.substring(value2 + 1, html.length());
        }
        return "";

    }

    public HtmlNode parse(String html) {

        List<String> values = new ArrayList<>();
        while (!html.trim().equals("")) {
            String tag = getTag(html);
            if (StringUtils.isNotBlank(tag.trim())) {
                values.add(tag.trim());
            }
            html = getHtml(html);
        }

        Stack<HtmlNode> items = new Stack<>();
        if (values.size() > 0) {
            items.add(new HtmlNode(values.get(0), new ArrayList<>()));
            for (int i = 1; i < values.size(); i++) {
                String value = values.get(i);
                if (!value.contains("/")) {
                    items.add(new HtmlNode(values.get(i), new ArrayList<>()));
                } else {
                    if (items.size() > 2) {
                        HtmlNode child = items.pop();
                        HtmlNode parent = items.pop();
                        parent.addChild(child);
                        items.add(parent);
                    }

                }
            }

            if (items.size() == 1) {
                return items.pop();
            } else {
                HtmlNode child = items.pop();
                HtmlNode parent = items.pop();
                parent.addChild(child);
                return parent;
            }


        }

        return null;

    }


}


