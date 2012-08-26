import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        public Item item;
        public Node prev;
        public Node next;

        public Node(Item item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    private
    Node head = null;
    private Node tail = null;
    private int count = 0;

    public Deque() {
        head = new Node(null, null, null);
        tail = new Node(null, head, null);
        head.next = tail;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public int size() {
        return this.count;
    }

    public void addFirst(Item item) {
        checkInsertedItemShouldNotNull(item);
        head.next = head.next.prev = new Node(item, head, head.next);
        ++count;
    }

    public void addLast(Item item) {
        checkInsertedItemShouldNotNull(item);
        tail.prev = tail.prev.next = new Node(item, tail.prev, tail);
        ++count;
    }

    public void removeFirst() {
        checkShouldNotCallRemoveIfQueueIsEmpty();
        head.next = head.next.next;
        head.next.prev = head;
        --count;
    }

    public void removeLast() {
        checkShouldNotCallRemoveIfQueueIsEmpty();
        tail.prev = tail.prev.prev;
        tail.prev.next = tail;
        --count;
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {

            private Node cur = head;
            @Override
            public boolean hasNext() {
                return cur.next != tail;
            }

            @Override
            public Item next() {
                cur = cur.next;
                return cur.item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove item during iterating is not allowed.");
            }

        };
    }

    private void checkInsertedItemShouldNotNull(Item item) throws NullPointerException {
        if (item == null) {
            throw new NullPointerException("Item should not be null.");
        }
    }

    private void checkShouldNotCallRemoveIfQueueIsEmpty() throws NoSuchElementException {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
    }
}