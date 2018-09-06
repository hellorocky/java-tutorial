import java.text.MessageFormat;

class LearnString {
    public static void main(String[] args) {
        //子字符串, 包括前面, 不包括后面, 跟Python相比较, 不是很灵活, 不能有负数.
        String name = "RockyWu";
        String firstName = name.substring(5,7);
        //字符串拼接
        String greeting = "你好, ";
        String welcome = greeting+name; //基础的字符串拼接方式, 该方法会自动转换非字符串的类型到字符串类型

        //Python的string format真的很强, 这个还是有点弱.
        //MessageFormat.format不是线程安全的.
        String welcome_1 = MessageFormat.format("''{0}''和''小白''结婚了, 门牌号是{1, number, integer}", name, 208.1);
        System.out.println(welcome_1); //'RockyWu'和'小白'结婚了, 门牌号是208

        //字符串比较,一定要使用qeuals方法, 不能使用==来比较, 因为==比较的是内存地址, equals比较的是内容
        "RockyWu".equals(name); //true, 区分大小写
        "rockywu".equalsIgnoreCase(name); //true, 不区分大小写
        //Java中字符串常量共享在一个大池子中, 字符串变量指向存储池中的相应位置, 但是+和substring等方法产生的结果并不是共享的.
        boolean x = "Rocky" == name.substring(0, 5); //false, 内容虽然一样, 但是内存地址不一样

        //空字符串的判断, 2种方法
        String kong = "";
        if (kong.length() == 0) {}
        if (kong.equals("")) {}

        //null的判断, String变量还可以存放一个特殊对象null, 表示目前没有任何对象与该变量关联.
        if (kong == null) {}
        //检查字符串既不是null也不是空
        if (kong != null && kong.length() != 0) {}

        //以XXX开头或者结尾
        boolean a = name.startsWith("R");
        boolean b = name.endsWith("W");
        //第一次/最后一次出现的位置, 未出现则返回-1
        name.indexOf("y");
        name.lastIndexOf("y");
        //包含未包含, 基于indexOf来实现的
        name.contains("W");
        //字符串替换
        String newName = name.replace("y", "Y");
        System.out.println(newName);
        //全部变成大写/小写, 返回一个新的字符串
        name.toUpperCase();
        name.toLowerCase();

        //删除首位的空格, 官方的包中没有提供参数
        name.trim();
        //用特定符号链接字符串
        System.out.println(String.join("|", "W", "F", "Q"));//W|F|Q

        //构建字符串
        //        //每次使用+连接字符串, 都会构建新的String对象, 当需要连接的字符串数量多的时候, 既耗时又浪费空间
        //所以这个场景下应该使用StringBuilder, StringBuilder和StringBuffer接口一致, 只是StringBuffer是线程安全的, 但是慢;
        //大部分场景构造字符串都是在一个线程中, 所以使用StringBuilder即可.
        StringBuilder builder = new StringBuilder();
        builder.append("ABC");
        builder.append(" DEF");
        String completedString = builder.toString();


    }
}

