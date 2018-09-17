import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Set;

/**
 * java-tutorial
 *
 * 这里的代码主要是Java1.7中关于File类的增强类的相关API使用
 * Java1.0中引入的File类, 有一些缺点:
 * 1. 很多情况都是返回true或者false而没有抛出异常, 不好确定操作失败的原因
 * 2. 不支持软连接
 * 3. 没有支持文件复制/移动的方法
 * 4. 没有遍历目录的原生方法
 * @author RockyWu
 * @date 2018/9/16
 */
public class ClassicIOImproved {
    private static String homeDir = System.getProperty("user.home");
    public static void main(String[] args) throws IOException {
//        readFile();
//        commonMethod();
        writeFile();
    }

    private static void commonMethod() throws IOException {
        Path path = Paths.get(homeDir + "/tmp/test.txt");
        Path destPath = Paths.get(homeDir + "/tmp/test.txt.copy");
        //获取文件名称
        path.getFileName();
        //获取父路径
        path.getParent();
        //Path to File对象
        path.toFile();
        //类似于Python中的join方法
        Path base = Paths.get(homeDir);
        Path absPath = base.resolve("tmp/test.txt");
        //判断两个路径是否相同
        Files.isSameFile(path, absPath);
        //创建文件, 目录不存在的时候会抛出异常, 而且没发现有类似mkdirs的方法.文件存在也会抛异常.
        Files.createFile(path);
        //复制文件
        Files.copy(path, destPath);
    }

    private static void readFile() throws IOException {
        Path path = Paths.get(homeDir + "/tmp/test.txt");
        BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
        String line;
        while ((line=reader.readLine()) != null) {
            System.out.println(line);
        }
    }

    private static void writeFile() throws IOException {
        Path path = Paths.get(homeDir + "/tmp/test.txt");
        BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
        //写文件会清空之前的记录.
        writer.write("你好, Java, 你好!");
        //添加换行
        writer.newLine();
        writer.flush();
        writer.close();
        //方法二
        String contentToAppend = "好好学习天天向上!";
        Files.write(path, contentToAppend.getBytes(), StandardOpenOption.APPEND);
        //其他的参考https://www.baeldung.com/java-append-to-file

    }

    private static void createTemp() throws IOException {
        //在Java中要创建临时文件还需要自己处理关闭, 有3个方法
        //1. 处理完以后主动关闭
        //2. 添加addShutdownHook回掉函数, 看着挺复杂
        //3. Path转换成File, 使用File.deleteOnExists()方法, 这个方法是在JVM正常退出的情况下会删除临时文件.不靠谱
        //不如Python中的with语句
        //https://stackoverflow.com/questions/28752006/alternative-to-file-deleteonexit-in-java-nio
        Path path = Files.createTempDirectory("prefix_string");
        Files.deleteIfExists(path);

    }
}
