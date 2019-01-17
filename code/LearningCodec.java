import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.net.URLCodec;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.UnsupportedEncodingException;

public class LearningCodec {
    /**
     * 工作中有这么一个场景, 制作谷歌的二次验证token, 二维码中的那个secret就是采用的Base32加密的字符串
     * base32/64一般有2个场景:
     * 1. 文本格式比较复杂, 比如一个嵌套多层的Json, 传输的时候容易出问题, 这时候可以采用base64
     * 2. 一些不可见字符容易被网络中间的路由器过滤之类的, 把所有不可见字符转换成可见字符
     */
    public static void base32() throws UnsupportedEncodingException {
        Base32 base32 = new Base32();
        //返回加密后的base32字符串
        System.out.println(base32.encodeAsString("test".getBytes()));
        //返回解密后的字符串
        System.out.println(new String(base32.decode("ORSXG5A="), "UTF-8"));
    }

    static void base64() throws UnsupportedEncodingException {
        Base64 base64 = new Base64();
        //返回加密后的字符串
        System.out.println(base64.encodeAsString("RockyWu".getBytes()));
        //返回解密后的字符串
        System.out.println(new String(base64.decode("Um9ja3lXdQ=="), "UTF-8"));
    }

    static void md5 (){
        //返回RockyWu经过MD5计算后的字符串
        System.out.println(DigestUtils.md5Hex("RockyWu"));
    }

    static void sha256(){
        //返回经过SHA256计算后的字符串
        DigestUtils.sha256Hex("RockyWu");
    }

    /**
     * 可以用来存储用户密码, 据说比SHA256好
     */
    static void bcrypt(){
        String plain_password = "123456";
        String pw_hash = BCrypt.hashpw(plain_password, BCrypt.gensalt());
        String userInput = "123456";
        if(BCrypt.checkpw(userInput, pw_hash)){
            System.out.println("matched!");
        } else {
            System.out.println("Dont match!");
        }
    }

    static void urlCodec() throws EncoderException {
        URLCodec codec = new URLCodec();
        System.out.println(codec.encode("RockyWu"));
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
//        base32();
//        base64();
//md5();
bcrypt();
    }
}
