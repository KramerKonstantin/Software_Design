import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> implements Cache<K, V> {
    protected final Map<K, Node> VALUES = new HashMap<>();

    protected final int CAPACITY;

    protected Node head, tail;

    protected class Node {
        protected Node previous, next;

        public final V VALUE;
        public final K KEY;

        public Node (K key, V value) {
            this.VALUE = value;
            this.KEY = key;
        }
    }

    public LRUCache(int capacity) {
        if (capacity <= 0) {
            String exception = "Capacity can't be non-positive";
            throw new IllegalArgumentException(exception);
        }

        this.CAPACITY = capacity;
    }

    protected void moveToHead(Node node) {
        Node previous = node.previous;
        Node next = node.next;
        if (previous != null && next != null) {
            node.previous.next = next;
            node.next.previous = previous;
        } else if (previous != null) {
            assert tail == node;
            tail = tail.previous;
        } else if (next == null) {
            if (head == null) {
                head = tail = node;
                return;
            }
        } else {
            assert head == node;
            return;
        }

        assert head != null && tail != null;

        head.previous = node;
        node.next = head;
        head = node;
    }

    @Override
    public V get(final K key) {
        Node node = VALUES.get(key);
        if (node == null) {
            return null;
        }

        moveToHead(node);
        assert head == node;

        return node.VALUE;
    }

    @Override
    public void put(final K key, final V value) {
        if (key == null) {
            String exception = "Key can't have NULL value";
            throw new IllegalArgumentException(exception);
        }

        if (VALUES.containsKey(key)) {
            String exception = "Key already exists with value:" + VALUES.get(key).VALUE;
            throw new IllegalStateException(exception);
        }

        if (VALUES.size() >= CAPACITY) {
            assert VALUES.remove(tail.KEY) != null;
            tail = tail.previous;

            assert VALUES.size() < CAPACITY;
        }

        Node newNode = new Node(key, value);
        assert VALUES.put(key, newNode) == null;

        moveToHead(newNode);
        assert head == newNode;
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }

    @Override
    public int getSize() {
        return VALUES.size();
    }

}
