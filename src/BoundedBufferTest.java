import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class BoundedBufferTest {
    public static void main(String[] args) throws InterruptedException {
        final int NUM_THREADS = 12;

        Thread[] threads = new Thread[NUM_THREADS];
        BoundedBuffer buffer = new BoundedBuffer(10);
        Random random = new Random();


        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch finishSignal = new CountDownLatch(NUM_THREADS);

        for (int i = 0; i < NUM_THREADS; i++) {
            final int n = i + 1;
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        startSignal.await();
                        for (int j = 0; j < 10; j++) {
                            int rand = Math.abs(random.nextInt()) % 1000;
                            if (n % 2 == 0) {
                                System.out.println("положили: " + rand);
                                buffer.put(rand);
                            } else {
                                System.out.println("убрали: " + buffer.take());
                            }
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    finishSignal.countDown();
                }
            });
            threads[i].start();
        }
        startSignal.countDown();
        finishSignal.await();
    }
}
