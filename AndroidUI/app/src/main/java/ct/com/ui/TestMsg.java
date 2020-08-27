package ct.com.ui;

import android.util.Log;

public class TestMsg {

    public TestMsg() {
        Log.e("TestMsg", "创建TestMsg");
    }

    public static final Object sPoolSync = new Object();

    private static TestMsg sPool;

    TestMsg next;

    public static TestMsg obtain() {

        synchronized (sPoolSync) {
            if (sPool != null) {
                TestMsg m = sPool;
                sPool = m.next;
                Log.e("Obtain", "--->" + sPool);
                m.next = null;
                return m;
            }
        }

        return new TestMsg();
    }


    public void recycler() {
        synchronized (sPoolSync) {

            next = sPool;
            sPool = this;

        }
    }
}
