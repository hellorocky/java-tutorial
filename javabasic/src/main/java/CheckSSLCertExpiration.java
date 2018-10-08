import javax.net.ssl.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * 检查指定域名的HTTPS证书是否过期
 * @author RockyWu
 * @date 2018/10/8
 */
public class CheckSSLCertExpiration {
    public static void main(String[] args) throws Exception {
        checkSSLCert("api.bbobo.com", 443, 300);
    }

    /**
     * 获取网站域名的SSL证书并查看该网站证书的过期时间, 如果距离当前时间不足指定天数的时候会提示.
     *
     * @param hostname 域名, 比如app.bbobo.com
     * @param port     端口, 默认使用443即可
     * @param day      距离过期几天的时候提醒管理员
     */
    public static void checkSSLCert(String hostname, int port, int day) throws NoSuchAlgorithmException, KeyManagementException {
        //下面这段是网上摘录的, 我还没有完全理解这一段代码
        // create custom trust manager to ignore trust paths
        TrustManager trustManager = new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        };

        //获取域名的证书内容
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[]{trustManager}, null);
        try {
            SSLSocketFactory factory = sslContext.getSocketFactory();
            SSLSocket socket = (SSLSocket) factory.createSocket();
            socket.connect(new InetSocketAddress(hostname, port), 3000);
            socket.startHandshake();
            SSLSession session = socket.getSession();
            java.security.cert.Certificate[] servercerts = session.getPeerCertificates();
            socket.close();

            //byte array转化成base64字符串
            StringBuilder stringBuilder = new StringBuilder();

            for (java.security.cert.Certificate certificate : servercerts) {
                stringBuilder.append("-----BEGIN CERTIFICATE-----\n");
                stringBuilder.append(new sun.misc.BASE64Encoder().encode(certificate.getEncoded()));
                stringBuilder.append("\n-----END CERTIFICATE-----\n");
            }
            //字符串转成byte array来作为下面的参数.
            InputStream inputStream = new ByteArrayInputStream(stringBuilder.toString().getBytes());

            //证书工厂。此处指明证书的类型
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

            // 根据传入的流类型，可以生成Cert实例
            X509Certificate cert = (X509Certificate) certificateFactory.generateCertificate(inputStream);

            // X509Certificate 中提供了多个方法，用于读取证书中的各类信息。
            Date certExpireDate = cert.getNotAfter();
            //Date --> LocalDate, 为了方便使用Java8中的period方法
            LocalDateTime certExpireLocalDateTime = certExpireDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime now = LocalDateTime.now();
            //如果使用LocalDate类型的话, 使用Duration比较的时候会报错
            //https://stackoverflow.com/questions/34440874/duration-ofdays-generates-unsupportedtemporaltypeexception
            Duration duration = Duration.between(certExpireLocalDateTime, now);
            long diff = Math.abs(duration.toDays());
            if (diff < day) {
                System.out.println("域名: " + hostname + "即将在: " + diff + "天后过期!");
            }
        } catch (SocketTimeoutException e) {
            System.out.println(hostname + ": 连接超时!");
        } catch (UnknownHostException e) {
            System.out.println(hostname + ": 域名未解析!");
        } catch (Exception e) {
            System.out.println(hostname + ": 其他错误!");
        }

    }


}
