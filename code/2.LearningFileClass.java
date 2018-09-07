import java.io.File;
import java.io.IOException;


/*
* File类是Java中负责操作文件/文件夹的类, 具有很多方法, 但是不能操作具体的文件内容.
*
* */
public class Test {
    public static void main(String[] args) {
//        props();
        create();
    }

    public static void props() throws IOException {
        //实例化一个File对象, 这是常用的初始化方法
        File file = new File("/Users/rocky/tmp/bsz.jpg");
        //判断该文件或者文件夹是否存在
        file.exists();
        //判断该对象是否是一个目录
        file.isDirectory();
        //判断该对象是否是一个文件
        file.isFile();
        //判断文件/文件夹是否可读写执行, 其实就是Linux中的权限的映射
        file.canRead();
        file.canWrite();
//        file.canExecute();
        //如果是文件获取文件的名称, 如果是目录获取最后一层的目录名称
        file.getName();
        //获取路径名称字符串
        file.getPath();
        //是否为隐藏
        file.isHidden();

    }

    public static void create(){
        File file = new File("/Users/rocky/tmp/123/456");
        //创建新的文件, 如果文件不存在且有权限创建成功则返回true, 如果存在则返回false.
        try {
            file.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //创建目录, 成功则返回true, 失败则返回false, mkdirs相当于-p, 可以递归创建不存在的目录
        file.mkdir();
        file.mkdirs();
    }
}

