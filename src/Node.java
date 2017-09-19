public class Node {

    double Entropy;

    Node leftChild = null;
    boolean hasLeft = false;
    Node rightChild = null;
    boolean hasRight = false;
    String data= "";
    boolean isLeafNode = true;
    String name = "";


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public boolean isLeafNode() {
        return isLeafNode;
    }

    public void setLeafNode(boolean leafNode) {
        isLeafNode = leafNode;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    String direction;
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Node() {
    }

    public double getEntropy() {
        return Entropy;
    }

    public void setEntropy(double entropy) {
        Entropy = entropy;
    }

    public Node getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
        hasLeft = true;
        isLeafNode = false;
    }

    public boolean hasLeftChild() {
        return hasLeft;
    }

    public boolean hasRightChild() {
        return hasRight;
    }
    public Node getRightChild() {
        return rightChild;
    }

    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
        hasRight = true;
        isLeafNode = false;
    }


}
