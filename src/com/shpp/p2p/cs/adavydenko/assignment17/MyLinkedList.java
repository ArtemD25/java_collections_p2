package com.shpp.p2p.cs.adavydenko.assignment17;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class implements a copy of a LinkedList class with
 * only several most used methods provided.
 *
 * @param <T> stands for a type of the elements that will be stored in the array
 */
public class MyLinkedList<T> implements Iterable<T> {

    /**
     * Is the first node of a LinkedList.
     */
    private final Node<T> FIRST_NODE;

    /**
     * Is the last node of a LinkedList.
     */
    private Node<T> lastNode = new Node<>(false, null, null);

    /**
     * Number of object already added to the MyLinkedList
     */
    private int numOfAddedObjects = 0;

    /**
     * Constructor for creating a MyLinkedList object. When created the
     * MyLinkedList has only two nodes - the first and the last one.
     * Those nodes do not contain any meaningful information the user
     * wants to store. They are created to mark the MyLinkedList`s
     * start and the end points.
     */
    public MyLinkedList() {
        this.FIRST_NODE = new Node<>(true, lastNode, null);
        this.lastNode = new Node<>(false, null, FIRST_NODE);
    }

    /**
     * Returns the number of objects added to the LinkedList.
     *
     * @return the number of objects added to the array
     */
    public int size() {
        return numOfAddedObjects;
    }

    /**
     * Adds an object to the end of the LinkedList
     *
     * @param obj is an object provided by user to be
     *            added to the LinkedList.
     */
    public void add(T obj) {
        Node<T> newNode = new Node<>(obj, lastNode, lastNode.getPrevNode());
        // Setting the new node as the new nextNode for the previous prevNode of the lastNode
        lastNode.getPrevNode().setNextNode(newNode);
        // Setting the new node as the new prevNode for the lastNode
        lastNode.setPrevNode(newNode);
        changeIndex(newNode);
        numOfAddedObjects++;
    }

    /**
     * Adds an object provided by user and places it to the
     * index provided by user as a first argument. All other
     * objects of the array if any are shifted to the right.
     *
     * @param index the index in the array for the object provided.
     * @param obj   any object provided by user.
     */
    public void add(int index, T obj) {
        if (index < 0 || index > numOfAddedObjects) {
            throw new IndexOutOfBoundsException();
        } else if (index == numOfAddedObjects) {
            add(obj);
        } else {
            Node<T> objUnderIndex = getNode(index);
            Node<T> newNode = new Node<>(obj, objUnderIndex, objUnderIndex.getPrevNode());
            objUnderIndex.getPrevNode().setNextNode(newNode);
            objUnderIndex.setPrevNode(newNode);
            numOfAddedObjects++;
            changeIndex(newNode);
            changeIndexes(index);
        }
    }

    /**
     * Adds an object to the beginning of the LinkedList
     *
     * @param obj is an object provided by user to be
     *            added to the LinkedList.
     */
    public void addFirst(T obj) {
        add(0, obj);
    }

    /**
     * Adds an object to the end of the LinkedList
     *
     * @param obj is an object provided by user to be
     *            added to the LinkedList.
     */
    public void addLast(T obj) {
        add(obj);
    }

    /**
     * Substitutes already existing element with provided index
     * in the array by another element provided by user.
     *
     * @param index is the index of element in the array that shall be substituted.
     * @param obj   a new object shall be put to the array.
     * @return the object inserted to the array.
     */
    public T set(int index, T obj) {
        if (index < 0 || index >= numOfAddedObjects) {
            throw new IndexOutOfBoundsException();
        } else {
            Node<T> objUnderIndex = getNode(index);
            Node<T> newPrevNode = objUnderIndex.getPrevNode();
            Node<T> newNextNode = objUnderIndex.getNextNode();
            Node<T> newNode = new Node<>(obj, newNextNode, newPrevNode);
            newPrevNode.setNextNode(newNode);
            newNextNode.setPrevNode(newNode);
            changeIndex(newNode);
            return obj;
        }
    }

    /**
     * Says whether the array contains the object the user provided
     * as an argument.
     *
     * @param obj any object provided by user.
     * @return true if the array contains such object.
     */
    public boolean contains(T obj) {
        Node<T> currentNode = FIRST_NODE.getNextNode();
        while (!currentNode.IS_LAST) {
            if (currentNode.getValue() == null || obj == null) {
                if (currentNode.getValue() == null && obj == null) {
                    return true;
                }
            } else if (currentNode.getValue().equals(obj)) {
                return true;
            }
            currentNode = currentNode.getNextNode();
        }
        return false;
    }

    /**
     * Removes all objects from the array.
     */
    public void clear() {
        if (numOfAddedObjects > 0) {
            FIRST_NODE.setNextNode(lastNode);
            lastNode.setPrevNode(FIRST_NODE);
            numOfAddedObjects = 0;
        }
    }

    /**
     * Removes an element under provided by user index from
     * the array. All other elements that are placed to the right
     * are shifted to the left.
     *
     * @param index is the index of the element that shall be removed.
     * @return the object deleted from the array.
     */
    public T remove(int index) {
        if (index < 0 || index >= numOfAddedObjects) {
            throw new IndexOutOfBoundsException();
        } else {
            Node<T> objUnderIndex = getNode(index);
            Node<T> newPrevNode = objUnderIndex.getPrevNode();
            Node<T> newNextNode = objUnderIndex.getNextNode();
            newPrevNode.setNextNode(newNextNode);
            newNextNode.setPrevNode(newPrevNode);
            changeIndexes(index - 1);
            numOfAddedObjects--;
            return objUnderIndex.getValue();
        }
    }

    /**
     * Removes the object provided by user from the array if
     * such objects exists. If not, prints a message to console.
     * If there are several such objects in the array, the program
     * will remove the first one of those (starting from the array`s left).
     *
     * @param obj is any object provided by user.
     * @return the object removed from the array.
     */
    public T remove(T obj) {
        return remove(indexOf(obj));
    }

    /**
     * Removes the first element of the array
     *
     * @return the object removed from the array.
     */
    public T removeFirst() {
        return remove(0);
    }

    /**
     * Removes the last element of the array
     *
     * @return the object removed from the array.
     */
    public T removeLast() {
        return remove(numOfAddedObjects - 1);
    }

    /**
     * Returns the index of the element requested. If the element does nit
     * exist the program returns -1.
     * If there are several such objects in the array, the program will return
     * the index of the first one of those (starting from the array`s left).
     *
     * @param obj any object provided to the program.
     * @return the index of the element requested.
     */
    public int indexOf(T obj) {
        if (!contains(obj)) {
            throw new NoSuchElementException();
        } else {
            Node<T> currentNode = FIRST_NODE.getNextNode();
            while (!currentNode.IS_LAST) {
                if (currentNode.getValue() == null || obj == null) {
                    if (currentNode.getValue() == null && obj == null) {
                        return currentNode.getIndex();
                    }
                } else if (currentNode.getValue().equals(obj)) {
                    return currentNode.getIndex();
                }
                currentNode = currentNode.getNextNode();
            }
        }
        return -1;
    }

    /**
     * Gets an objects requested by user based on its index
     * in the array.
     *
     * @param index is index of the requested objects in the array.
     * @return the object under provided index.
     */
    public T get(int index) {
        if (index >= 0 && index < numOfAddedObjects) {
            Node<T> searchedNode = FIRST_NODE.getNextNode();

            for (int i = 0; i < index; i++) {
                searchedNode = searchedNode.getNextNode();
            }
            return searchedNode.getValue();

        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Provides the first element of the array.
     *
     * @return the first object in the array if any.
     */
    public T getFirst() {
        return get(0);
    }

    /**
     * Provides the last element of the array.
     *
     * @return the last object in the array if any.
     */
    public T getLast() {
        return get(numOfAddedObjects - 1);
    }

    /**
     * Returns the Node that has a index value equal to the
     * index value provided as an argument.
     *
     * @param index is index of the requested node in the array.
     * @return the node with the index provided.
     */
    private Node<T> getNode(int index) {
        Node<T> searchedNode = FIRST_NODE.getNextNode();

        for (int i = 0; i < index; i++) {
            searchedNode = searchedNode.getNextNode();
        }

        return searchedNode;
    }

    /**
     * Sets the new index for a node in the LinkedList.
     *
     * @param anyNode is any node provided to the method.
     */
    private void changeIndex(Node<T> anyNode) {
        if (anyNode.getPrevNode().getIndex() < 0) { // If the previous node is the FIRST_NODE
            anyNode.setIndex(0);
        } else {
            anyNode.setIndex(anyNode.getPrevNode().getIndex() + 1);
        }
    }

    /**
     * Changes indexes of all array items starting the next item
     * after the one with index provided to the method.
     *
     * @param index is the index of an item in the array whose index
     *              shall remain but all next items shall have
     *              their indexes changed.
     */
    private void changeIndexes(int index) {
        Node<T> node = getNode(index + 1);
        while (!node.IS_LAST) {
            changeIndex(node);
            node = node.getNextNode();
        }
    }

    /**
     * Prints the MyLinkedList to console with all its items being
     * displayed in one line and separated by a comma. All the array`s
     * data is surrounded by square brackets.
     */
    public void printArray() {
        Node<T> currentNode = FIRST_NODE.getNextNode();

        if (!currentNode.IS_LAST) { // If the next node of the first node is not the last node
            System.out.print("\n[");
            while (!currentNode.IS_LAST) { // While we do not get to the last node of the LinkedList

                if (currentNode.getNextNode().IS_LAST) {
                    System.out.print(currentNode.getValue());
                } else {
                    System.out.print(currentNode.getValue() + ", ");
                }
                currentNode = currentNode.getNextNode();
            }
            System.out.print("]\n");
        } else {
            System.out.println("[]\n");
        }
    }

    /**
     * Creates and returns a MyIterator object to enable
     * the MyLinkedList object to use foreach loop.
     *
     * @return a MyIterator object.
     */
    @Override
    public Iterator<T> iterator() {
        return new MyIterator(this);
    }

    /**
     * This class has description of three methods used to
     * created an iterator instance to enable the MyLinkedList
     * object to use foreach loop
     */
    private class MyIterator implements Iterator<T> {

        /**
         * A link to a particular MyLinkedList object whose
         * elements shall be iterated.
         */
        private final MyLinkedList<T> LIST;

        /**
         * The index of an element in the MyLinkedList object
         * that shall be provided now.
         */
        private int currentPosition;

        /**
         * Copies a link of a MyLinkedList object to access the
         * elements that shall be iterated and sets the index
         * to begin with to zero.
         *
         * @param arr is a MyLinkedList object whose elements shall
         *            be provided to iterator.
         */
        private MyIterator(MyLinkedList<T> arr) {
            LIST = arr;
            currentPosition = 0;
        }

        /**
         * Says whether there is any element left in the MyLinkedList
         * object that can be extracted and processed in a foreach loop.
         *
         * @return true if there is such element.
         */
        @Override
        public boolean hasNext() {
            return currentPosition < LIST.size();
        }

        /**
         * Provides the next element to process it
         * in the foreach loop.
         *
         * @return the next element of the MyLinkedList object.
         */
        @Override
        public T next() {
            T object = LIST.get(currentPosition);
            currentPosition++;
            return object;
        }
    }
}
