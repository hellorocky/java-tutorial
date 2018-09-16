import java.io.File;
import java.io.IOException;

/**
 * java-tutorial
 *
 * @author RockyWu
 * @date 2018/9/16
 * <p>
 * File类只是用来处理文件/文件夹相关的操作, 不涉及文件内容的处理, 该类从JDK1.0就已经引入了.
 * 学习编程, 做好最快的办法就是自己去写代码, 去实践, 去总结.
 */
public class ClassicIO {
    static final String homeDir = System.getProperty("user.home");

    public static void main(String[] args) throws IOException {
//        createFile();
//        deleteFile();
//        createDir();
//        getAttr();
        File file = new File(homeDir);
        iterDir(file);

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
            File[] dirList = file.listFiles();
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





}
