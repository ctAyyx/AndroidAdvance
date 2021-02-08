package ct.com.basics.thread.course04;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class CustomLock implements Lock {

    private CustomSync sync = new CustomSync();
//    private CustomReSync sync = new CustomReSync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    /**
     * 我们当前的这个锁是不可重入的
     */
    static class CustomSync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            System.out.println(Thread.currentThread()+"tryAcquire:" + arg);
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }

            return false;

        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0)
                throw new IllegalArgumentException("不能再次释放锁了");
            System.out.println("tryRelease:" + arg);
            setState(getState() - 1);
            setExclusiveOwnerThread(null);
            return true;
        }
    }

    static class CustomReSync extends AbstractQueuedSynchronizer {

        @Override
        protected boolean tryAcquire(int arg) {
            System.out.println("tryAcquire:" + arg);
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }

            //继续判断 拿到锁的线程是否是当前线程
            if (getExclusiveOwnerThread() == Thread.currentThread()) {
                //如果当前线程是获取了锁的线程
                //需要将 state ++ 因为需要释放锁
                setState(getState() + 1);
                return true;
            }

            return false;

        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0)
                throw new IllegalArgumentException("不能再次释放锁了");
            System.out.println("tryRelease:" + arg);
            setState(getState() - 1);
            //如果state 等于 0
            //表示持有锁的线程已经释放了锁
            if (getState() == 0)
                setExclusiveOwnerThread(null);
            return true;
        }
    }

}
