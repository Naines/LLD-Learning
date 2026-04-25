KV Store with TTL – Requirements

Assumptions:
- The system is an in-memory key-value store.
- Each entry has a time-to-live (TTL) after which it expires.
- Expired entries should not be returned when queried.
- The store must support concurrent access (multi-threaded environment).
- Persistence and distributed coordination are out of scope.
- Eviction can be lazy (on access) or proactive (background cleanup).

APIs:
1. void put(K key, V value, long ttlMillis)
    - Stores a key-value pair with a TTL.
    - After ttlMillis, the entry should be considered expired.

2. V get(K key)
    - Retrieves the value if it exists and has not expired.
    - Returns null if the key is missing or expired.

3. void remove(K key)
    - Deletes a key explicitly from the store.

4. boolean containsKey(K key) [optional]
    - Checks if a key exists and is not expired.

5. int size() [optional]
    - Returns the number of active (non-expired) entries.

Focus Areas:
- TTL Management
    - Each entry must store its expiry timestamp.
    - Expiry can be checked on get or cleaned up periodically.

- Concurrency
    - Use thread-safe collections (ConcurrentHashMap).
    - Ensure multiple threads can safely read/write without race conditions.

- Eviction Strategy
    - Lazy eviction: remove expired entries when accessed.
    - Proactive eviction: background thread scans and removes expired entries.
    - Trade-off: lazy is simple, proactive keeps memory clean.

- Extensibility
    - Could later add persistence (DB, Redis).
    - Could support distributed TTL management.
    - Could expose metrics (hit/miss, eviction count).
