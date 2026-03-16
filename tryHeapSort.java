public class tryHeapSort {

    static String[] heap;
    static int heapSize;

    // ─── HEAPIFY DOWN (shared by both builds and sort) ───
    static void heapifyDown(int i) {
        int smallest = i;
        int l = 2 * i + 1, r = 2 * i + 2;
        if (l < heapSize && heap[l].compareTo(heap[smallest]) < 0) smallest = l;
        if (r < heapSize && heap[r].compareTo(heap[smallest]) < 0) smallest = r;
        if (smallest != i) {
            String tmp = heap[i]; heap[i] = heap[smallest]; heap[smallest] = tmp;
            heapifyDown(smallest);
        }
    }

    // ─── HEAPIFY UP (used by top-down build) ───
    static void heapifyUp(int i) {
        int parent = (i - 1) / 2;
        while (i > 0 && heap[parent].compareTo(heap[i]) > 0) {
            String tmp = heap[i]; heap[i] = heap[parent]; heap[parent] = tmp;
            i = parent;
            parent = (i - 1) / 2;
        }
    }

    // ─── (a) BOTTOM-UP BUILD — O(n) ───
    static void buildBottomUp(String[] words) {
        heap = words.clone();
        heapSize = heap.length;
        // Start from last non-leaf and heapify down each node
        for (int i = heapSize / 2 - 1; i >= 0; i--)
            heapifyDown(i);
    }

    // ─── (b) TOP-DOWN BUILD — O(n log n) ───
    static void buildTopDown(String[] words) {
        heap = new String[words.length];
        heapSize = 0;
        for (String w : words) {
            heap[heapSize] = w;
            heapSize++;
            heapifyUp(heapSize - 1);
        }
    }

    // ─── HEAP SORT — extracts min repeatedly (shared) ───
    static String[] heapSort() {
        String[] sorted = new String[heapSize];
        int total = heapSize;
        for (int i = 0; i < total; i++) {
            sorted[i] = heap[0];           // min is always at root
            heap[0] = heap[heapSize - 1];  // move last to root
            heapSize--;
            heapifyDown(0);                // restore heap
        }
        return sorted;
    }

    public static void main(String[] args) throws Exception {

        // ── SMALL TEST (≤20 words) to verify correctness ──
        String[] small = {"zebra","mango","apple","fig","banana",
                "cherry","date","elderberry","grape","kiwi"};

        System.out.println("=== Small Test: Bottom-Up ===");
        buildBottomUp(small);
        String[] s1 = heapSort();
        for (String s : s1) System.out.print(s + " ");
        System.out.println();

        System.out.println("=== Small Test: Top-Down ===");
        buildTopDown(small);
        String[] s2 = heapSort();
        for (String s : s2) System.out.print(s + " ");
        System.out.println();

        // ── FULL RUN on Ulysses ──
        String[] words = WordCleaner.getWords("C:\\Users\\Cscience1\\Downloads\\ulysses.text");
        System.out.println("\nTotal unique words loaded: " + words.length);

        // Bottom-up timing
        long start = System.nanoTime();
        buildBottomUp(words);
        String[] result1 = heapSort();
        long end = System.nanoTime();
        long timeBottomUp = (end - start) / 1_000_000;

        // Top-down timing
        start = System.nanoTime();
        buildTopDown(words);
        String[] result2 = heapSort();
        end = System.nanoTime();
        long timeTopDown = (end - start) / 1_000_000;

        // ── RESULTS ──
        System.out.println("\n=== Timing Results ===");
        System.out.println("Bottom-Up build + sort: " + timeBottomUp + " ms");
        System.out.println("Top-Down build + sort:  " + timeTopDown  + " ms");

        System.out.println("\nFirst n sorted words (Bottom-Up):");
        for (int i = 0; i < words.length; i++) System.out.println("  " + result1[i]);

        System.out.println("\nFirst n sorted words (Top-Down):");
        for (int i = 0; i < words.length; i++) System.out.println("  " + result2[i]);
    }
}
