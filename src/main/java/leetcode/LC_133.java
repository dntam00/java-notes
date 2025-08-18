package leetcode;

public class LC_133 {

    public static void main(String[] args) {

    }

//    public Node cloneGraph(Node node) {
//        if (node == null) {
//            return null;
//        }
//        Map<Integer, Node> newMap = new HashMap<>();
//        Deque<Node> queue = new ArrayDeque<>();
//        Node newRoot = new Node(node.val);
//        queue.addLast(node);
//        newMap.put(newRoot.val, newRoot);
//        while(!queue.isEmpty()) {
//            Node curr = queue.removeFirst();
//            List<Node> neighbors = curr.neighbors;
//            Node currNew = newMap.get(curr.val);
//            List<Node> newN = new ArrayList<>();
//            for (Node no : neighbors) {
//                if (!newMap.containsKey(no.val)) {
//                    Node newNode = new Node(no.val);
//                    queue.addLast(no);
//                    newMap.put(newNode.val, newNode);
//                    newN.add(newNode);
//                } else {
//                    newN.add(newMap.get(no.val));
//                }
//            }
//            currNew.neighbors = newN;
//        }
//        return newRoot;
//    }
}
