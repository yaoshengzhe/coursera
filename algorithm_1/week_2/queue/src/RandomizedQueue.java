import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int count = 0;

    private int capacity = 128;

    private Item[] buf = null;
    private final Random rand = new Random();

    public RandomizedQueue() {
        buf = (Item[]) new Object[capacity];
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void enqueue(Item item) {
        checkInsertedItemShouldNotNull(item);
        if (size() >= capacity) {
            resize(capacity * 2);
        }
        buf[count++] = item;
    }

    public Item dequeue() {
        checkShouldNotCallRemoveIfQueueIsEmpty();
        int index = rand.nextInt(size());
        swap(index, size() - 1);
        --count;
        Item item = buf[count];
        buf[count] = null;
        if (size() < (capacity / 4)) {
            resize(capacity / 2);
        }
        return item;
    }

    public Item sample() {
        checkShouldNotCallRemoveIfQueueIsEmpty();
        return buf[rand.nextInt(size())];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void resize(int newCapacity) {
        Item[] newBuf = (Item[]) new Object[newCapacity];
        for (int i = 0; i < size(); ++i) {
            newBuf[i] = buf[i];
        }
        buf = newBuf;
        capacity = newCapacity;
    }

    private void swap(int i, int j) {
        Item tmp = buf[i];
        buf[i] = buf[j];
        buf[j] = tmp;
    }

    private void checkInsertedItemShouldNotNull(Item item) {
        if (item == null) {
            throw new NullPointerException("Item should not be null.");
        }
    }

    private void checkShouldNotCallRemoveIfQueueIsEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty.");
        }
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private Item[] randomQueue = null;
        private int currentIndex = 0;

        public RandomizedQueueIterator() {
            randomQueue = (Item[]) new Object[size()];
            Random r = new Random();
            for (int i = 0; i < size(); ++i) {
                randomQueue[i] = buf[i];
                int index = r.nextInt(i + 1);
                Item tmp = randomQueue[index];
                randomQueue[index] = randomQueue[i];
                randomQueue[i] = tmp;
            }
        }

        @Override
        public boolean hasNext() {
            return currentIndex < size();
        }

        @Override
        public Item next() {
            if (currentIndex >= size()) {
                throw new NoSuchElementException("Exceed the end of the queue.");
            }
            return randomQueue[currentIndex++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove item during iterating is not allowed.");
        }
    };
}
