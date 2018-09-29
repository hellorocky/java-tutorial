import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RockyWu
 * 最近整理了Java中发送HTTP请求的一些方法, 相比较于Python的语法, 确实感觉Java有点啰嗦, 语法繁琐.
 * 不同语言, 应该去学习它的优点:
 * 1. 如果是要学习一些和编程语言没有关系的知识, 比如HTTP协议, TCP协议等, 我还是建议使用Python来实践学习.
 * 2. 如果是学习中大型互联网公司的技术框架, 相关规范等, 建议学习Java语言等相关知识.
 * 3. 如果是语言本身的特点的话, 这就没什么好说的了.哪个语言就学那一个就行.
 */
public class HttpMethod {
    public static void main(String[] args) throws IOException {
//        downloadImage();
//        doGetAPI();
//        doPOSTAPI();
        doFluentGetApi();
    }

    private static void doGetApi() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://httpbin.org/get");
        //设置超时时间
        //Connection Timeout: 和远程主机建立连接
        //
        //Socket Timeout:  the time waiting for data – after the
        //connection was established; maximum time of inactivity between two data packets
        //
        //the Connection Manager Timeout (http.connection-manager.timeout) – the time to
        //wait for a connection from the connection manager/pool
        //
        //The first two parameters – the connection and socket timeouts – are the most
        //important, but setting a timeout for obtaining a connection is definitely important in
        //high load scenarios, which is why the third parameter shouldn’t be ignored.
        RequestConfig config = RequestConfig.custom().setConnectTimeout(5000).
                setConnectionRequestTimeout(5000).setSocketTimeout(5000).build();
        httpGet.setConfig(config);
        //添加请求header
        httpGet.addHeader("name", "RockyWu");
        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {
            //获取HTTP状态码
            System.out.println(response.getStatusLine().getStatusCode());
            //获取指定header
            Header[] headers = response.getHeaders("Content-Type");
            System.out.println(headers[0].getValue());
            //遍历header
            HeaderIterator headerIterator = response.headerIterator();
            while (headerIterator.hasNext()) {
                System.out.println(headerIterator.nextHeader().getName());
            }
            //获取body内容
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                InputStream inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                //使用fastjson反序列化成Java Bean
                HttpbinBean httpbinBean = JSON.parseObject(stringBuilder.toString(), HttpbinBean.class);
                System.out.println("我的IP是: " + httpbinBean.getOrigin());
                reader.close();
            }

        } finally {
            //关闭当前连接
            response.close();
        }
    }

    private static void doFluentGetApi() throws IOException {
        String ret = Request.Get("http://httpbin.org/get")
                .connectTimeout(1000)
                .socketTimeout(1000)
                .execute().returnContent().asString();
        System.out.println(ret);
    }

    private static void doPOSTAPI() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost("https://httpbin.org/post");
        HttpPost httpPost = new HttpPost("http://127.0.0.1:5000");
        //添加请求header
        httpPost.addHeader("name", "RockyWu");
        httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");
        httpPost.addHeader("Accept", "application/json");

        //构造请求JSON内容
        Map<String, String> map = new HashMap<>();
        map.put("name", "大白");
        map.put("sextual", "男");
        String paramJson = JSONObject.toJSONString(map);
        //解决中文乱码的关键
        StringEntity stringEntity = new StringEntity(paramJson, "UTF-8");
        //添加请求body
        httpPost.setEntity(stringEntity);
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {

            //获取body内容
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                InputStream inputStream = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                System.out.println(stringBuilder.toString());
                //使用fastjson反序列化成Java Bean
//                HttpbinBean httpbinBean = JSON.parseObject(stringBuilder.toString(), HttpbinBean.class);
//                System.out.println("我的IP是: " + httpbinBean.getOrigin());
//                reader.close();
            }

        } finally {
            //关闭当前连接
            response.close();
        }
    }

    private static void downloadImage() throws IOException {
        String url = "http://rockywu.me/down/abc.jpg";
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        //添加请求header
        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {
            //获取body内容
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                InputStream inputStream = httpEntity.getContent();

                File destFile = new File("/tmp/abc.jpg");
                FileOutputStream fileOutputStream = new FileOutputStream(destFile);

                int b;
                while ((b = inputStream.read()) != -1) {
                    fileOutputStream.write(b);
                }

                inputStream.close();
                fileOutputStream.close();


            }

        } finally {
            //关闭当前连接
            response.close();
        }
    }

}

class HttpbinBean {
    private String origin;
    private String url;

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}