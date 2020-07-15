package ct.com.basics.io;

import java.io.File;
import java.io.RandomAccessFile;

/**
 *
 */
public class RandomAccessFileDemo {

    private static final String filePath = "./app/src/main/java/C.txt";
    private static final File file = new File(filePath);

    //假设这是远程的二进制流数据
    private static final String res = "{\n" +
            "    \"data\":[\n" +
            "        {\n" +
            "            \"_id\":\"5e59ec146d359d60b476e621\",\n" +
            "            \"coverImageUrl\":\"http://gank.io/images/b9f867a055134a8fa45ef8a321616210\",\n" +
            "            \"desc\":\"Always deliver more than expected.\\uff08Larry Page\\uff09\",\n" +
            "            \"title\":\"Android\",\n" +
            "            \"type\":\"Android\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\":\"5e59ed0e6e851660b43ec6bb\",\n" +
            "            \"coverImageUrl\":\"http://gank.io/images/d435eaad954849a5b28979dd3d2674c7\",\n" +
            "            \"desc\":\"Innovation distinguishes between a leader and a follower.\\uff08Steve Jobs\\uff09\",\n" +
            "            \"title\":\"\\u82f9\\u679c\",\n" +
            "            \"type\":\"iOS\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\":\"5e5a25346e851660b43ec6bc\",\n" +
            "            \"coverImageUrl\":\"http://gank.io/images/c1ce555daf954961a05a69e64892b2cc\",\n" +
            "            \"desc\":\"The man who has made up his mind to win will never say \\u201c Impossible\\u201d\\u3002\\uff08 Napoleon \\uff09\",\n" +
            "            \"title\":\"Flutter\",\n" +
            "            \"type\":\"Flutter\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\":\"5e5a254b6e851660b43ec6bd\",\n" +
            "            \"coverImageUrl\":\"http://gank.io/images/4415653ca3b341be8c61fcbe8cd6c950\",\n" +
            "            \"desc\":\"Education is a progressive discovery of our own ignorance. \\uff08 W. Durant \\uff09\",\n" +
            "            \"title\":\"\\u524d\\u7aef\",\n" +
            "            \"type\":\"frontend\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"_id\":\"5e5a25716e851660b43ec6bf\",\n" +
            "            \"coverImageUrl\":\"http://gank.io/images/c3c7e64f0c0647e3a6453ccf909e9780\",\n" +
            "            \"desc\":\"Do not, for one repulse, forgo the purpose that you resolved to effort. \\uff08 Shakespeare \\uff09\",\n" +
            "            \"title\":\"APP\",\n" +
            "            \"type\":\"app\"\n" +
            "        }],\n" +
            "    \"status\":100\n" +
            "}\n";

    public static void main(String[] args) {
        //writeToFile();

        //模拟断点下载 开启3个线程
        String str01 = res.substring(0, 30);
        String str02 = res.substring(30, 90);
        String str03 = res.substring(90);

        writeToFile02(str01,0);
        writeToFile02(str02,30);
        writeToFile02(str03,90);

    }

    private static void writeToFile() {

        try {


            RandomAccessFile randomAccessFile01 = new RandomAccessFile(file, "rw");
            RandomAccessFile randomAccessFile02 = new RandomAccessFile(file, "rw");
            RandomAccessFile randomAccessFile03 = new RandomAccessFile(file, "rw");

            String str01 = "RandomAccessFile的使用";
            String str02 = "RandomAccessFile可以指定读写的位置,这样我们就可以实现断点续传的功能";
            String str03 = "RandomAccessFile可以指定文件的长度";

            byte[] byte01 = str01.getBytes();
            byte[] byte02 = str02.getBytes();
            byte[] byte03 = str03.getBytes();


            randomAccessFile01.seek(0);
            randomAccessFile01.write(byte01);
            randomAccessFile02.seek(byte01.length);
            randomAccessFile02.write(byte02);
            randomAccessFile03.seek(byte02.length + byte01.length);
            randomAccessFile03.write(byte03);


        } catch (Exception e) {
        }

    }

    private static void writeToFile02(final String msg, final int seek) {

        Thread thread01 = new Thread() {
            @Override
            public void run() {
                try {
                    RandomAccessFile randomAccessFile01 = new RandomAccessFile(file, "rw");
                    randomAccessFile01.seek(seek);
                    randomAccessFile01.write(msg.getBytes());
                    randomAccessFile01.close();
                    System.out.println(Thread.currentThread().getName() + "下载完成");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread01.start();

    }
}
