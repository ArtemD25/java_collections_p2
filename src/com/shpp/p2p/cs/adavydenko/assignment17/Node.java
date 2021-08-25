package com.shpp.p2p.cs.adavydenko.assignment17;

/**
 * This class represents the structure of a node in the MyLinkedList,
 * MyStack or MyQueue objects.
 */
public class Node<T> {

    /**
     * Boolean flag for the first node in the array
     */
    protected final boolean IS_FIRST;

    /**
     * Boolean flag for the last node in the array
     */
    protected final boolean IS_LAST;

    /**
     * Index of the item in the array
     */
    private int index = -1;

    /**
     * Link to the previous node that the current node
     * is linked to.
     */
    private Node<T> prevNode = null;

    /**
     * Link to the next node that the current node
     * is linked to.
     */
    private Node<T> nextNode = null;

    /**
     * The value that has an item of the array.
     */
    private T value;

    /**
     * Constructor for creating the first and the last nodes of an array
     *
     * @param isFirst  says whether a particular node is a first one (if true)
     *                 or the last one (if false).
     * @param nextNode Link to the next node that the current node is linked to
     * @param prevNode Link to the previous node that the current node is linked to
     */
    public Node(boolean isFirst, Node<T> nextNode, Node<T> prevNode) {
        if (isFirst) {
            this.IS_FIRST = true;
            this.IS_LAST = false;
            this.nextNode = nextNode;
        } else {
            this.IS_FIRST = false;
            this.IS_LAST = true;
            this.prevNode = prevNode;
        }
    }

    /**
     * Constructor for creating a regular node of an array that contains
     * some value and is always linked to a previous node and to the next one.
     *
     * @param value    is the value that the node contains. Can by of any type
     *                 (only not of a primitive one)
     * @param nextNode Link to the next node that the current node is linked to
     * @param prevNode Link to the previous node that the current node is linked to
     */
    public Node(T value, Node<T> nextNode, Node<T> prevNode) {
        this.value = value;
        this.nextNode = nextNode;
        this.prevNode = prevNode;
        this.IS_FIRST = false;
        this.IS_LAST = false;
    }

    /**
     * Provides the node that is the previous node for the argument node.
     *
     * @return the node that is the previous node for the argument node.
     */
    public Node<T> getPrevNode() {
        return prevNode;
    }

    /**
     * Provides the node that is the next node for the argument node.
     *
     * @return the node that is the next node for the argument node.
     */
    public Node<T> getNextNode() {
        return nextNode;
    }

    /**
     * Sets the previous node for the node submitted as an argument.
     *
     * @param anyNode is any node provided by user.
     */
    public void setPrevNode(Node<T> anyNode) {
        this.prevNode = anyNode;
    }

    /**
     * Sets the next node for the node submitted as an argument.
     *
     * @param anyNode is any node provided by user.
     */
    public void setNextNode(Node<T> anyNode) {
        this.nextNode = anyNode;
    }

    /**
     * Provides the index in the array of the item requested by user.
     *
     * @return the index of this item.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Provides the index in the array of the item requested by user.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Provides the index in the array of the item requested by user.
     *
     * @return the index of this item.
     */
    public T getValue() {
        return this.value;
    }
}
