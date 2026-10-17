
public class FavoritesPQ {

    private Contact[] heap = new Contact[16];
    private int size;

    public void insert(Contact c) {
        if (size == heap.length) {
            Contact[] bigger = new Contact[heap.length * 2];
            for (int i = 0; i < heap.length; i++) {
                bigger[i] = heap[i];
            }
            heap = bigger;
        }
        heap[size] = c;
        siftUp(size);
        size++;
    }

    public Contact extractMax() {
        if (size == 0) return null;
        Contact top = heap[0];
        size--;
        heap[0] = heap[size];
        heap[size] = null;
        if (size > 0) siftDown(0);
        return top;
    }



    
    public boolean remove(Contact c) {
        for (int i = 0; i < size; i++) {
            if (heap[i] == c) {
                size--;
                Contact last = heap[size];
                heap[size] = null;
                if (i < size) {
                    heap[i] = last;
                    siftUp(i);
                    siftDown(i);
                }
                return true;
            }
        }
        return false;
    }





    
    public Contact[] topK(int k) {
        FavoritesPQ copy = new FavoritesPQ();
        copy.heap = new Contact[heap.length];
        for (int i = 0; i < size; i++) {
            copy.heap[i] = heap[i];
        }
        copy.size = size;

        int n = Math.min(k, size);
        Contact[] result = new Contact[n];
        for (int i = 0; i < n; i++) {
            result[i] = copy.extractMax();
        }
        return result;
    }

    private void siftUp(int i) {
        while (i > 0) {
            int parent = (i - 1) / 2;
            if (heap[i].getCallCount() > heap[parent].getCallCount()) {
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int largest = i;
            if (left < size && heap[left].getCallCount() > heap[largest].getCallCount()) {
                largest = left;
            }
            if (right < size && heap[right].getCallCount() > heap[largest].getCallCount()) {
                largest = right;
            }
            if (largest == i) break;
            swap(i, largest);
            i = largest;
        }
    }

    private void swap(int a, int b) {
        Contact t = heap[a];
        heap[a] = heap[b];
        heap[b] = t;
    }
}
