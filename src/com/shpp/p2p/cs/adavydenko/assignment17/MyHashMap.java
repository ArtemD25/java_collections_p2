package com.shpp.p2p.cs.adavydenko.assignment17;

import java.util.*;

/**
 * This class implements a custom copy of a java standard HashMap class.
 *
 * @param <K> stands for a type of the elements that will be stored in the array as a key
 * @param <V> stands for a type of the elements that will be stored in the array as a value
 */
public class MyHashMap<K, V> {

    /**
     * Default size of a MyHashMap object right after it is created.
     */
    private final int DEFAULT_NUM_OF_BUCKETS = 16;

    /**
     * Size of a MyHashMap object. Equals 16 right after it has been created.
     */
    private int numberOfBuckets = DEFAULT_NUM_OF_BUCKETS;

    /**
     * Number of key-value items added to the MyHashMap object.
     */
    private int numOfAddedObjects = 0;

    /**
     * The internal array that stores all information
     * provided to MyArrayList.
     */
    @SuppressWarnings("unchecked")
    private HashMapNode<K, V>[] table = new HashMapNode[numberOfBuckets];

    /**
     * If a MyHashMap is already filled with the number of objects (key-value)
     * exceeding {LOAD_FACTOR * numberOfBuckets}, the numberOfBuckets shall
     * be enlarged and all MyHashMap items shall rewritten to a new MyHashMap.
     */
    private static final double LOAD_FACTOR = 0.75;

    /**
     * Indicates for the printHashMap method whether the first
     * node was already printed or not.
     */
    private boolean firstNodePrinted = false;

    /**
     * Returns the number of objects (key-value) added to the MyHashMap.
     *
     * @return the number of objects added to the MyHashMap.
     */
    public int size() {
        return numOfAddedObjects;
    }

    /**
     * Creates a node with user key and value, adds hashCode to
     * this node and add this node to the internal MyHashCode array.
     *
     * @param itemKey   is the key value provided by user.
     * @param itemValue is the value for the key provided by user.
     */
    public void put(K itemKey, V itemValue) {
        int hashCode = getHashCode(itemKey);
        int index = getIndex(hashCode);
        if (!containsKey(itemKey)) { // if there is no such key in the MyHashMap yet
            if (isHashMapFull()) { // if the MyHashMap is already full
                numOfAddedObjects = 0;
                numberOfBuckets += 100; // make the size of the next table-array plus 100 cells
                table = copyItemsToLargerTable();
                hashCode = getHashCode(itemKey);
                index = getIndex(hashCode);
            }
            saveNewNode(hashCode, index, itemKey, itemValue, table); // if the MyHashMap is not full yet
        } else { // if such key already exists in this MyHashMap object
            changeExistingKeyValue(itemKey, itemValue, index);
        }
    }

    /**
     * If a key already exists in the MyHashMap, the program gets the
     * HashMapNode storing it and updates the value of this key.
     *
     * @param itemKey   is the key whose value shall be changed.
     * @param itemValue is the new value of the provided key
     *                  that shall substitute the current value.
     * @param index     is the index of the bucket (table`s cell) that
     *                  stores a HashMapNode with this key-value.
     */
    private void changeExistingKeyValue(K itemKey, V itemValue, int index) {
        HashMapNode<K, V> currentNode = table[index];
        while (currentNode.getNextNode() != null) {
            currentNode = setNewItemValueIfApplicable(itemKey, itemValue, currentNode);
        } // checking last node
        setNewItemValueIfApplicable(itemKey, itemValue, currentNode);
    }

    /**
     * If the program found a HashMapNode with the same key as the
     * key provided by user, it sets new value for this HashMapNode.
     *
     * @param itemKey     is the key whose value shall be changed.
     * @param itemValue   is the new value of the provided key
     *                    that shall substitute the current value.
     * @param currentNode is the HashMapNode whose key shall be
     *                    checked for being equal to the key
     *                    provided by user.
     * @return the next HashMapNode that shall be inspected.
     */
    private HashMapNode<K, V> setNewItemValueIfApplicable(K itemKey, V itemValue, HashMapNode<K, V> currentNode) {
        if (currentNode.getItemKey() == null || itemKey == null) {
            if (currentNode.getItemKey() == null && itemKey == null) {
                currentNode.setItemValue(itemValue);
            }
        } else if (currentNode.getItemKey().equals(itemKey)) {
            currentNode.setItemValue(itemValue);
        }
        return currentNode.getNextNode();
    }

    /**
     * If the current table (an array storing all HashMapNodes) is already full,
     * the program creates a new bigger array and copies all items from the current
     * array to this new array.
     *
     * @return the link to array with all current array`s items copied to.
     */
    @SuppressWarnings("unchecked")
    private HashMapNode<K, V>[] copyItemsToLargerTable() {
        HashMapNode<K, V>[] newTable = new HashMapNode[numberOfBuckets]; // create new array
        for (HashMapNode<K, V> kvHashMapNode : table) {
            if (kvHashMapNode != null) {
                HashMapNode<K, V> currentNode = kvHashMapNode;
                while (currentNode.getNextNode() != null) {
                    saveNewNode(currentNode.getHashCode(), getIndex(currentNode.getHashCode()), currentNode.getItemKey(), currentNode.getItemValue(), newTable);
                    currentNode = currentNode.getNextNode();
                } // save last node of a particular bucket
                saveNewNode(currentNode.getHashCode(), getIndex(currentNode.getHashCode()), currentNode.getItemKey(), currentNode.getItemValue(), newTable);
            }
        }
        return newTable;
    }

    /**
     * Provides a MyHashMap`s item value based on the key
     * requested by user.
     *
     * @param itemKey is the key whose value a user wants to get.
     * @return the value of the key provided by user if any.
     */
    public V get(K itemKey) {
        if (containsKey(itemKey)) {
            return getValueOfParticularKey(itemKey);
        } else {
            return null;
        }
    }

    /**
     * If the the MyHashMap has a particular key provided by user,
     * this method finds the corresponding node and provides the
     * value of this key.
     *
     * @param itemKey is the key whose value a user wants to get.
     * @return the value of the key provided by user if any.
     */
    private V getValueOfParticularKey(K itemKey) {
        int hashCode = getHashCode(itemKey);
        int index = getIndex(hashCode);
        HashMapNode<K, V> currentNode = table[index];
        if (itemKey != null) { // if the key provided is NOT a null
            while (!currentNode.getItemKey().equals(itemKey)) { // while we do not find equal values
                currentNode = currentNode.getNextNode();
            }
        } else { // if the key provided is a null
            while (currentNode.getItemKey() != null) { // while we do not find equal values
                currentNode = currentNode.getNextNode();
            }
        }
        return currentNode.getItemValue();
    }

    /**
     * Says whether a MyHashMap contains a particular key provided by a user
     *
     * @param anyKey is the key to be looked for in the MyHashMap object.
     * @return true if the MyHashMap object contains such key.
     */
    public boolean containsKey(K anyKey) {
        int hashCode = getHashCode(anyKey);
        int index = getIndex(hashCode);
        HashMapNode<K, V> currentNode = table[index];
        if (currentNode != null && numOfAddedObjects > 0) { // if such bucket exists and there are any elements in this MyHashMap
            while (currentNode.getNextNode() != null) {
                if (anyKey == null || currentNode.getItemKey() == null) {
                    if (anyKey == null && currentNode.getItemKey() == null) {
                        return true;
                    }
                    currentNode = currentNode.getNextNode();
                } else if (currentNode.getItemKey().equals(anyKey)) {
                    return true;
                } else {
                    currentNode = currentNode.getNextNode();
                }
            } // checking last node
            if (anyKey == null || currentNode.getItemKey() == null) {
                return anyKey == null && currentNode.getItemKey() == null;
            } else return currentNode.getItemKey().equals(anyKey);
        }
        return false; // node under such index does not exist or no added elements in this MyHashMap
    }

    /**
     * Says whether a MyHashMap contains a particular value provided by a user
     *
     * @param anyValue is the value to be looked for in the MyHashMap object.
     * @return true if the MyHashMap object contains such value.
     */
    public boolean containsValue(V anyValue) {
        for (HashMapNode<K, V> kvHashMapNode : table) {
            if (kvHashMapNode != null) {
                HashMapNode<K, V> currentNode = kvHashMapNode;
                while (currentNode.getNextNode() != null) {
                    if (anyValue == null || currentNode.getItemValue() == null) {
                        if (anyValue == null && currentNode.getItemValue() == null) {
                            return true;
                        }
                        currentNode = currentNode.getNextNode();
                    } else if (currentNode.getItemValue().equals(anyValue)) {
                        return true;
                    } else {
                        currentNode = currentNode.getNextNode();
                    }
                } // checking last node
                if (anyValue == null || currentNode.getItemValue() == null) {
                    if (anyValue == null && currentNode.getItemValue() == null) {
                        return true;
                    }
                } else if (currentNode.getItemValue().equals(anyValue)) {
                    return true;
                }
            }
        }
        return false; // currentNode == null
    }

    /**
     * Removes an item from the MyHashMap object based on its key.
     *
     * @param itemKey is the key that shall be removed from the
     *                MyHashMap along with its value.
     */
    public void remove(K itemKey) {
        if (containsKey(itemKey)) {
            int hashCode = getHashCode(itemKey);
            int index = getIndex(hashCode);
            HashMapNode<K, V> currentNode = table[index];
            if (removeItemFromFirstNodeIfApplicable(itemKey, currentNode, index)) { // checking whether the first node is the one we need
                return;
            } // if the first node is not the node we look for, get next node after it
            MyLinkedList<HashMapNode<K, V>> nodes = removeItemFromOtherThanFirstNode(itemKey, currentNode);
            currentNode = nodes.get(0);
            HashMapNode<K, V> prevNode = nodes.get(1);
            if (currentNode.getNextNode() == null) { // if there is no node after this node
                prevNode.setNextNode(null);
            } else { // if there is any node after this node
                prevNode.setNextNode(currentNode.getNextNode());
            }
            numOfAddedObjects--;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Checks whether it is the first HashMapNode in the bucket that shall
     * be removed. If yes, removes it.
     *
     * @param itemKey     is the key to be looked for in the MyHashMap object.
     * @param currentNode is the HashMapNode that is inspected.
     * @param index       index of a cell where a node shall be removed.
     * @return yes if the node was removed. False if otherwise.
     */
    private boolean removeItemFromFirstNodeIfApplicable(K itemKey, HashMapNode<K, V> currentNode, int index) {
        if (itemKey != null) { // if the key provided is NOT a null
            if (currentNode.getItemKey().equals(itemKey)) { // while we do not find equal values
                changeNodeLinksAfterRemoving(currentNode, index);
                return true;
            }
        } else { // if the key provided is a null
            if (currentNode.getItemKey() == null) { // while we do not find equal values
                changeNodeLinksAfterRemoving(currentNode, index);
                return true;
            }
        }
        return false;
    }

    /**
     * Looks for a node in the bucket that shall be removed.
     *
     * @param itemKey     is the key to be looked for in the MyHashMap object.
     * @param currentNode is the HashMapNode that is inspected.
     * @return a MyLinkedList containing two nodes. The current node has index 0
     * and the previous node has index 1.
     */
    private MyLinkedList<HashMapNode<K, V>> removeItemFromOtherThanFirstNode(K itemKey, HashMapNode<K, V> currentNode) {
        HashMapNode<K, V> prevNode = currentNode;
        currentNode = currentNode.getNextNode();
        if (itemKey != null) { // if the key provided is NOT a null
            while (!currentNode.getItemKey().equals(itemKey)) { // while we do not find equal values
                prevNode = currentNode;
                currentNode = currentNode.getNextNode();
            }
        } else { // if the key provided is a null
            while (currentNode.getItemKey() != null) { // while we do not find equal values
                prevNode = currentNode;
                currentNode = currentNode.getNextNode();
            }
        }
        MyLinkedList<HashMapNode<K, V>> nodes = new MyLinkedList<>();
        nodes.add(currentNode);
        nodes.add(prevNode);
        return nodes;
    }

    /**
     * Changes HashMapNodes` links after removing an item. If the item was
     * the only node in the bucket
     *
     * @param currentNode is the HashMapNode that is inspected.
     * @param index       index of a cell where a node was removed.
     */
    private void changeNodeLinksAfterRemoving(HashMapNode<K, V> currentNode, int index) {
        if (currentNode.getNextNode() == null) { // if there is no node after this node
            table[index] = null;
        } else { // if there is any node after this node
            table[index] = currentNode.getNextNode();
        }
        numOfAddedObjects--;
    }

    /**
     * Creates a new hashcode value for an item added to the MyHashMap.
     *
     * @param key is the key that user wants to add to the MyHashMap.
     * @return the new hashcode for key-value items added to the MyHashCode.
     */
    private int getHashCode(Object key) {
        int nativeHash; // HashCode calculated in a way the Object object provides it
        return (key == null) ? 0 : (nativeHash = key.hashCode()) ^ (nativeHash >>> 16); //numberOfBuckets - was here earlier instead of 16
    }

    /**
     * Gets the index of the bucket (a cell in the table-array) that will
     * store a particular key and its value.
     *
     * @param hash is the hashCode of the key provided.
     * @return the index of the bucket (a cell in the table-array) that will
     * store a particular key and its value
     */
    private int getIndex(int hash) {
        return (numberOfBuckets - 1) & hash;
    }

    /**
     * Says whether the MyHashMap is almost full and shall be rewritten
     * to a larger array.
     *
     * @return true if the MyHashMap is not full yet. False if otherwise.
     */
    private boolean isHashMapFull() {
        return (numOfAddedObjects > (Math.floor(LOAD_FACTOR * numberOfBuckets)));
    }

    /**
     * Saves a node with some key and some value to the MyHashMap object.
     *
     * @param hashCode    is a hashCode for a node.
     * @param index       is the index in the table-array (the index of a bucket)
     *                    where this node shall be saved.
     * @param itemKey     is the key to be saved.
     * @param itemValue   is the value to be saved.
     * @param tableToSave is the table-array to saves the nodes to.
     */
    private void saveNewNode(int hashCode, int index, K itemKey, V itemValue, HashMapNode<K, V>[] tableToSave) {
        if (tableToSave[index] == null) { // If there is no node in this bucket yet
            tableToSave[index] = new HashMapNode<>(hashCode, itemKey, itemValue);
        } else { // If there is at least one node in this bucket
            HashMapNode<K, V> currentNode = tableToSave[index];
            while (currentNode.getNextNode() != null) { // find the last node in this bucket with nextNode == null
                currentNode = currentNode.getNextNode();
            }
            currentNode.setNextNode(new HashMapNode<>(hashCode, itemKey, itemValue));
        }
        numOfAddedObjects++;
    }

    /**
     * Prints MyHashMap`s items (keys and values) to console surrounded
     * by square brackets.
     */
    public void printHashMap() {
        System.out.print("\n[");
        for (HashMapNode<K, V> kvHashMapNode : table) {
            if (kvHashMapNode != null) {
                HashMapNode<K, V> currentNode = kvHashMapNode;
                while (currentNode.getNextNode() != null) {
                    if (!firstNodePrinted) {
                        System.out.print(currentNode.getItemKey() + "=" + currentNode.getItemValue());
                        firstNodePrinted = true;
                    } else {
                        System.out.print(", " + currentNode.getItemKey() + "=" + currentNode.getItemValue());
                    }
                    currentNode = currentNode.getNextNode();
                }
                printLastNode(currentNode);
            }
        }
        System.out.print("]\n");
        firstNodePrinted = false;
    }

    /**
     * Prints the last node in the bucket to console.
     *
     * @param currentNode is the last node in the bucket.
     */
    private void printLastNode(HashMapNode<K, V> currentNode) {
        if (!firstNodePrinted) { // print the last node in the bucket
            System.out.print(currentNode.getItemKey() + "=" + currentNode.getItemValue());
            firstNodePrinted = true;
        } else {
            System.out.print(", " + currentNode.getItemKey() + "=" + currentNode.getItemValue());
        }
    }

    /**
     * Clears the MyHashMap object and resets it to the
     * start values.
     */
    @SuppressWarnings("unchecked")
    public void clear() {
        numOfAddedObjects = 0;
        numberOfBuckets = DEFAULT_NUM_OF_BUCKETS;
        table = (HashMapNode<K, V>[]) new HashMapNode[numberOfBuckets];
    }

    /**
     * Visits all HashMapNodes, collects all key-value pairs and saves
     * them in a LinkedList.
     *
     * @return a LinkedList with all MyHashMap nodes (key-value pairs).
     */
    public MyLinkedList<MyEntry> entrySet() {
        MyLinkedList<MyEntry> set = new MyLinkedList<>();

        for (HashMapNode<K, V> kvHashMapNode : table) {
            if (kvHashMapNode != null) {
                HashMapNode<K, V> currentNode = kvHashMapNode;
                while (currentNode.getNextNode() != null) {
                    MyEntry entry = new MyEntry(currentNode.getItemKey(), currentNode.getItemValue());
                    set.add(entry);
                    currentNode = currentNode.getNextNode();
                } // get the last node from this bucket
                MyEntry entry = new MyEntry(currentNode.getItemKey(), currentNode.getItemValue());
                set.add(entry);
            }
        }
        return set;
    }

    /**
     * Visits all HashMapNodes, collects all keys and saves
     * them in a LinkedList.
     *
     * @return a LinkedList with all MyHashMap keys.
     */
    public MyLinkedList<K> keySet() {
        MyLinkedList<K> keySet = new MyLinkedList<>();

        for (HashMapNode<K, V> kvHashMapNode : table) {
            if (kvHashMapNode != null) {
                HashMapNode<K, V> currentNode = kvHashMapNode;
                while (currentNode.getNextNode() != null) {
                    keySet.add(currentNode.getItemKey());
                    currentNode = currentNode.getNextNode();
                } // get the last node from this bucket
                keySet.add(currentNode.getItemKey());
            }
        }
        return keySet;
    }

    /**
     * Visits all HashMapNodes, collects all values and saves
     * them in a LinkedList.
     *
     * @return a LinkedList with all MyHashMap value.
     */
    public MyLinkedList<V> values() {
        MyLinkedList<V> valuesSet = new MyLinkedList<>();

        for (HashMapNode<K, V> kvHashMapNode : table) {
            if (kvHashMapNode != null) {
                HashMapNode<K, V> currentNode = kvHashMapNode;
                while (currentNode.getNextNode() != null) {
                    valuesSet.add(currentNode.getItemValue());
                    currentNode = currentNode.getNextNode();
                } // get the last node from this bucket
                valuesSet.add(currentNode.getItemValue());
            }
        }
        return valuesSet;
    }

    /**
     * This class describes MyHashMap nodes that contain user data (key-value pairs).
     * MyHashMap object is based on an array[] named table. Each table`s cell can have
     * 0, 1 or more HashMapNodes. Each HashMapNode stands for separate key-value pair.
     * If the program decides to put a HashMapNode to the table`s cell that already has
     * one HashMapNode, the first HashMapNode will be added to this cell as the second
     * HashMapNode`s neighbour (the already existing HashMapNode will have a link pointing
     * at this new HashMapNode) and both of them will be saved in this cell.
     *
     * @param <K> is any type (but primitive) of the key user provided.
     * @param <V> is any type (but primitive) of the value user provided.
     */
    private class HashMapNode<K, V> {

        /**
         * An unique code for a particular HashMapNode object with unique key
         */
        private final int HASH_CODE;

        /**
         * Any object that shall be saved to the MyHashMap object and shall have
         * particular value. The key might be seen as the "name" of the value stored.
         */
        private final K ITEM_KEY;

        /**
         * Any actual information that shall be stored in MyHashMap object.
         * Each value can be accessed by its unique key.
         */
        private V itemValue;

        /**
         * Link to another node stored in the same table`s array cell. If there
         * is not such node and the current node is the single node in a cell,
         * the link points to null.
         */
        private HashMapNode<K, V> nextNode = null;

        /**
         * Creates a new HashMapNode to store user`s data.
         *
         * @param hashCode  is an unique code for a particular HashMapNode object with unique key.
         * @param itemKey   is any object that shall be saved to the MyHashMap object and shall have
         *                  particular value. The key might be seen as the "name" of the value stored.
         * @param itemValue is any actual information that shall be stored in MyHashMap object. Each
         *                  value can be accessed by its unique key.
         */
        private HashMapNode(int hashCode, K itemKey, V itemValue) {
            this.HASH_CODE = hashCode;
            this.ITEM_KEY = itemKey;
            this.itemValue = itemValue;
        }

        /**
         * Provides the next node this current node is pointing at.
         *
         * @return the next node of the current node.
         */
        private HashMapNode<K, V> getNextNode() {
            return nextNode;
        }

        /**
         * Sets the next node for the current node.
         *
         * @param newNode is the node that shall be set as
         *                the next node for the current one.
         */
        private void setNextNode(HashMapNode<K, V> newNode) {
            this.nextNode = newNode;
        }

        /**
         * Provides the value of this node.
         *
         * @return the value of this node
         */
        private V getItemValue() {
            return itemValue;
        }

        /**
         * Sets a new value for a node and deletes the old one.
         *
         * @param anyValue is any new value provided by a user.
         */
        private void setItemValue(V anyValue) {
            this.itemValue = anyValue;
        }

        /**
         * Provides the key of this node.
         *
         * @return the key of this node
         */
        private K getItemKey() {
            return ITEM_KEY;
        }

        /**
         * Provides the hashCode of a node.
         *
         * @return the hashCode of a node.
         */
        private int getHashCode() {
            return HASH_CODE;
        }
    }

    /**
     * Represents a MyEntry object that has only two fields:
     * a key and a value. Those fields are copies of a particular
     * MyHashMap node`s fields. All MyEntry objects are stored
     * in a LinkedList that is used to apply foreach iterator to
     * the MyHashMap object that stores all that data.
     */
    public class MyEntry {
        /**
         * Any type (but primitive) of the key user provided.
         */
        private final K ENTRY_KEY;

        /**
         * Any type (but primitive) of the value user provided.
         */
        private final V ENTRY_VALUE;

        /**
         * Copies a HashMapNode from the MyHashMap object to
         * an LinkedListed to iterate through it.
         *
         * @param key   is any type (but primitive) of the value user provided.
         * @param value is any type (but primitive) of the value user provided.
         */
        public MyEntry(K key, V value) {
            this.ENTRY_KEY = key;
            this.ENTRY_VALUE = value;
        }

        /**
         * Gets the key from a particular HashMapNode.
         *
         * @return the key from a particular HashMapNode
         */
        public K getKey() {
            return ENTRY_KEY;
        }

        /**
         * Gets the value from a particular HashMapNode.
         *
         * @return the value from a particular HashMapNode
         */
        public V getValue() {
            return ENTRY_VALUE;
        }
    }
}
