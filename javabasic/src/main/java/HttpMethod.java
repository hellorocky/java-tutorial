import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.Collections;
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

/**
 * @author RockyWu
 * 最近整理了Java中发送HTTP请求的一些方法, 请教了一位公司的Java开发者, 他给指点了一下, 参考了他的代码, 这里简单总结一下RestTemplate的使用.
 * RestTemplate是Spring 3.0的时候引入的, 现在Spring已经更新到了5.0, RestTemplate是同步请求, 现在已经不推荐使用了, 逐渐会被WebClient
 * 来代替, 后续会写一写相关的使用教程.
 *
 *
 *
 *
 */
@Service
public class Test {
    @Autowired
    RestTemplate restTemplate;

    public static void main(String[] args) throws IOException {
        doGet();
    }

    private static void doGet() {
        RestTemplate restTemplate = new RestTemplate();
        //默认情况下restTemplate会使用Jackson和Gson等来解析接口返回的json数据, 转化成自己定义的Class或者Bean等.spring没有把阿里的fastjson
        //内置成为json的解析器, 所以如果想直接像如下的写法的话, 引入Jackson或者Gson即可, 该接口返回的内容结构如下:
        //{
        //  "type": "success",
        //  "value": {
        //    "id": 11,
        //    "quote": "I have two hours today to build an app from scratch. @springboot to the rescue!"
        //  }
        //}
        Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        String quoteStr = quote.getValue().getQuote();
        System.out.println(quoteStr);

        //国内很多开发者习惯上使用阿里的fastjson, 这时候可以不定义Bean, 直接使用fastjson中的JSONObject来接返回的对象, 如下, result对象中
        //包含了很多方法可以获取想要的结果, 这种方式不是很确定, 只能解析一层, 如果嵌套的话就不合适了.
        //还可以使用JSON.parseObject(response.getBody(), Quote.class)类似这样的方式来解析.
        //从网上简单搜了一下fastjson和jackson的比较, 大致说fastjson扩展性差, 代码质量差, 性能快不是最根本的, 所以我自己的选择是不使用fastjson
//        JSONObject result = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", JSONObject.class);
//        System.out.println(result.get("type"));


    }

    private static void doPost(){
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("name", "大白");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        HttpEntity httpEntity = new HttpEntity(params, headers);
        //下面的跟GET的类似了
        Quote quote = restTemplate.postForObject("http://example.com", httpEntity, Quote.class);
    }

}

class Value{
    private String id;
    private String quote;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}

class Quote{
    private String type;
    private Value value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }
}

/**
 * 下面是自定义配置restTemplate, 默认情况下restTemplate使用的是SimpleClientHttpRequestFactory来构造HTTPclient, 使用的Java默认的HTTPURLconnection
 * 一般生产环境中都会使用Apache HTTPclient, 下面是在springboot中配置restTemplate.
 */
@Configuration
class RestTemplatConfiguration {

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {
        //下面都是Apache HTTPclient的相关配置, 支持很多配置, 这里为了演示只是简单的配置了几项.
        RequestConfig config = RequestConfig.custom()
                //连接目标服务器超时时间
                .setConnectTimeout(10000)
                //读取目标服务器数据超时时间
                .setSocketTimeout(30000)
                //从连接池获取连接的超时时间
                .setConnectionRequestTimeout(10000)
                .build();
        //HTTP Client构造器
        HttpClientBuilder builder = HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(5, false));
        //构造HTTP Client
        HttpClient httpClient = builder.build();

        //最终使用的是spring提供的方法(该方法专门为ApacheHTTPclient封装)返回一个ClientHttpRequestFactory供该类的第二个方法使用.
        return new HttpComponentsClientHttpRequestFactory(httpClient);
    }

    /**
     * @param clientHttpRequestFactory
     * @return
     * Spring Boot初始化的时候会加载下面的Bean, 这样就达到了重新配置restTemplate的目的.
     */
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        return new RestTemplate(clientHttpRequestFactory);
    }


}