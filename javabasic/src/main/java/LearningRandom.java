import java.util.Random;

public class LearningRandom {
    /**
     * 获取随机数字字符串, 一般的场景是短信验证码等
     */
    static void getRandomNumString(){
        Integer length = 6;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            stringBuilder.append(((int) Math.floor(Math.random() * 10)));
        }
        System.out.println(stringBuilder.toString());
    }

    public static void main(String[] args) {
        getRandomNumString();
    }
}
