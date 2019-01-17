import java.io.*;


/*
* File类是Java中负责操作文件/文件夹的类, 具有很多方法, 但是不能操作具体的文件内容.
*
* */
class Test {
    public static void main(String[] args) {
//        props();
        create();
        //递归遍历目录
        String dir = "/Users/rocky/tmp/123";
        listDir(dir);
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

    private static void listDir(String dir) {
            System.out.println(dir + "是目录");
            File file = new File(dir);
            File[] files = file.listFiles();
            System.out.println("目录中文件/文件夹个数为: " + files.length);
            for (File file1 : files) {
    
                if (file1.isDirectory()) {
                    listDir(file1.getPath());
                } else {
                    System.out.println(file1.getPath());
                }
    
            }
        }


}




class LearnFile {

    private void test1() throws IOException {
        //递归遍历目录
        String testFile = "/Users/rocky/tmp/123/test.txt";
        File file = new File(testFile);
        if (!file.exists()) {
            file.createNewFile();
        }
        String message = "我要学会Java编程!\n";
        //追加的方式打开文件
        OutputStream outputStream = new FileOutputStream(file, true);
        outputStream.write(message.getBytes());
        outputStream.close();

    }
    private static void copyFile() throws IOException {
        String fileName = "/Users/rocky/tmp/test.py";
        String newFileName = "/Users/rocky/tmp/test_1.py";
        File file = new File(fileName);
        File file1 = new File(newFileName);
        if (file.exists()) {
            InputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = new FileOutputStream(file1);
            //每次读取的字节内容
            int n;
            while ((n = inputStream.read()) != -1) {
                outputStream.write(n);
            }
            inputStream.close();
            outputStream.close();
        }
    }
    private static void readOnebyOne() throws IOException {
        File file = new File("/Users/rocky/tmp/test.py");
        InputStream inputStream = new FileInputStream(file);
        int length = inputStream.available();
        for (int i=0; i<length; i++) {
            System.out.println((char) inputStream.read() + "\n");
        }

    }

    private static void readText() throws IOException {
        File file = new File("/Users/rocky/tmp/test.py");
        InputStream inputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String data;
        while ((data=reader.readLine()) != null) {
            System.out.println(data);
        }
    }


}

