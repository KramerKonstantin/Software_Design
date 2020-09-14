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

        public Node(K key, V value) {
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
            assert tail == node : "A node that doesn't have a next node is a tail";
            tail = tail.previous;
        } else if (next == null) {
            if (tail == null) {
                head = tail = node;
                return;
            }
        } else {
            assert head == node : "A node that doesn't have a previous node is a head";
            return;
        }

        assert head != null && tail != null : "Cache has got some value, head and tail mustn't be null";

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
        assert head == node : "After using the node, it should be in the head";

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

        if (VALUES.size() + 1 > CAPACITY) {
            if (VALUES.remove(tail.KEY) == null) {
                throw new AssertionError("Can't remove values from empty cache");
            }
            tail = tail.previous;

            assert VALUES.size() < CAPACITY : "Cache has a size smaller than constant CAPACITY";
        }

        Node newNode = new Node(key, value);
        if (VALUES.put(key, newNode) != null) {
            throw new AssertionError("If was no mapping for key than must be NULL");
        }

        moveToHead(newNode);
        assert head == newNode : "After using the node, it should be in the head";
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
