package com.shpp.p2p.cs.adavydenko.assignment17;

/**
 * Launches various test for the MyPriorityQueue object to test whether
 * it functions without errors.
 */
public class TestMyPriorityQueue {

    /**
     * Launches the test.
     */
    public void launchTest() {
        System.out.println("------------------ LAUNCH MyPriorityQueue TESTING");
        testAddingItems();
        testAddingEqualItems();
        testAddingString();
        testObjectCompare();
        testForEach();
        System.out.println("\n------------------ END OF TEST");
    }

    /**
     * Tests adding and removing integers from the MyPriorityQueue.
     */
    private void testAddingItems() {
        System.out.println("\n-------------------");
        System.out.println("Tests adding 17 different integers to priority queue");
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>();
        for (int i = 17; i > 0; i--) {
            pq.add(i);
        }
        System.out.println("The queue looks like that");
        pq.printQueue();
        System.out.println("Size: " + pq.size());
        System.out.println("Now we remove all objects from the queue in following order");
        while (pq.size() != 0) {
            System.out.print(pq.poll() + " ");
        }
        pq.printQueue();
        System.out.println("Size: " + pq.size());
    }

    /**
     * Tests adding and removing equal items from the MyPriorityQueue.
     */
    private void testAddingEqualItems() {
        System.out.println("\n-------------------");
        System.out.println("Tests adding 20 equal integers (int = 10) to priority queue");
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>();
        for (int i = 0; i < 20; i++) {
            pq.add(10);
        }
        System.out.println("The queue looks like that");
        pq.printQueue();
        System.out.println("Size: " + pq.size());
        System.out.println("Now we remove all objects from the queue in following order");
        while (pq.size() != 0) {
            System.out.print(pq.poll() + " ");
        }
        pq.printQueue();
        System.out.println("Size: " + pq.size());
    }

    /**
     * Tests adding and removing Strings from the MyPriorityQueue.
     */
    private void testAddingString() {
        System.out.println("\n-------------------");
        System.out.println("Tests adding String objects to priority queue");
        MyPriorityQueue<String> pq = new MyPriorityQueue<>();
        pq.add("Kyiv");
        pq.add("Berlin");
        pq.add("Tokyo");
        pq.add("Paris");
        pq.add("Prague");
        pq.add("Madrid");
        pq.add("Washington");
        pq.add("Warsaw");
        System.out.println("The queue looks like that");
        pq.printQueue();
        System.out.println("Size: " + pq.size());
        System.out.println("Now we remove all objects from the queue in following order");
        while (pq.size() != 0) {
            System.out.print(pq.poll() + " ");
        }
        pq.printQueue();
        System.out.println("Size: " + pq.size());
    }

    /**
     * Test whether MyPriorityQueue can compare custom objects.
     */
    private void testObjectCompare() {
        System.out.println("\n-------------------");
        System.out.println("Tests adding TestBox objects to priority queue. Those objects have the only property - weight");
        MyPriorityQueue<TestBox> pq = new MyPriorityQueue<>();
        for (int i = 0; i < 10; i++) {
            TestBox box = new TestBox(i);
            pq.add(box);
            System.out.println("Box #" + i + ", weight " + box.getWeight() + " ");
        }
        System.out.println("\nNow we extract TestBoxes from the priority queue in following order: ");
        while (pq.size() != 0) {
            System.out.println("Box weight " + pq.poll().getWeight() + " ");
        }
        pq.printQueue();
        System.out.println("Size: " + pq.size());
    }

    /**
     * Tests foreach iterator for MyPriorityQueue.
     */
    private void testForEach() {
        System.out.println("\n-------------------");
        System.out.println("Tests adding TestBox objects to priority queue. Those objects have the only property - weight");
        MyPriorityQueue<TestBox> pq = new MyPriorityQueue<>();
        for (int i = 10; i >= 0; i--) {
            TestBox box = new TestBox(i);
            pq.add(box);
            System.out.println("NewBox #" + i + ", weight " + box.getWeight() + " ");
        }
        System.out.println("\nNow we launch foreach iterator for this MyPriorityQueue");
        for (TestBox box : pq) {
            System.out.println("Box weight: " + box.getWeight() + " ");
        }
        System.out.println("\nThe order is not preserved but it is ok since PriorityQueue has not to preserve order while using foreach");
    }

    /**
     * A class created to test how does the MyPriorityQueue compares
     * objects.
     */
    class TestBox implements Comparable<TestBox> {

        /**
         * The weight of a box.
         */
        private final int weight;

        /**
         * Creates a TestBox with a certain value of weight.
         *
         * @param weight is weight of a certain box in kg.
         */
        private TestBox(int weight) {
            this.weight = weight;
        }

        /**
         * Provides the weight of a TestBox.
         *
         * @return the weight of a TestBox
         */
        private int getWeight() {
            return this.weight;
        }

        /**
         * Compares two TestBox objects based on their weight values.
         *
         * @param box is the box that the current box shall be compared with.
         * @return if this method returns a negative numeric, the current object will be
         * positioned before the one provided as an argument. If the method returns a
         * positive numeric, the provided object will be positioned after the second object.
         * If the method returns zero, both objects are equal.
         */
        @Override
        public int compareTo(TestBox box) {
            return box.weight > this.weight ? -1 : 1;
        }
    }
}
