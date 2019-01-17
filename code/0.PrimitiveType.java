class PrimitiveType {
    static final String name = "RockyWu";  //类常量, 在main方法外面声明, 该类下面的所有方法都可以使用, 但是别的类不能使用
    public static final double PI = 3.14;  //类常量, 在main方法外面声明, 该类下面的所有方法都可以使用, 别的类也能使用
    //枚举类型
    public enum Color {
        RED, BLUE, PINK
    }

    public static void main(String[] args) {
        //Java有8种基本类型, 4种整型, 2种浮点型, 1种字符型, 1种bool型
        //Java的整型都是有符号的, 所以每种类型占用的字节数跟系统无关, 完美跨平台.
        byte a = 1; //占1个字节
        short b = 2; //占2个字节
        int c = 2; //占4个字节, 常用
        long d = 4L; //占4个字节
        //浮点数不适用于无法接受摄入误差的金融计算, 因为浮点数采用二进制系统表示, 二进制系统中无法精确表示分数1/10等.这就好像十进制无法精确表示1/3一样.
        //如果要在数值计算中不允许有任何误差, 应该使用BigDecimal
        double e = 0.01; //占8个字节
        float f = 0.1f; //占4个字节

        char g = 'a'; //一般不建议直接在代码中使用char, 因为char描述的是UTF-16编码中的一个代码单元.
        boolean h = false; //Java中不能使用非空字符串, 非零数字等表示true, 应该明确使用boolean类型表示

        final double PI = 3.14; //不提倡在一行中声明多个变量

        //强制类型转换
        double i = 9.998;
        int ni = (int) i; //9, 将小数点后面的值截断获取整数

        double j = 9.998;
        int nj = (int) Math.round(j); //10, 四舍五入

        double k = i<j ? i:j; //三目运算符, 返回i和j中较小的那个

        Color color = Color.BLUE;

    }
}
