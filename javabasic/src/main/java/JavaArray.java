import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

/**
 * java-tutorial
 *1. Java中的数组要求组内的元素具有相同的数据类型, 即一个数组内只能存储一种数据类型的数据.可以是继承的类型.
 *2. 数组一旦创建后, 数组的长度是不能改变的.即使清空该数组, 数组的长度也不变
 *3. 数组本身也是一种数据类型, 属于引用类型.数组中既可以存储基本类型, 也可以存储引用类型.二维数组就是数组中存储数组类型
 *4. 数组可以通过下标来访问数组中的元素, 比如a[1].
 * @author RockyWu
 * @date 2018/9/17
 */
public class JavaArray {
    public static void main(String[] args) {
//        fillArray();
        sortArray();
    }

    private static void createArray() {
        //方法一
        int[] a = new int[100];
        //方法二
        int[] b = {1,2,3,4};
    }

    private static void fillArray() {
        //不管使用任何一种方式初始化数组, 只要定义了数组, 数组中就会有值.
        //如果没有显性设置数组的初始值, 系统初始化的时候的规律是, 数值型为0(或者0.0等)
        //引用类型为null
        int[] a = new int[100];
        for (int i = 0; i < a.length; i++) {
            a[i] = i*2;
        }
        //或者工作中更常见的是方法返回的是数组, 直接赋值即可填充.

    }

    private static void iterArray() {
        int[] a = new int[100];
        //下面是foreach的写法, 比较简洁, 如果需要使用索引的时候就需要用到传统的for循环了.
        for (int i:a) {
            System.out.println(i);
        }
    }

    private static void copyArray() {
        int[] a = {1,2,3,4,5};
        //第一个参数是原始数组, 第二个参数是新数组的长度
        //这个方法通常用来增加数组的大小, 因为数组一旦定义好了以后打小就不能改变了.
        //如果长度比之前大, 系统会根据类型来补全, 比如整型会使用0补全.
        //如果长度小于之前的长度, 那么只取前面的.
        int[] b = Arrays.copyOf(a, 3);
    }

    private static void sortArray() {
        Student[] persons = new Student[3];
        persons[0] =new Student("tom",1,88,45);
        persons[1] =new Student("jack",6,80,12);
        persons[2] =new Student("bill",4,68,21);

        System.out.println("排序前的数据：");
        for (Student student:persons) {
            System.out.println(student);
        }

        SortByNumber sortByNumber = new SortByNumber();
        Arrays.sort(persons, sortByNumber);

        System.out.println("排序后的数据：");
        for (Student student:persons) {
            System.out.println(student);
        }



    }



}



class Student{
    private String name;
    private int number;
    private int score;
    private int age;

    public Student(String name,int number,int score,int age){
        this.name = name;
        this.number = number;
        this.score = score;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student[name:"+name+",age:"+age+",number:"+number+",score:"+score+"]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

class SortByNumber implements Comparator<Student>{
// 该类实现Comparator，重写该接口的compare()
// 重写了compare方法, 前者减去后者表示从小到大
    @Override
    public int compare(Student o1, Student o2) {
        return o1.getNumber()-o2.getNumber();
    }
}