public interface MyCache<K, V> {
    /**
     * Returns value from cache associated with given key if it exists.
     * Otherwise, returns <b>null</b>.
     *
     * This operations takes O(1) computation time.
     *
     * @param key some value for getting access to value
     *
     * @return value associated with this key if it exists;
     * null otherwise
     */
    V get(K key);

    /**
     * Add new key-value pair in a {@link LRUCache}.
     * If number of such pairs is more than {@link #getCapacity() capacity}
     * of cache then the oldest pairs will be removed from cache.
     *
     * This operation takes O(1) computation time.
     *
     * @param key some value that is used for getting access
     * @param value something associated with key
     *
     * @throws IllegalAccessException if key is NULL value
     * or if pair with such key already exists in cache
     */
    void put(K key, V value);

    /**
     * Returns constant value that refers to maximum number
     * of elements in cache line before some pair will be
     * removed from it in {@link #put(Object, Object) put} method.
     *
     * This operation takes O(1) computation time.
     *
     * @return maximum number of entries in cache line
     */
    int getCapacity();

    /**
     * Returns current number of elements in cache line.
     *
     * This operation takes O(1) computation time.
     *
     * @return number of elements in cache line
     */
    int getSize();
}
