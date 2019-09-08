public interface Deque<Item> {
    /* Adds item to the front of the list */
    public void addFirst(Item item);

    /**
     * Adds an item at the the end of the list.
     **/
    public void addLast(Item item);

    /* Returns true if deque is empty, false otherwise. */
    public boolean isEmpty();

    /* Returns the number of items in the deque. */
    public int size();

    /* Prints the items in the deque from first to last, separated by a space. */
    public void printDeque();
    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public Item removeFirst();

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public Item removeLast();

    /* Gets the item at the given index. */
    public Item get(int index);

    /* Gets the item at the given index, recursively */
    public Item getRecursive(int i);

}

