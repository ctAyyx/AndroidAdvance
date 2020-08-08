package ct.com.basics.io;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 关于 InputStream 和 OutputStream
 *
 * 读写流的最小单元是 字节
 */
public class InputAndOutPutDemo {

    private static final String filePath = "./app/src/main/java/A.txt";
    private static final File file = new File(filePath);

    public static void main(String[] args) {
        //直接读写数据
//        writeToFile();
//        readyFromFile();

        //使用缓冲读写数据
//        writeToFileByBuffer();
//        readyFromFileByBuffer();

        //使用Data读取数据
//        writeToFileByData();
//        readFromFileByData();

        //使用Object读取对象
        writeToFileByObject();
        readFromFileByObject();
    }

    /**
     * 将byte写出到文件
     */
    private static void writeToFile() {

        try {
            FileOutputStream ous = new FileOutputStream(new File(filePath));
            ous.write("直接使用FileOutputStream写出数据".getBytes());
            ous.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件以字节流读入
     */
    private static void readyFromFile() {

        try {

            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);

            System.out.println("读取的数据：" + new String(bytes));
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 上面一个字节一个字节的写出数据效率很低,所以加入缓冲区 一次写出多个数据
     * 这里就用到了装饰器模式
     * <p>
     * 对OutputStream的写出功能进行加强
     */
    private static void writeToFileByBuffer() {

        try {
            FileOutputStream ous = new FileOutputStream(new File(filePath));
            //使用缓存流
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(ous, 1024 * 1024);
            bufferedOutputStream.write("OutputStream一个字节一个字节的写出数据效率很低,所以加入缓冲区 一次写出多个数据".getBytes());
            bufferedOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 将二进制流读入缓冲区 每次调用read的时候 从缓冲区读取数据 如果失败 再从硬盘读取数据到缓冲区 再返给用户
     */
    private static void readyFromFileByBuffer() {

        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(
                    new FileInputStream(file)
            );


            byte[] bytes = new byte[10];
            int read = 0;

            while ((read = bufferedInputStream.read(bytes)) != -1) {
                //这样是没有意义的 因为会出现乱码的情况
                String str = new String(bytes, 0, read);
                System.out.println("读取的数据:" + str);

            }
            bufferedInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 还可以按照一定的格式来写出数据
     */
    private static void writeToFileByData() {

        try {
            FileOutputStream ous = new FileOutputStream(new File(filePath));
            //使用缓存流
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(ous, 1024 * 1024);
            //使用Data流
            DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);

            dataOutputStream.writeBoolean(true);
            dataOutputStream.writeUTF("使用DataOutputStream来格式化的写出数据");
            dataOutputStream.writeInt(100);
            dataOutputStream.writeLong(5555555555555L);
            dataOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 使用DataInputStream读取文件时 需要按照写出的顺序读取 否则会出现乱码
     */
    private static void readFromFileByData() {

        try {
            DataInputStream dataInputStream = new DataInputStream(
                    new BufferedInputStream(new FileInputStream(file))
            );


            Boolean b = dataInputStream.readBoolean();
            String str = dataInputStream.readUTF();
            int i = dataInputStream.readInt();
            long l = dataInputStream.readLong();
            dataInputStream.close();

            System.out.println("读取的数据:" + b + "  " + str + "  " + i + "  " + l);

        } catch (Exception e) {

        }
    }


    /**
     * 将一个对象输出到文件上 使用ObjectOutputStream 需要序列化的对象要实现Serializable接口
     */
    private static void writeToFileByObject() {

        try {


            Person person = new Person("张三", 18, true, 176.0);


            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                    new BufferedOutputStream(new FileOutputStream(file))
            );

            objectOutputStream.writeObject(person);
            objectOutputStream.close();


        } catch (Exception e) {

        }

    }

    private static void readFromFileByObject() {

        try {

            ObjectInputStream objectInputStream = new ObjectInputStream(
                    new BufferedInputStream(new FileInputStream(file))
            );

            Person person= (Person) objectInputStream.readObject();
            System.out.println("读取的对象数据:"+person);

        } catch (Exception e) {

        }

    }


    private static class Person implements Serializable {

        private String name;
        private int age;
        private boolean sex;
        private double height;

        public Person() {
        }

        public Person(String name, int age, boolean sex, double height) {
            this.name = name;
            this.age = age;
            this.sex = sex;
            this.height = height;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", sex=" + sex +
                    ", height=" + height +
                    '}';
        }
    }

}
