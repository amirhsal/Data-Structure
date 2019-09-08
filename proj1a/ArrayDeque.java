public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst = 4;
    private int nextLast = 5;

    /**
     * Creates an empty Array deque.
     */
    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
    }

    /**
     * Resizing the array to the target capacity.
     */
    private void resizing(int capacity) {
        T[] a = (T[]) new Object[capacity];
        int i = 0;
        while (i < size) {
            nextFirst += 1;
            a[i] = items[(nextFirst % items.length)];
            i += 1;
        }
        items = a;
        nextLast = size;
        nextFirst = items.length - 1;

    }

    /**
     * Adds item to the front of the list
     */
    public void addFirst(T item) {
        if (size == items.length) {
            resizing(items.length * 2);
        }
        if (nextFirst < 0) {
            nextFirst = items.length - 1;
        }
        items[nextFirst % items.length] = item;
        nextFirst -= 1;
        size += 1;
    }

    /**
     * Adds an item at the the end of the list.
     **/
    public void addLast(T item) {
        if (size == items.length) {
            resizing(items.length * 2);
        }
        items[nextLast % items.length] = item;
        nextLast = nextLast % items.length + 1;
        size += 1;
    }

    /**
     * Returns true if deque is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the deque.
     */
    public int size() {
        if (size < 0) {
            size = 0;
        }
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque() {
        int i = 0;
        while (i != items.length) {
            if (items[i] != null) {
                System.out.print(items[i] + " ");
            }
            i += 1;
        }
    }

     /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T toBeRemove = items[(nextFirst + 1) % items.length];
        items[(nextFirst + 1) % items.length] = null;
        size -= 1;
        nextFirst = nextFirst % items.length + 1;
        double efficiency = 100 * size / items.length;
        if (items.length > 17 && efficiency < 25) {
            resizing(items.length / 2);
        }
        return toBeRemove;
    }


     /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
      */
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nextLast = (nextLast + items.length - 1) % items.length;
        T toBeRemove = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        double efficiency = 100 * size / items.length;
        if (items.length > 17 && efficiency < 25) {
            resizing(items.length / 2);
        }
        return toBeRemove;
    }

     /**
     * Gets the item at the given index
     */


    public T get(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        int g = nextFirst;
        int j = 0;
        while (j <= index) {
            j += 1;
            g = (g + 1) % items.length;
        }
        return items[g];
    }
}
