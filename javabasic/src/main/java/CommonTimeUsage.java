package test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

class Test {
    public static void main(String[] args) {
        useLocalDateTime();
    }

    static void timeStamp(){
        //获取时间戳(毫秒, 比如1539789323360)
        long ts = System.currentTimeMillis();
        System.out.println(ts);

        Date date = new Date();
        long ts1 = date.getTime();
        System.out.println(ts1);

        Instant ts2 = Instant.now();
        System.out.println(ts2.toEpochMilli()); //优先使用这个, Java1.8引入的.
    }
    static void useLocalDate(){
        //LocalDate只包含日期, 不包含时间.
        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);  //2018-10-23
        System.out.println(localDate.getYear()); //获取年份, 2018
        System.out.println(localDate.getMonthValue()); //获取月份, 10
        System.out.println(localDate.getDayOfMonth()); //获取日, 23
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        System.out.println(dayOfWeek.getValue()); //获取今天是星期几(1-7)
        System.out.println(localDate.getDayOfYear()); //获取今天是今年的第几天, 296
        LocalDate newdate = localDate.minusDays(1); //获取前天的LocalDate对象, 当然也可以加减年月日等
        LocalDate afterdate = localDate.plusDays(1); //获取明天的LocalDate对象
        LocalDate birthday = LocalDate.of(1990,07, 28); //构造任意日期
        birthday.equals(newdate); //判断两个日期是否相等.equals是LocalDate重写的, 支持传入对象
    }

    static void useLocalTime(){
        //只有时间没有日期
        LocalTime localTime = LocalTime.now();
        System.out.println(localTime); //22:59:14.133
        System.out.println(localTime.getHour()); //获取小时, 22
        System.out.println(localTime.getMinute()); //获取分钟, 59
        System.out.println(localTime.getSecond()); //获取秒, 14(只是整数部分)
    }

    static void useLocalDateTime() {
        //相当于上面2个的结合, 能获取上面的所有属性
        //如果要解析的字符串中包含字母T等, 使用小括号 DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        //时间对象转成字符串
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);
        //字符串转成时间对象
        //如果要解析的字符串中包含字母T等, 使用小括号 CharSequence timeSeq = "2017-09-28T17:07:05Z";
        CharSequence timeSeq = "2017-09-28 17:07:05";
        LocalDateTime ldt = LocalDateTime.parse(timeSeq, df);
        //LocalDateTime转成时间戳, 比如下面是获取当天0点的时间戳
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now()).atOffset(ZoneOffset.of("+8")).toLocalDateTime();
        System.out.println(localDateTime.toInstant(ZoneOffset.of("+8")).getEpochSecond()); //long, 1540742400
        System.out.println(localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli()); //long, 1540742400000
    }
    //获取java.sql.Timestamp时间戳 
    static void localDateTime2Timestamp() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.now()).atOffset(ZoneOffset.of("+8")).toLocalDateTime();
        //数据库中建议使用DateTime类型, 存储范围更大9999, 不建议数据库存储timestamp格式, 因为数据库会根据时区进行处理, 对应Java的Timestamp
        Timestamp ts = Timestamp.valueof(localDateTime);
    }
    //整型时间戳转格式化字符串
    static void timestamp2String() {
        Integer timestamp = 1234567;
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String time = df.format(LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault()));
        System.out.println(time);
    }

}
