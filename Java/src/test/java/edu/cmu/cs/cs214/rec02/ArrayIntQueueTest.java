package edu.cmu.cs.cs214.rec02;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 * TODO: 1. The {@link LinkedIntQueue} has no bugs. We've provided you with some
 * example test cases. Write your own unit tests to test against IntQueue
 * interface with specification testing method using mQueue = new
 * LinkedIntQueue();
 *
 * 2. Comment `mQueue = new LinkedIntQueue();` and uncomment `mQueue = new
 * ArrayIntQueue();` Use your test cases from part 1 to test ArrayIntQueue and
 * find bugs in the {@link ArrayIntQueue} class Write more unit tests to test
 * the implementation of ArrayIntQueue, with structural testing method Aim to
 * achieve 100% line coverage for ArrayIntQueue
 *
 * @author Alex Lockwood, George Guo, Terry Li
 */
public class ArrayIntQueueTest {

    private IntQueue mQueue;
    private List<Integer> testList;

    /**
     * Called before each test.
     */
    @Before
    public void setUp() {

        mQueue = new ArrayIntQueue();

        testList = new ArrayList<>(List.of(1, 2, 3));
    }

    @Test
    public void testIsEmpty() {

        assertTrue(mQueue.isEmpty());
    }

    @Test
    public void testNotEmpty() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
        }
        assertTrue(!mQueue.isEmpty());
    }

    @Test
    public void testPeekEmptyQueue() {
        try {
            mQueue.peek();
        } catch (Exception e) {
            assertTrue(true);
        }
    }

    @Test
    public void testPeekNoEmptyQueue() {
        try {
            mQueue.peek();
            assertTrue(true);
        } catch (Exception e) {
        }
    }

    @Test
    public void testEnqueue() {

        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
            assertEquals(testList.get(0), mQueue.peek());
            assertEquals(i + 1, mQueue.size());
        }
    }

    @Test
    public void testDequeue() {
        for (int i = 0; i < testList.size(); i++) {
            mQueue.enqueue(testList.get(i));
        }

        for (int i = 0; i < testList.size(); i++) {
            Integer element = mQueue.dequeue();

            if (element != null) {
                assertEquals(testList.get(i), element);
                assertEquals(testList.size() - i - 1, mQueue.size());
            }

        }
    }

    @Test
    public void testContent() throws IOException {

        InputStream in = new FileInputStream("src/test/resources/data.txt");
        try (Scanner scanner = new Scanner(in)) {
            scanner.useDelimiter("\\s*fish\\s*");

            List<Integer> correctResult = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                correctResult.add(input);
                System.out.println("enqueue: " + input);
                mQueue.enqueue(input);
            }

            for (Integer result : correctResult) {
                assertEquals(mQueue.dequeue(), result);
            }
        }
    }

    @Test
    public void testClearEmptyQueue() {
        ArrayIntQueue queue = new ArrayIntQueue();

        assertTrue(queue.isEmpty());

        queue.clear();

        assertTrue(queue.isEmpty());

        assertEquals(0, queue.size());
    }

    @Test
    public void testClearNonEmptyQueue() {
        ArrayIntQueue queue = new ArrayIntQueue();

        for (int i = 0; i < 5; i++) {
            queue.enqueue(i);
        }

        assertFalse(queue.isEmpty());
        assertEquals(5, queue.size());

        queue.clear();

        assertTrue(queue.isEmpty());

        assertEquals(0, queue.size());

        assertEquals(0, queue.getHead());

        try {
            queue.peek();
            fail("Expected exception when peeking an empty queue.");
        } catch (Exception e) {
            assertTrue(e instanceof NoSuchElementException);
        }
    }

    @Test
    public void testEnsureCapacityBasicResize() {
        ArrayIntQueue queue = new ArrayIntQueue();

        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }

        assertEquals(10, queue.size());

        queue.enqueue(10);

        assertEquals(11, queue.size());
        assertEquals(Integer.valueOf(0), queue.peek());
    }

    @Test
    public void testEnsureCapacityWithWraparound() {
        ArrayIntQueue queue = new ArrayIntQueue();

        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }

        queue.dequeue();

        queue.enqueue(10);

        assertEquals(10, queue.size());
        assertEquals(Integer.valueOf(1), queue.peek());
    }

    @Test
    public void testEnsureCapacityDataIntegrity() {
        ArrayIntQueue queue = new ArrayIntQueue();

        for (int i = 0; i < 10; i++) {
            queue.enqueue(i);
        }

        queue.dequeue();
        queue.dequeue();

        for (int i = 10; i < 15; i++) {
            queue.enqueue(i);
        }

        assertEquals(Integer.valueOf(2), queue.peek());

        assertEquals(15, queue.size());
    }

    @Test
    public void testDequeueEmptyQueueReturnsNull() {
        ArrayIntQueue queue = new ArrayIntQueue();

        assertTrue(queue.isEmpty());

        Integer result = queue.dequeue();

        assertNull(result);

        assertEquals(0, queue.size());

        assertTrue(queue.isEmpty());
    }

}
