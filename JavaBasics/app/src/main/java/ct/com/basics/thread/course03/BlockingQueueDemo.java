package ct.com.basics.thread.course03;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import ct.com.basics.CLog;

/**
 * 阻塞队列(BlockingQueue)
 * 非阻塞方法
 * add      remove 操作失败 抛出异常
 * offer    poll   操作失败 返回false或Null
 * 阻塞方法
 * put      take
 *
 * <p>
 * ArrayBlockingQueue      一个有数组结构组成的有界阻塞队列
 * LinkedBlockingQueue     一个由链表结构组成的有界阻塞队列
 * PriorityBlockingQueue   一个支持优先级排序的无界阻塞队列(就是元素要实现Comparable接口)
 * DelayQueue              一个支持延迟获取的无界阻塞队列
 * SynchronousQueue        一个不存储元素的阻塞队列(生产线程生产的数据需要有一个消费线程来消费)
 * LinkedTransferQueue     一个由链表结构组成的无界阻塞队列(生产线程生产的数据可以尝试将该数据发送给一个消费者,如果不成功,再添加入队列)
 * LinkedBlockingDeque     一个由链表结构组成的双向阻塞队列
 */
public class BlockingQueueDemo {

    public static void main(String[] args) {
        //ArrayBlockingQueue
        //useArrayBlockingQueue();

        useLinkedBlockingQueue();
    }

    private static void useArrayBlockingQueue() {

        //capacity  队列的容量
        //fair 是否是公平锁 主要是针对锁
        final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(16, true);

        ProducerThread producer01 = new ProducerThread(queue, 3000);
        ProducerThread producer02 = new ProducerThread(queue, 3000);

        ConsumerThread consumer01 = new ConsumerThread(queue);
        ConsumerThread consumer02 = new ConsumerThread(queue);

        producer01.start();
        producer02.start();
        consumer01.start();
        consumer02.start();

    }


    private static void useLinkedBlockingQueue() {
        //capacity  队列的容量
        final LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>(16);

        ProducerThread producer01 = new ProducerThread(queue, 3000);
        ProducerThread producer02 = new ProducerThread(queue, 3000);

        ConsumerThread consumer01 = new ConsumerThread(queue);
        ConsumerThread consumer02 = new ConsumerThread(queue);

        producer01.start();
        producer02.start();
        consumer01.start();
        consumer02.start();
    }


    private static class ProducerThread extends Thread {
        private BlockingQueue<String> queue;
        private long time;

        public ProducerThread(BlockingQueue<String> queue) {
            this(queue, 300);
        }

        public ProducerThread(BlockingQueue<String> queue, long time) {
            this.queue = queue;
            this.time = time;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(time);
                    queue.put(Thread.currentThread().getName() + "的产品");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ConsumerThread extends Thread {
        private BlockingQueue<String> queue;
        private long time;

        public ConsumerThread(BlockingQueue<String> queue) {
            this(queue, 300);
        }

        public ConsumerThread(BlockingQueue<String> queue, long time) {
            this.queue = queue;
            this.time = time;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String product = queue.take();
                    CLog.log("消费者获取的产品:" + product);
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
