import java.util.NoSuchElementException;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;


public class Deque_T {

    private Deque<Integer> queue = null;
    @Before
    public void setUp() {
        queue = new Deque<Integer>();
    }

    @Test
    public void testBaiscQueueOperation() {
        Assert.assertTrue(queue.isEmpty());
        queue.addFirst(4);
        Assert.assertFalse(queue.isEmpty());
        Assert.assertEquals(1, queue.size());

        queue.addLast(5);
        Assert.assertFalse(queue.isEmpty());
        Assert.assertEquals(2, queue.size());
        queue.addLast(6);
        queue.addLast(7);
        queue.addLast(8);
        queue.addLast(9);
        queue.addFirst(3);
        queue.addFirst(2);
        queue.addFirst(1);
        queue.addFirst(0);

        queue.removeFirst();
        queue.removeLast();
        queue.removeFirst();
        queue.removeLast();
        queue.removeFirst();
        queue.removeLast();
        queue.removeFirst();
        queue.removeLast();
        queue.removeFirst();
        queue.removeLast();

        for (Integer i : queue) {
            System.out.println(i);
        }
    }

    @Test(expected=NoSuchElementException.class)
    public void testRemoveIfQueueIsEmpty() {
        queue.removeFirst();
    }

    @Test(expected=NullPointerException.class)
    public void testAddNullObject() {
        queue.addFirst(null);
    }
}
