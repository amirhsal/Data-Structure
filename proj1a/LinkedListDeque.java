public class LinkedListDeque<T> implements Deque<T> {
    private class AnyNode {
        private AnyNode prev;
        private T item;
    private AnyNode next;

    AnyNode(AnyNode p, T i, AnyNode n) {
        prev = p;
        item = i;
        next = n;
    }
}

    private AnyNode sentinel;
    private int size;

    /**
     * Creates an empty linked list deque.
     */
    public LinkedListDeque() {
        sentinel = new AnyNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;

    }

    /**
     * Adds item to the front of the list
     */
    @Override
    public void addFirst(T item) {
        sentinel.next = new AnyNode(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    /**
     * Adds an item at the the end of the list.
     **/
    @Override
    public void addLast(T item) {
        sentinel.prev = new AnyNode(sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size += 1;
    }

    /**
     * Returns true if deque is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    @Override
    public void printDeque() {
        AnyNode r = sentinel.next;
        while (r != sentinel) {
            System.out.print(r.item + " ");
            r = r.next;
        }
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    @Override
    public T removeFirst() {
        if (sentinel.next == sentinel) {
            return null;
        } else {
            T toBeRemove = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return toBeRemove;
        }


    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    @Override
    public T removeLast() {
        if (sentinel == sentinel.next) {
            return null;
        } else {
            T toBeRemove = sentinel.prev.item;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return toBeRemove;
        }
    }

    /**
     * Gets the item at the given index
     */
    @Override
    public T get(int index) {
        int i = 0;
        AnyNode p = sentinel.next;
        while (i != index) {
            p = p.next;
            i += 1;
        }
        return p.item;
    }
    /** Helper method for getRecursive that start with AnyNode p */
    private T getForRec(AnyNode p, int index) {
        if (index == 0) {
            return p.item;
        }
        return getForRec(p.next, index - 1);
    }

    /**
     * Recursively gets the item at the given index
     */

    public T getRecursive(int index) {
        return getForRec(sentinel.next, index);
    }
}
