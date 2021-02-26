import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class Producer implements Runnable{
    BlockingQueue<Integer> fibbNums;
    BlockingQueue<Integer> numsForFibb;

    Producer(BlockingQueue<Integer> fibInput, BlockingQueue<Integer> outPut) {
        fibbNums=fibInput;
        numsForFibb=outPut;
    }

    private int fibRecurs(int n) {
        if (n<2) {
            return n;
        }
        return fibRecurs(n-1) + fibRecurs(n-2);
    }

    @Override
    public void run() {
        System.out.println("Staerdfafsd" + fibbNums.size());
        while (true) {
            try {
                int number = fibbNums.take();
                System.out.println("go for" + number);
                int itemProduced = fibRecurs(number);
                //int itemProduced = fibRecurs(12);
                //int itemProduced = 12;
                numsForFibb.put(itemProduced);
                System.out.println("Produced: "+ itemProduced+ " -- QueueSize: "+fibbNums.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> fibbNums = new ArrayBlockingQueue<>(15);
        BlockingQueue<Integer> numsForFibb = new ArrayBlockingQueue<>(15);
        List<Integer> nums = List.of(4,5,8,12,21,22,34,35,36,37,38,39,42,43);
        numsForFibb.addAll(nums);
        Iterator<Integer> it = numsForFibb.iterator();
        while(it.hasNext()) {
            System.out.println(it.next());
        }
        Thread p1 = new Thread(new Producer(numsForFibb,fibbNums));
        Thread p2 = new Thread(new Producer(numsForFibb,fibbNums));
        Thread p3 = new Thread(new Producer(numsForFibb,fibbNums));
        Thread p4 = new Thread(new Producer(numsForFibb,fibbNums));
        Thread c = new Thread(new Consumer(fibbNums));
        p1.setName("Producer1");
        p2.setName("Producer2");
        p3.setName("Producer3");
        p4.setName("Producer4");
        c.setName("Consumer");
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        c.start();


        /*
        Thread t = new Thread(new Consumer(queue));
        t.start();

        produce(queue);
        produce(queue);
        produce(queue);
        produce(queue);
        produce(queue);
        produce(queue);
//        produce(queue);
//        produce(queue);
//        produce(queue);



        t.join(10000);
        t.interrupt();
        t.join();
        System.out.println("All items Produced and Consumed");

         */

    }
}
