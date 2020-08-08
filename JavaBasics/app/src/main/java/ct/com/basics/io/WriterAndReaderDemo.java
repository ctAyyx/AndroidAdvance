package ct.com.basics.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 字符流 Writer Reader
 * <p>
 * 读写流的最小单元是 字符
 */
public class WriterAndReaderDemo {


    private static final String filePath = "./app/src/main/java/B.txt";
    private static final File file = new File(filePath);

    public static void main(String[] args) {
        //使用字符流读写文件
//        writerToFile();
//        readFromFile();

        //使用Buffer读写数据
        writeToFileByBuffer();
        readFromFileByBuffer();

    }


    /**
     * 通过OutputStreamWriter 将数据已字符的写入文件
     */
    private static void writerToFile() {
        try {

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    new FileOutputStream(file), "GBK"
            );

            outputStreamWriter.write("使用字符流写出到文件");
            outputStreamWriter.close();
        } catch (Exception e) {

        }
    }

    /**
     * 通过 InputStreamReader读取文件数据
     * 注意 读取的时候要和文件的编码格式一致
     */
    private static void readFromFile() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(
                    new FileInputStream(file),"GBK"
            );
            char[] chars = new char[10];
            inputStreamReader.read(chars, 0, chars.length);

            System.out.println(new String(chars));


        } catch (Exception e) {
        }
    }

    private static void writeToFileByBuffer() {
        try {

            BufferedWriter bufferedWriter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file), "GBK")

            );

            bufferedWriter.write("使用BufferWriter来写出数据");
            bufferedWriter.newLine();
            bufferedWriter.write("新的一行");
            bufferedWriter.close();
        } catch (Exception e) {
        }
    }

    private static void readFromFileByBuffer() {
        try {

            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file),"GBK"
                    )
            );

            //读一行数据
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("通过BufferReader读取的数据:" + line);
            }

        } catch (Exception e) {

        }
    }


}
