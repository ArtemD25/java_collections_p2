package com.shpp.p2p.cs.adavydenko.assignment17;

/**
 * This class launches test for two custom classes - MyHashMap and MyPriorityQueue.
 * <p>
 * --- Calculating a hashCode and an index in the MyHashCode internal array for a new item
 * https://javarush.ru/groups/posts/2496-podrobnihy-razbor-klassa-hashmap
 * --- Iterator and Entry objects
 * https://stackoverflow.com/questions/3110547/java-how-to-create-new-entry-key-value
 * https://javarush.ru/groups/posts/1884-pattern-iterator
 * --- Priority heap
 * https://www.youtube.com/watch?v=noQ4SUoqrQA&t=126s&ab_channel=EvgeniyMalovEvgeniyMalov
 */
public class Assignment17Part1 {

    /**
     * Launches testing of MyHashMap and MyPriorityQueue classes.
     *
     * @param args command line arguments are not provided.
     */
    public static void main(String[] args) {
        TestMyHashMap testHM = new TestMyHashMap();
        testHM.launchTest();

        TestMyPriorityQueue testPQ = new TestMyPriorityQueue();
        testPQ.launchTest();
    }
}
