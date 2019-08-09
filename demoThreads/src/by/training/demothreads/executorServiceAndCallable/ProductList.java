package by.training.demothreads.executorServiceAndCallable;

import java.util.ArrayDeque;

public final class ProductList {
    private ProductList() {
    }
    /**
     * deque which contains different products.
     */
    private static ArrayDeque<String> arr = new ArrayDeque<String>() {
        {
            this.add("Product 1");
            this.add("Product 2");
            this.add("Product 3");
            this.add("Product 4");
            this.add("Product 5");
        }
    };

    /**
     * Get method.
     *
     * @return first product from the deque
     */
    public static String getProduct() {
        return arr.poll();
    }
}
