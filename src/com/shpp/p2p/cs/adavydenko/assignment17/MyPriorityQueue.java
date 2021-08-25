package com.shpp.p2p.cs.adavydenko.assignment17;

import java.util.Iterator;

/**
 * This class implements a custom copy of a java standard PriorityQueue class.
 *
 * @param <T> stands for an element of any type (but primitive) that will
 *            be store in a MyPriorityQueue.
 */
public class MyPriorityQueue<T> implements Iterable<T> {

    /**
     * A link to the binary heap that stores user data
     * and is used as MyPriorityQueue fundament.
     */
    @SuppressWarnings("unchecked")
    private T[] binaryHeap = (T[]) (new Object[START_SIZE]);

    /**
     * Default start size of a binary heap.
     */
    private final static int START_SIZE = 15;

    /**
     * The number of objects that can possibly be placed in the
     * current binary heap.
     */
    private int binaryHeapLength;

    /**
     * Number of objects added to the MyPriorityQueue.
     */
    private int numOfAddedObjects = 0;

    /**
     * The index of a cell in the binary heap array the the parent shall be moved to.
     * It can be either index of the previous left or right child.
     */
    private int indexToMoveParentTo;

    /**
     * The MyPriorityQueue object that shall be either moved down
     * or remain in its position after comparing to its children nodes.
     */
    private T parent;

    /**
     * Children of a parent that shall be compared to their parent.
     */
    private Comparable<T> leftChild, rightChild;

    /**
     * The index of a parent node in the binary heap array + 1.
     */
    private int parentIndex = 1;

    /**
     * Links to the parent node`s children.
     */
    private int leftChildIndex, rightChildIndex;

    /**
     * Sets the value of the binaryHeapLength to the length
     * of the binary heap at start.
     */
    public MyPriorityQueue() {
        this.binaryHeapLength = binaryHeap.length;
    }

    /**
     * Returns the number of objects added to the MyPriorityQueue.
     *
     * @return the number of objects added to the MyPriorityQueue.
     */
    public int size() {
        return numOfAddedObjects;
    }

    /**
     * Adds new objects to the MyPriorityQueue. All objects are sorted
     * inside the binary heap.
     *
     * @param obj is any object of any type (but the primitive one) that
     *            shall be stored inside the binary heap.
     * @return true if the object was successfully added to the binary heap.
     */
    public boolean add(T obj) {
        if (obj == null) { // One can not add a null object to the MyPriorityQueue
            throw new NullPointerException();
        } else {
            if (numOfAddedObjects == 0) { // If it is the first object to be added to the MyPriorityQueue
                binaryHeap[0] = obj;
                numOfAddedObjects++;
            } else { // If it is NOT the first object to be added to the MyPriorityQueue
                if (binaryHeapIsFull()) {
                    binaryHeap = createNewHeapAndCopyItems();
                }
                addNewItem(obj);
            }
            return true;
        }
    }

    /**
     * Adds new item to the binary heap if the number of already
     * added objects is more than 0.
     *
     * @param obj is the object that shall be added to the MyPriorityQueue.
     */
    @SuppressWarnings("unchecked")
    private void addNewItem(T obj) {
        Comparable<T> newObj = (Comparable<T>) obj;
        binaryHeap[numOfAddedObjects] = obj; // new item is added to the end of the binary heap
        int newObjIndex = numOfAddedObjects + 1;
        int parentIndex = (int) Math.floor(newObjIndex / 2.0);
        T parentObj = binaryHeap[parentIndex - 1]; // get parent object of the newly added object
        while (newObj.compareTo(parentObj) < 0) { // if the parent`s value is bigger than the newly added object`s value
            binaryHeap[newObjIndex - 1] = parentObj;
            binaryHeap[parentIndex - 1] = obj;
            if (parentIndex == 1) { // if we put the new object to the top of the binary heap
                break;
            }
            newObjIndex = parentIndex;
            parentIndex = (int) Math.floor(newObjIndex / 2.0);
            parentObj = binaryHeap[parentIndex - 1]; // get new parent to compare the current object to
        }
        numOfAddedObjects++;
    }

    /**
     * Creates a new bigger binary heap in a form of an array
     * and copies all items from the current binary heap to the
     * new binary heap.
     *
     * @return the new binary heap with all same items that were
     * stored in the previous binary heap but this time this new
     * binary heap is larger (has more cells) the the previous
     * binary heap.
     */
    @SuppressWarnings("unchecked")
    private T[] createNewHeapAndCopyItems() {
        binaryHeapLength += 20;
        T[] newBinaryHeap = (T[]) (new Object[binaryHeapLength]);
        for (int i = 0; i < binaryHeap.length; i++) {
            newBinaryHeap[i] = binaryHeap[i];
        }
        return newBinaryHeap;
    }

    /**
     * Gets the top most item in the MyPriorityQueue and removes it from the
     * queue. After that the queue gets its last item and puts to the queue top.
     * Than tha method starts comparing the new top item with its new left and
     * right children. The child with smaller value will go up and the item that
     * was just moved to the queue`s top, will go down. The process repeats until
     * all items are placed in a right way (each parent has a value that is the same
     * or less than the value of its children).
     *
     * @return the object that was removed. Null if there are no items in the MyPriorityQueue.
     */
    public T poll() {
        if (!binaryHeapIsEmpty()) {
            T objectToRemove = binaryHeap[0];
            if (numOfAddedObjects < 2) {
                binaryHeap[numOfAddedObjects - 1] = null;
                numOfAddedObjects--;
            } else { // if there are 2 or more items in the binary heap
                binaryHeap[0] = binaryHeap[numOfAddedObjects - 1]; // move the last binary heap item to the top
                binaryHeap[numOfAddedObjects - 1] = null; // reset the cell whose item we just moved to the top
                numOfAddedObjects--;
                rearrangeBinaryHeap();
            }
            return objectToRemove;
        } else {
            return null;
        }
    }

    /**
     * Rearranges the binary heap in order to ensure that all parent nodes
     * have values are are equal or less than their children`s values. Starts
     * with the top most item, compares it to its children and puts the parent
     * node in the child's place if needed.
     */
    private void rearrangeBinaryHeap() {
        parent = binaryHeap[0];
        parentIndex = 1;
        setLeftChildAndLeftChildIndex();
        setRightChildAndRightChildIndex();

        while (isLeftLess() || isRightLess()) { // While at least one of the children is less than the parent
            if (isLeftLess() && isRightLess()) { // if both children are less
                processOneOfTwoChildren();
            } else if (isLeftLess()) { // if only left child is less
                processLeftChild();
            } else { // if only right child is less
                processRightChild();
            }
            setLeftChildAndLeftChildIndex();
            setRightChildAndRightChildIndex();
        }
    }

    /**
     * If both children`s value are less than the parent`s one, get the least of them
     * and swaps them with parent node.
     */
    private void processOneOfTwoChildren() {
        T leastValueChild = getLeastValueChild();
        binaryHeap[indexToMoveParentTo - 1] = parent;
        binaryHeap[parentIndex - 1] = leastValueChild;
        parent = binaryHeap[indexToMoveParentTo - 1];
        parentIndex = indexToMoveParentTo;
    }

    /**
     * If left child`s value is less than the parent`s one, swaps it with parent node.
     */
    @SuppressWarnings("unchecked")
    private void processLeftChild() {
        binaryHeap[leftChildIndex - 1] = parent;
        binaryHeap[parentIndex - 1] = (T) leftChild;
        parent = binaryHeap[leftChildIndex - 1];
        parentIndex = leftChildIndex;
    }

    /**
     * If right child`s value is less than the parent`s one, swaps it with parent node.
     */
    @SuppressWarnings("unchecked")
    private void processRightChild() {
        binaryHeap[rightChildIndex - 1] = parent;
        binaryHeap[parentIndex - 1] = (T) rightChild;
        parent = binaryHeap[rightChildIndex - 1];
        parentIndex = rightChildIndex;
    }

    /**
     * Compares two children and returns the one that shall be moved up.
     * If the children are equal, returns the left child.
     *
     * @return the child that whose value is less.
     */
    @SuppressWarnings("unchecked")
    private T getLeastValueChild() {
        T rightChildToCompare = (T) rightChild;
        if (leftChild.compareTo(rightChildToCompare) < 1) {
            indexToMoveParentTo = leftChildIndex;
            return (T) leftChild;
        } else {
            indexToMoveParentTo = rightChildIndex;
            return (T) rightChild;
        }
    }

    /**
     * Says whether left child`s value is less than the parent`s value.
     *
     * @return true if left child`s value is less than the parent`s value.
     */
    private boolean isLeftLess() {
        if (leftChild == null) {
            return false;
        } else {
            return leftChild.compareTo(parent) < 0;
        }
    }

    /**
     * Says whether right child`s value is less than the parent`s value.
     *
     * @return true if right child`s value is less than the parent`s value.
     */
    private boolean isRightLess() {
        if (rightChild == null) {
            return false;
        } else {
            return rightChild.compareTo(parent) < 0;
        }
    }

    /**
     * Defines the left child for the current parent`s position.
     * Also defines the index of this child.
     */
    @SuppressWarnings("unchecked")
    private void setLeftChildAndLeftChildIndex() {
        if (2 * parentIndex - 1 < numOfAddedObjects) { // get the index of the left child if such exists and the left child
            leftChildIndex = 2 * parentIndex;
            leftChild = (Comparable<T>) binaryHeap[leftChildIndex - 1];
        } else {
            leftChildIndex = -1;
            leftChild = null;
        }
    }

    /**
     * Defines the right child for the current parent`s position.
     * Also defines the index of this child.
     */
    @SuppressWarnings("unchecked")
    private void setRightChildAndRightChildIndex() {
        if ((2 * parentIndex - 1) + 1 < numOfAddedObjects) { // get the index of the right child if such exists and the right child
            rightChildIndex = (2 * parentIndex) + 1;
            rightChild = (Comparable<T>) binaryHeap[rightChildIndex - 1];
        } else {
            rightChildIndex = -1;
            rightChild = null;
        }
    }

    /**
     * Says whether the binary heap is already 100 % filled
     * with objects or not.
     *
     * @return true if the binary heap is already filled.
     */
    private boolean binaryHeapIsFull() {
        return numOfAddedObjects + 1 == binaryHeap.length;
    }

    /**
     * Says whether the binary heap does not have any items.
     *
     * @return true if the binary heap is empty.
     */
    private boolean binaryHeapIsEmpty() {
        return numOfAddedObjects == 0;
    }

    /**
     * Prints the MyPriorityQueue to console.
     */
    public void printQueue() {
        System.out.print("\n[");
        for (int i = 0; i < numOfAddedObjects; i++) {
            if (i == 0) {
                System.out.print(binaryHeap[i]);
            } else {
                System.out.print(", " + binaryHeap[i]);
            }
        }
        System.out.println("]\n");
    }

    /**
     * Creates and returns a MyIterator object to enable
     * the MyPriorityQueue object to use foreach loop.
     *
     * @return a MyIterator object.
     */
    @Override
    public Iterator<T> iterator() {
        return new MyIterator(this);
    }

    /**
     * This class has description of three methods used to
     * created an iterator instance to enable the MyPriorityQueue
     * object to use foreach loop
     */
    private class MyIterator implements Iterator<T> {

        /**
         * A link to a particular MyPriorityQueue object whose
         * elements shall be iterated.
         */
        private final T[] ARRAY;

        /**
         * The index of an element in the MyPriorityQueue object
         * that shall be provided now.
         */
        private int currentPosition;

        private final int SIZE;

        /**
         * Copies a link of a MyPriorityQueue object to access the
         * elements that shall be iterated and sets the index
         * to begin with to zero.
         *
         * @param arr is a MyPriorityQueue object whose elements shall
         *            be provided to iterator.
         */
        private MyIterator(MyPriorityQueue<T> arr) {
            this.ARRAY = arr.binaryHeap;
            this.SIZE = arr.size();
            this.currentPosition = 0;
        }

        /**
         * Says whether there is any element left in the MyPriorityQueue
         * object that can be extracted and processed in a foreach loop.
         *
         * @return true if there is such element.
         */
        @Override
        public boolean hasNext() {
            return currentPosition < SIZE;
        }

        /**
         * Provides the next element to process it
         * in the foreach loop.
         *
         * @return the next element of the MyPriorityQueue object.
         */
        @Override
        public T next() {
            T object = ARRAY[currentPosition];
            currentPosition++;
            return object;
        }
    }
}
