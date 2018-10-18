import com.sun.tools.doclets.formats.html.SourceToHTMLConverter;

import javax.sound.midi.Soundbank;
import java.util.Date;

public class CommonTimeUsage {
    public static void main(String[] args) {
        DataUsage();
    }

    static void DataUsage(){
        //Date是JDK1.0版本引入的时间相关的模块, 一般很少使用了
        Date date = new Date();
        System.out.println(date.getTime()); //毫秒时间戳
    }
}
