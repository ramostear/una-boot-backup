package com.ramostear.unaboot.common.util;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;


/**
 * @ClassName HttpClientUtils
 * @Description TODO
 * @Author ramostear
 * @Date 2019/11/22 0022 1:49
 * @Version 1.0
 **/
@Slf4j
public class HttpClientUtils {

    private static final int CONNECT_TIMEOUT = 5000;//设置超时毫秒数

    private static final int SOCKET_TIMEOUT = 10000;//设置传输毫秒数

    private static final int REQUESTCONNECT_TIMEOUT = 3000;//获取请求超时毫秒数

    private static final int CONNECT_TOTAL = 200;//最大连接数

    private static final int CONNECT_ROUTE = 20;//设置每个路由的基础连接数

    private static final int VALIDATE_TIME = 30000;//设置重用连接时间

    private static final String RESPONSE_CONTENT = "通信失败";

    private static PoolingHttpClientConnectionManager manager = null;

    private static CloseableHttpClient client = null;

    static {
        ConnectionSocketFactory csf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory lsf = createSSLConnSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", csf).register("https", lsf).build();
        manager = new PoolingHttpClientConnectionManager(registry);
        manager.setMaxTotal(CONNECT_TOTAL);
        manager.setDefaultMaxPerRoute(CONNECT_ROUTE);
        manager.setValidateAfterInactivity(VALIDATE_TIME);
        SocketConfig config = SocketConfig.custom().setSoTimeout(SOCKET_TIMEOUT).build();
        manager.setDefaultSocketConfig(config);
        RequestConfig requestConf = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(REQUESTCONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        client = HttpClients.custom().setConnectionManager(manager).setDefaultRequestConfig(requestConf).setRetryHandler(
                //实现了HttpRequestRetryHandler接口的
                //public boolean retryRequest(IOException exception, int executionCount, HttpContext context)方法
                (exception, executionCount, context) -> {
                    if(executionCount >= 3)
                        return false;
                    if(exception instanceof NoHttpResponseException)//如果服务器断掉了连接那么重试
                        return true;
                    if(exception instanceof SSLHandshakeException)//不重试握手异常
                        return false;
                    if(exception instanceof InterruptedIOException)//IO传输中断重试
                        return true;
                    if(exception instanceof UnknownHostException)//未知服务器
                        return false;
                    if(exception instanceof ConnectTimeoutException)//超时就重试
                        return true;
                    if(exception instanceof SSLException)
                        return false;

                    HttpClientContext cliContext = HttpClientContext.adapt(context);
                    HttpRequest request = cliContext.getRequest();
                    if(!(request instanceof HttpEntityEnclosingRequest))
                        return true;
                    return false;
                }).build();
        if(manager!=null && manager.getTotalStats()!=null)
            log.info("客户池状态："+manager.getTotalStats().toString());
    }

    private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
        SSLConnectionSocketFactory sslsf = null;
        SSLContext context;
        try {
            X509TrustManager x509m = new X509TrustManager() {

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
            };
            context = SSLContext.getInstance(SSLConnectionSocketFactory.SSL);
            // 初始化SSLContext实例
            try {
                //最关键的必须有这一步，否则抛出SSLContextImpl未被初始化的异常
                context.init(null,
                        new TrustManager[] { x509m },
                        new java.security.SecureRandom());
            } catch (KeyManagementException e) {
                log.debug("SSL上下文初始化失败， 由于 {}", e.getLocalizedMessage());
            }
            sslsf = new SSLConnectionSocketFactory(context, NoopHostnameVerifier.INSTANCE);
        } catch (NoSuchAlgorithmException e) {
            log.debug("SSL上下文创建失败，由于 {}", e.getLocalizedMessage());
        }
        return sslsf;
    }

    private static String getContent(HttpRequestBase method) {
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpResponse response = null;
        String content = RESPONSE_CONTENT;
        try {
            response = client.execute(method, context);//执行GET/POST请求
            HttpEntity entity = response.getEntity();//获取响应实体
            if(entity!=null) {
                Charset charset = ContentType.getOrDefault(entity).getCharset();
                content = EntityUtils.toString(entity, charset);
                EntityUtils.consume(entity);
            }
        } catch(ConnectTimeoutException cte) {
            log.debug("请求连接超时，由于 {}", cte.getLocalizedMessage());
        } catch(SocketTimeoutException ste) {
            log.debug("请求通信超时，由于 {}", ste.getLocalizedMessage());
        } catch(ClientProtocolException cpe) {
            log.debug("协议错误（比如构造HttpGet对象时传入协议不对(将'http'写成'htp')or响应内容不符合），由于 {}", cpe.getLocalizedMessage());
            cpe.printStackTrace();
        } catch(IOException ie) {
            log.debug("实体转换异常或者网络异常， 由于 {}", ie.getLocalizedMessage());
        } finally {
            try {
                if(response!=null) {
                    response.close();
                }
            } catch(IOException e) {
                log.debug("响应关闭异常， 由于 {}", e.getLocalizedMessage());
            }
            if(method!=null) {
                method.releaseConnection();
            }
        }
        return content;
    }

    private String res(HttpRequestBase method) {
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpResponse response = null;
        String content = RESPONSE_CONTENT;
        try {
            response = client.execute(method, context);//执行GET/POST请求
            HttpEntity entity = response.getEntity();//获取响应实体
            if(entity!=null) {
                Charset charset = ContentType.getOrDefault(entity).getCharset();
                content = EntityUtils.toString(entity, charset);
                EntityUtils.consume(entity);
            }
        } catch(ConnectTimeoutException cte) {
            log.error("请求连接超时，由于 " + cte.getLocalizedMessage());
            cte.printStackTrace();
        } catch(SocketTimeoutException ste) {
            log.error("请求通信超时，由于 " + ste.getLocalizedMessage());
            ste.printStackTrace();
        } catch(ClientProtocolException cpe) {
            log.error("协议错误（比如构造HttpGet对象时传入协议不对(将'http'写成'htp')or响应内容不符合），由于 " + cpe.getLocalizedMessage());
            cpe.printStackTrace();
        } catch(IOException ie) {
            log.error("实体转换异常或者网络异常， 由于 " + ie.getLocalizedMessage());
            ie.printStackTrace();
        } finally {
            try {
                if(response!=null) {
                    response.close();
                }

            } catch(IOException e) {
                log.error("响应关闭异常， 由于 " + e.getLocalizedMessage());
            }

            if(method!=null) {
                method.releaseConnection();
            }

        }

        return content;
    }

    public String get(String url) {
        HttpGet get = new HttpGet(url);
        return res(get);
    }

    public String get(String url, String cookie) {
        HttpGet get = new HttpGet(url);
        if(StringUtils.isNotBlank(cookie))
            get.addHeader("cookie", cookie);
        return res(get);
    }

    public byte[] getAsByte(String url) {
        return get(url).getBytes();
    }

    public String getHeaders(String url, String cookie, String headerName) {
        HttpGet get = new HttpGet(url);
        if(StringUtils.isNotBlank(cookie))
            get.addHeader("cookie", cookie);
        res(get);
        Header[] headers = get.getHeaders(headerName);
        return headers == null ? null : headers.toString();
    }

    public String getWithRealHeader(String url) {
        HttpGet get = new HttpGet(url);
        get.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;");
        get.addHeader("Accept-Language", "zh-cn");
        get.addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3");
        get.addHeader("Keep-Alive", "300");
        get.addHeader("Connection", "Keep-Alive");
        get.addHeader("Cache-Control", "no-cache");
        return res(get);
    }

    public String post(String url, Map<String, String> param, String cookie) {
        HttpPost post = new HttpPost(url);
        param.keySet().stream().forEach(key -> {
            String value = param.get(key);
            if(value!=null)
                post.addHeader(key, value);
        });
        if(StringUtils.isNotBlank(cookie))
            post.addHeader("cookie", cookie);
        return res(post);
    }

    public String post(String url, String data) {
        HttpPost post = new HttpPost(url);
        if(StringUtils.isNotBlank(data))
            post.addHeader("Content-Type", "application/json");
        post.setEntity(new StringEntity(data,ContentType.APPLICATION_JSON));
        return res(post);
    }

    public String post(String url, Map<String, String> param, String cookie, Map<String, String> headers) {
        HttpPost post = new HttpPost(url);
        String reqEntity = "";
        for(Entry<String, String> entry:param.entrySet()) {
            post.addHeader(entry.getKey(), entry.getValue());
            try {
                reqEntity += entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "utf-8") + "&";
            } catch (UnsupportedEncodingException e) {
                log.error("请求实体转换异常，不支持的字符集，由于 " + e.getLocalizedMessage());
                e.printStackTrace();
            }
        }

        try {
            post.setEntity(new StringEntity(reqEntity));
        } catch (UnsupportedEncodingException e) {
            log.error("请求设置实体异常，不支持的字符集， 由于 " + e.getLocalizedMessage());
            e.printStackTrace();
        }

        if(StringUtils.isNotEmpty(cookie))
            post.addHeader("cookie", cookie);

        return res(post);
    }

}
