package com.shpp.p2p.cs.adavydenko.assignment17;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

/**
 * Launches various test for the MyHashMap object to test whether
 * it functions without errors.
 */
public class TestMyHashMap {

    /**
     * Launches the test.
     */
    public void launchTest() {
        System.out.println("------------------ LAUNCH MyHashMap TESTING");
        analyzePicture();
        removeItems();
        testContainsMethods();
        testGetMethod();
        testForEach();
        System.out.println("\n------------------ END OF TEST");
    }

    /**
     * Analyzes first 32 * 1024 bytes extracted from the test picture.
     * Prints to console what bytes and how many of them exactly did
     * the program find in the first buffer of the extracted bytes.
     */
    private void analyzePicture() {
        System.out.println("\n-------------------");
        System.out.println("Testing multiple put-method calls, the program saves many bytes from an image to a MyHashMap");
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream("testMyHashMap.png"));
            MyHashMap<Byte, Integer> hm = new MyHashMap<>();
            byte[] buffer = new byte[32 * 1024];
            int len; // The number of bytes extracted from the file
            len = bis.read(buffer);
            for (int i = 0; i < len; i++) {
                if (hm.size() < 256) { // If still not all 256 possible bytes are already in the array
                    if (hm.containsKey(buffer[i])) { // If the array has such byte
                        hm.put(buffer[i], hm.get(buffer[i]) + 1);
                    } else { // if the array does not have such byte
                        hm.put(buffer[i], 1);
                    }
                } else { // If all 256 possible bytes are already in the array
                    hm.put(buffer[i], hm.get(buffer[i]) + 1);
                }
            }

            System.out.println("Size of the MyHashMap: " + hm.size());
            for (MyHashMap<Byte, Integer>.MyEntry b : hm.entrySet()) {
                System.out.print(b.getKey() + "=" + b.getValue() + " ");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a MyHashMap consisting of 200 elements and deletes
     * each second element (only those with even numbers as keys).
     */
    private void removeItems() {
        System.out.println("\n-------------------");
        System.out.println("Testing remove method");
        MyHashMap<Integer, String> hm = new MyHashMap<>();
        System.out.println("First we create a HashMap of size 200");
        for (int i = 0; i < 200; i++) {
            hm.put(i, "A-" + i);
        }
        System.out.println("HashMap size: " + hm.size());
        hm.printHashMap();
        System.out.println("Now we delete each second item (all items with keys being even numbers)");
        for (int i = 0; i < 200; i += 2) {
            hm.remove(i);
        }
        System.out.println("HashMap size: " + hm.size());
        hm.printHashMap();
        hm.clear();
        System.out.println("Create new HashMap with 13 items");
        for (int i = 0; i < 10; i++) {
            hm.put(i, "A-" + i);
        }
        hm.put(null, "A-000");
        hm.put(11, null);
        hm.put(12, "A-12");
        System.out.println("HashMap size: " + hm.size());
        hm.printHashMap();
        System.out.println("Now we delete the item with key of null");
        hm.remove(null);
        System.out.println("HashMap size: " + hm.size());
        hm.printHashMap();
    }

    /**
     * Test containsValue and containsKey methods.
     */
    private void testContainsMethods() {
        System.out.println("\n-------------------");
        System.out.println("Testing containsKey and containsValue methods");
        System.out.println("The HashMap looks like following");
        MyHashMap<Integer, String> hm = new MyHashMap<>();
        for (int i = 0; i < 10; i++) {
            hm.put(i, "A-" + i);
        }
        hm.put(null, "A-456");
        hm.put(12, null);
        for (int i = 13; i < 18; i++) {
            hm.put(i, "A-" + i);
        }
        hm.printHashMap();
        System.out.println("HashMap size: " + hm.size());
        System.out.println("Contains key 3? - " + hm.containsKey(3));
        System.out.println("Contains key 29? - " + hm.containsKey(29));
        System.out.println("Contains key null? - " + hm.containsKey(null));
        System.out.println("Contains key 17? - " + hm.containsKey(17));
        System.out.println("Contains value A-3? - " + hm.containsValue("A-3"));
        System.out.println("Contains value A-29? - " + hm.containsValue("A-29"));
        System.out.println("Contains value null? - " + hm.containsValue(null));
        System.out.println("Contains value A-17? - " + hm.containsValue("A-17"));
    }

    /**
     * Test get method.
     */
    private void testGetMethod() {
        System.out.println("\n-------------------");
        System.out.println("Testing get method");
        System.out.println("The HashMap looks like following");
        MyHashMap<Integer, String> hm = new MyHashMap<>();
        for (int i = 0; i < 5; i++) {
            hm.put(i, "B-" + i);
        }
        hm.put(null, "B-456");
        hm.put(7, null);
        hm.put(8, "B-8");

        System.out.println("HashMap size: " + hm.size());
        hm.printHashMap();
        for (int i = 0; i < 5; i++) {
            System.out.println("Item with key of " + i + " - " + hm.get(i));
        }
        System.out.println("Item with key of null -" + hm.get(null));
        for (int i = 7; i < 9; i++) {
            System.out.println("Item with key of " + i + " - " + hm.get(i));
        }
    }

    /**
     * Test foreach iterator.
     */
    private void testForEach() {
        System.out.println("\n-------------------");
        System.out.println("Testing foreach iterator");
        System.out.println("The HashMap looks like following");
        MyHashMap<Integer, String> hm = new MyHashMap<>();
        for (int i = 0; i < 10; i++) {
            hm.put(i, "C-" + i);
        }
        System.out.println("HashMap size: " + hm.size());
        hm.printHashMap();

        System.out.println("Now we display all keys");
        for (Integer integer : hm.keySet()) {
            System.out.print(integer + " ");
        }
        System.out.println("\n");

        System.out.println("Now we display all values");
        for (String str : hm.values()) {
            System.out.print(str + " ");
        }
        System.out.println("\n");

        System.out.println("Now we display all key-value pairs");
        for (MyHashMap<Integer, String>.MyEntry entry : hm.entrySet()) {
            System.out.print("[Key: " + entry.getKey() + ", value: " + entry.getValue() + "] ");
        }
        System.out.println("\n");

        System.out.println("Array cleared");
        hm.clear();
        hm.printHashMap();
        System.out.println("HashMap size: " + hm.size());
    }
}
