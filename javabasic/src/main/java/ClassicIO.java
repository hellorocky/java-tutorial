import java.io.*;

/**
 * java-tutorial
 *
 * @author RockyWu
 * @date 2018/9/16
 * <p>
 * File类只是用来处理文件/文件夹相关的操作, 不涉及文件内容的处理, 该类从JDK1.0就已经引入了.
 * 学习编程, 做好最快的办法就是自己去写代码, 去实践, 去总结.
 * 经典的IO中, Java把所有的输入和输出都模拟成流,流中的数据只能顺序访问.不能回退访问, 如需回退, 可以使用pushback
 */
public class ClassicIO {
    static final String homeDir = System.getProperty("user.home");

    public static void main(String[] args) throws IOException {
//        createFile();
//        deleteFile();
//        createDir();
//        getAttr();
//        File file = new File(homeDir);
//        iterDir(file);
//        writeString();
//        readStringBuffer();
        randomReadWrite();

    }

    private static void createFile() throws IOException {
        //这里不是很方便, Java不会自己处理目录的拼接, tmp前面必须自己添加/
        File file = new File(homeDir + "/tmp/test.txt");
        //创建成功后返回true, 存在的时候(不管是同名目录还是同名文件)返回false, 不会修改目前已经存在的文件
        //如果没有权限则抛出异常
        if (!file.exists()) {
            boolean isCreated = file.createNewFile();
            System.out.println(isCreated);
        }

    }

    private static void deleteFile() {
        File file = new File(homeDir + "/tmp/test.txt");
        if (file.exists()) {
            //删除文件或者目录
            file.delete();
        }
    }

    private static void createDir() {
        File file = new File(homeDir + "/tmp/abc/def/test.txt");
        //这个方法是创建多级目录的, 类似于mkdir -p参数, 最后那个test.txt也会创建成目录.
        file.mkdirs();
    }

    private static void getAttr() throws IOException {
        File file = new File(homeDir + "/tmp/abc/def");
        //是否是文件
        file.isFile();
        //是否是文件夹
        file.isDirectory();
        //是否为隐藏文件
        file.isHidden();
        //是否可读写执行
        file.canRead();
        file.canWrite();
        file.canExecute();
        //如果是文件获取文件的名称, 如果是目录获取最后一层的目录名称
        file.getName();
        //获取绝对路径
        file.getPath();
        //获取文件的大小(字节), 如果是目录的话也是目录的大小, 不过不准确, 4K的那个.
        file.length();
        //获取父路径(String, File)
        file.getParent();
        file.getParentFile();
    }

    private static void iterDir(File file) {
        if (file.exists()) {
            //定义一个过滤器, 过滤结尾包含py的文件/文件夹
            FileFilter fileFilter = new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.getName().endsWith("py");
                }
            };

            File[] dirList = file.listFiles(fileFilter);
            if (dirList != null) {
                for (File f : dirList) {
                    if (f.isFile()) {
                        System.out.println(f.getName() + ": " +f.length());
                    } else {
                        iterDir(f);
                    }
                }
            }
        }
    }

    private static void createTmp() throws IOException {
        //创建临时文件, 未指定目录的话, 默认读取的System.out.println(System.getProperty("java.io.tmpdir"));
        File tempFile = File.createTempFile("test", ".py");
        //当JVM正常退出的时候删除文件.
        tempFile.deleteOnExit();
    }

    private static void copyFile() throws IOException {
        File sourceFile = new File(homeDir + "/tmp/source.py");
        File destFile = new File(homeDir + "/tmp/dest.py");
        FileInputStream fileInputStream = new FileInputStream(sourceFile);
        FileOutputStream fileOutputStream = new FileOutputStream(destFile);
        //每次读取的字节内容
        int b;
        while ((b = fileInputStream.read()) != -1) {
            fileOutputStream.write(b);
        }
        fileInputStream.close();
        fileOutputStream.close();
    }

    private static void setAttr() {
        File file = new File(homeDir + "/tmp/abc/def/test.txt");
        //设置文件属性, 用到的概率比较小.
        file.setReadable(true, false);
        file.setWritable(true, false);
        file.setExecutable(true, false);
    }

    private static void writeString() throws IOException {
        //字符流写文件
        String welcome = "你好, Java!";
        FileWriter fileWriter = new FileWriter(homeDir + "/tmp/test.txt");
        //文件不存在的时候会自动创建
        fileWriter.write(welcome);
        //忘记关闭后会出现写不进去的现象.
        fileWriter.close();
    }

    private static void readString() throws IOException {
        FileReader fileReader = new FileReader(homeDir + "/tmp/test.txt");
        //默认的读取char的方式
        char[] chars = new char[100];
        fileReader.read(chars);
        System.out.println(chars);
    }

    private static void readStringBuffer() throws IOException {
        FileReader fileReader = new FileReader(homeDir + "/tmp/test.txt");
        //使用bufferedReader按行读取, 使用buffer以后, 会先读取到buffer缓冲区中,默认的buffer size是8192
        BufferedReader bufferedReader = new BufferedReader(fileReader, 2048);
        String line;
        while ((line=bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
    }

    private static void randomReadWrite() throws IOException {
        RandomAccessFile file = new RandomAccessFile(homeDir + "/tmp/test.txt", "rw");
        file.seek(8);
        //获取游标的位置
        long pointer = file.getFilePointer();
        System.out.println(pointer);
        System.out.println(file.readLine());
    }


}
