package org.open;

import junit.framework.TestCase;
import org.junit.Test;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PresentationResponseMapperTest extends TestCase {

    @Test
    public void testGraph() {
        UUID rootId = UUID.randomUUID();
        System.out.println("rootId = " + rootId);
        PresentationResponseMapper.Node root = new PresentationResponseMapper.Node(rootId, null, "ROOT");

        UUID nodeAId = UUID.randomUUID();

        PresentationResponseMapper.Node nodeA = new PresentationResponseMapper.Node(nodeAId, rootId, "A");

        UUID nodeBId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeB = new PresentationResponseMapper.Node(nodeBId, nodeAId, "B");

        UUID nodeCId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeC = new PresentationResponseMapper.Node(nodeCId, nodeAId, "C");

        UUID nodeGId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeG = new PresentationResponseMapper.Node(nodeGId, nodeCId, "G");


        UUID nodeWId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeW = new PresentationResponseMapper.Node(nodeWId, nodeGId, "W");

        UUID nodeYId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeY = new PresentationResponseMapper.Node(nodeYId, nodeWId, "Y");


        UUID nodeEId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeE = new PresentationResponseMapper.Node(nodeEId, nodeAId, "E");

        UUID nodeDId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeD = new PresentationResponseMapper.Node(nodeDId, nodeEId, "D");


        List<PresentationResponseMapper.Node> nodes = new ArrayList<>();
        nodes.add(root);
        nodes.add(nodeA);
        nodes.add(nodeB);
        nodes.add(nodeC);
        nodes.add(nodeG);
        nodes.add(nodeE);
        nodes.add(nodeD);
        nodes.add(nodeW);
        nodes.add(nodeY);

        PresentationResponseMapper presentationResponseMapper = new PresentationResponseMapper();
        PresentationResponseMapper.Container container = new PresentationResponseMapper.Container();
        container.setDisplayName("c-diplay");
        container.setDescription("c-desc");
        PresentationResponseMapper.ContainerHierarchyDto containerHierarchyDto = presentationResponseMapper.buildHierarchy(container, nodes);


        containerHierarchyDto.print();


    }



    @Test
    public void testGraphSameLevel() {
        UUID rootId = UUID.randomUUID();
        System.out.println("rootId = " + rootId);
        PresentationResponseMapper.Node root = new PresentationResponseMapper.Node(rootId, null, "ROOT");

        UUID nodeAId = UUID.randomUUID();

        PresentationResponseMapper.Node nodeA = new PresentationResponseMapper.Node(nodeAId, rootId, "A");

        UUID nodeBId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeB = new PresentationResponseMapper.Node(nodeBId, nodeAId, "B");

        UUID nodeCId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeC = new PresentationResponseMapper.Node(nodeCId, rootId, "C");

        UUID nodeGId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeG = new PresentationResponseMapper.Node(nodeGId, nodeCId, "G");


        UUID nodeWId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeW = new PresentationResponseMapper.Node(nodeWId, nodeGId, "W");

        UUID nodeYId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeY = new PresentationResponseMapper.Node(nodeYId, nodeWId, "Y");


        UUID nodeEId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeE = new PresentationResponseMapper.Node(nodeEId, nodeAId, "E");

        UUID nodeDId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeD = new PresentationResponseMapper.Node(nodeDId, nodeEId, "D");


        List<PresentationResponseMapper.Node> nodes = new ArrayList<>();
        nodes.add(root);
        nodes.add(nodeA);
        nodes.add(nodeB);
        nodes.add(nodeC);
        nodes.add(nodeG);
        nodes.add(nodeE);
        nodes.add(nodeD);
        nodes.add(nodeW);
        nodes.add(nodeY);

        PresentationResponseMapper presentationResponseMapper = new PresentationResponseMapper();
        PresentationResponseMapper.Container container = new PresentationResponseMapper.Container();
        container.setDisplayName("c-diplay");
        container.setDescription("c-desc");
        PresentationResponseMapper.ContainerHierarchyDto containerHierarchyDto = presentationResponseMapper.buildHierarchy(container, nodes);


        containerHierarchyDto.print();


    }


    @Test
    public void testGraphSameLevel2() {
        UUID rootId = UUID.randomUUID();
        System.out.println("rootId = " + rootId);
        PresentationResponseMapper.Node root = new PresentationResponseMapper.Node(rootId, null, "ROOT");

        UUID nodeAId = UUID.randomUUID();

        PresentationResponseMapper.Node nodeA = new PresentationResponseMapper.Node(nodeAId, rootId, "A");

        UUID nodeBId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeB = new PresentationResponseMapper.Node(nodeBId, nodeAId, "B");

        UUID nodeCId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeC = new PresentationResponseMapper.Node(nodeCId, nodeBId, "C");

        UUID nodeGId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeG = new PresentationResponseMapper.Node(nodeGId, nodeCId, "D");


        UUID nodeWId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeW = new PresentationResponseMapper.Node(nodeWId, nodeAId, "E");

        UUID nodeYId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeY = new PresentationResponseMapper.Node(nodeYId, nodeWId, "R");


        UUID nodeEId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeE = new PresentationResponseMapper.Node(nodeEId, nodeAId, "W");

        UUID nodeDId = UUID.randomUUID();
        PresentationResponseMapper.Node nodeD = new PresentationResponseMapper.Node(nodeDId, nodeEId, "R");


        List<PresentationResponseMapper.Node> nodes = new ArrayList<>();
        nodes.add(root);
        nodes.add(nodeA);
        nodes.add(nodeB);
        nodes.add(nodeC);
        nodes.add(nodeG);
        nodes.add(nodeE);
        nodes.add(nodeD);
        nodes.add(nodeW);
        nodes.add(nodeY);

        PresentationResponseMapper presentationResponseMapper = new PresentationResponseMapper();
        PresentationResponseMapper.Container container = new PresentationResponseMapper.Container();
        container.setDisplayName("c-diplay");
        container.setDescription("c-desc");
        PresentationResponseMapper.ContainerHierarchyDto containerHierarchyDto = presentationResponseMapper.buildHierarchy(container, nodes);


        containerHierarchyDto.print();


    }


}
