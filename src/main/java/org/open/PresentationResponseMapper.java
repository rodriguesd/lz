package org.open;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.*;

public class PresentationResponseMapper {

    public static class Container {
        private UUID id;
        private String displayName;
        private String description;


        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    enum NodeCategory {
        CLASSIFIER,
        INVESTMENT,
        PORTFOLIO,
        CLASSIFIER_TYPE
    }

    public static class Node {
        private UUID id;
        private UUID containerId;
        private UUID parentId;
        private String nodeName;
        private NodeCategory type;
        private BigDecimal weight;

        public Node(UUID id, UUID parentId, String nodeName) {

            this.id = id;
            this.parentId = parentId;
            this.nodeName = nodeName;

        }

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public UUID getContainerId() {
            return containerId;
        }

        public void setContainerId(UUID containerId) {
            this.containerId = containerId;
        }

        public UUID getParentId() {
            return parentId;
        }

        public void setParentId(UUID parentId) {
            this.parentId = parentId;
        }

        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public NodeCategory getType() {
            return type;
        }

        public void setType(NodeCategory type) {
            this.type = type;
        }

        public BigDecimal getWeight() {
            return weight;
        }

        public void setWeight(BigDecimal weight) {
            this.weight = weight;
        }
    }

    public class ContainerHierarchyDto {
        public String displayName;
        public String description;
        public Collection<HierarchyNodesDto> nodes;


        public void print() {
            if (nodes != null) {
                for (HierarchyNodesDto hierarchyNodesDto : nodes) {
                    hierarchyNodesDto.print();
                }
            }


        }

        public ContainerHierarchyDto(String displayName, String description, Collection<HierarchyNodesDto> nodes) {
            this.displayName = displayName;
            this.description = description;
            this.nodes = nodes;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Collection<HierarchyNodesDto> getNodes() {
            return nodes;
        }

        public void setNodes(Collection<HierarchyNodesDto> nodes) {
            this.nodes = nodes;
        }
    }

    public class HierarchyNodesDto {

        public UUID id;
        public String nodeName;
        public BigDecimal weight;
        public Collection<HierarchyNodesDto> elements;


        public UUID getId() {
            return id;
        }

        public void print() {

            print(1);

        }

        private void print(int indent) {

            StringBuilder b = new StringBuilder();
            for (int i = 0; i < indent; i++) {
                b.append("   ");
            }

            System.out.println(b.toString() + nodeName);

            if (elements != null) {
                for (HierarchyNodesDto hierarchyNodesDto : elements) {
                    hierarchyNodesDto.print(indent + 1);
                }
            }


        }

        public HierarchyNodesDto() {
            super();
        }

        public HierarchyNodesDto(UUID id, String nodeName,
                                 Collection<HierarchyNodesDto> elements) {
            this.id = id;
            this.nodeName = nodeName;
            this.elements = elements;
        }

        public HierarchyNodesDto(String nodeName, BigDecimal weight,
                                 Collection<HierarchyNodesDto> elements) {
            this.nodeName = nodeName;
            this.weight = weight;
            this.elements = elements;
        }


        public String getNodeName() {
            return nodeName;
        }

        public void setNodeName(String nodeName) {
            this.nodeName = nodeName;
        }

        public BigDecimal getWeight() {
            return weight;
        }

        public void setWeight(BigDecimal weight) {
            this.weight = weight;
        }

        public Collection<HierarchyNodesDto> getElements() {
            if(elements == null)
            {
                elements = new ArrayList<>();
            }
            return elements;
        }

        public void setElements(Collection<HierarchyNodesDto> elements) {
            this.elements = elements;
        }
    }


    public ContainerHierarchyDto buildHierarchy(Container container,
                                                Collection<Node> nodes) {

        Collection<HierarchyNodesDto> itemsToReturn = new ArrayList<>();
        Map<UUID, List<Node>> parenToChild = new HashMap<>();
        Map<UUID, Node> allNodes = new HashMap<>();
        UUID keyForRoot = null;
        if (nodes != null &&
                nodes.size() > 0) {
            for (Node node : nodes) {
                boolean isParent = node.getParentId() == null;
                if (!isParent) {
                    List<Node> children = parenToChild.get(node.getParentId());
                    allNodes.put(node.getId(), node);
                    if (children == null) {
                        children = new ArrayList<>();
                    }
                    children.add(node);
                    parenToChild.put(node.getParentId(), children);
                }
                else {

                    keyForRoot = node.getId();
                }
            }

            Map<UUID, HierarchyNodesDto> parentsHierarchyNodesDto = new HashMap<>();
            initGraph(parenToChild, allNodes, parentsHierarchyNodesDto);
            List<Node> values =   parenToChild.get(keyForRoot);
            if(values != null)
            {
                for(Node node:values)
                {
                    HierarchyNodesDto hierarchyNodesDto =   parentsHierarchyNodesDto.get(node.getId());
                    if(hierarchyNodesDto != null)
                    {
                        itemsToReturn.add(hierarchyNodesDto);
                    }
                }
            }
        }

        return new ContainerHierarchyDto(container.getDisplayName(), container.getDescription(), itemsToReturn);

    }




    private void initChildren(HierarchyNodesDto parentHierarchyNodesDto,
                              Map<UUID, List<Node>> parenToChild, UUID parent,
                              Map<UUID, HierarchyNodesDto> parentsHierarchyNodesDto) {
        List<Node> children = parenToChild.get(parent);
        if (children != null &&
                children.size() > 0) {


            for (Node node : children) {

                HierarchyNodesDto childHierarchyNodesDto = parentsHierarchyNodesDto.get(node.getId());
                if (childHierarchyNodesDto == null) {
                    childHierarchyNodesDto = new HierarchyNodesDto(node.getId(), node.getNodeName(), new ArrayList<>());
                    parentHierarchyNodesDto.getElements().add(childHierarchyNodesDto);
                    parentsHierarchyNodesDto.put(node.getId(), childHierarchyNodesDto);
                } else {
                    parentHierarchyNodesDto.getElements().add(childHierarchyNodesDto);
                }
            }

        }
    }




    private void initGraph(Map<UUID, List<Node>> parenToChild,
                           Map<UUID, Node> allNodes,
                           Map<UUID, HierarchyNodesDto> parentsHierarchyNodesDto) {



        for (UUID parent : parenToChild.keySet()) {


            Node parentNode = allNodes.get(parent);
            if (parentNode == null) {
                continue;
            }

            HierarchyNodesDto parentHierarchyNodesDto = parentsHierarchyNodesDto.get(parent);
            if (parentHierarchyNodesDto == null) {

                parentHierarchyNodesDto = new HierarchyNodesDto(parentNode.getId(), parentNode.getNodeName(), new ArrayList<>());
                parentsHierarchyNodesDto.put(parent, parentHierarchyNodesDto);
            }
            initChildren(parentHierarchyNodesDto,
                    parenToChild, parent,
                    parentsHierarchyNodesDto);
        }




    }


}
