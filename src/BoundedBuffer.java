class BoundedBuffer<T> {
    private final T[] buffer;
    private int count = 0;
    private int size = 0;
    private int in = 0;
    private int out = 0;

    @SuppressWarnings("unchecked")
    public BoundedBuffer(int size) {
        this.size = size;
        buffer = (T[]) new Object[size];
    }

    public synchronized void put(T item) throws InterruptedException {
        // TODO: Реализуйте метод
        while (count == buffer.length) {
            wait();
        }
        buffer[in] = item;
        in++;
        if (in == size) {
            in = 0;
        }
        count++;
        notifyAll();

    }

    public synchronized T take() throws InterruptedException {
        // TODO: Реализуйте метод
        while (count == 0) {
            wait();
        }
        T obj = buffer[out];
        out++;
        if (out == size) {
            out = 0;
        }
        count--;
        notifyAll();
        return obj;

    }
}

