package com.smok.web.utils;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.util.List;

/**
 * Created by smok on 2015/12/28.
 */
public class HttpClientUtil {
    private static final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();

    private static CloseableHttpClient httpClient = null;
    static {
        connectionManager.setMaxTotal(30);
        connectionManager.setDefaultMaxPerRoute(10);
        httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
    }

    public static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public static String post(String url, List<NameValuePair> nameValuePairList) throws Exception {
        return post(url, "utf-8", null, nameValuePairList, 5 * 1000, 5 * 1000);
    }

    public static String post(String url, String charset, List<Header> headerList, List<NameValuePair> nameValuePairList, int connTimeOut,
                              int waitTimeOut) throws Exception {
        CloseableHttpResponse response = null;
        HttpPost httpPost = new HttpPost(url);
        try {
            if (charset == null)
                charset = "utf-8";
            if (headerList != null)
                httpPost.setHeaders(headerList.toArray(new Header[0]));
            if (nameValuePairList != null)
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairList, charset));
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connTimeOut).setSocketTimeout(waitTimeOut).build();
            httpPost.setConfig(requestConfig);
            response = httpClient.execute(httpPost);
            return EntityUtils.toString(response.getEntity(), charset);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            if (httpPost != null) {
                try {
                    httpPost.releaseConnection();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
    }

    public static String get(String url) throws Exception {
        return get(url, null, null, 5 * 1000, 5 * 1000);
    }

    public static CloseableHttpResponse get(String url, RequestConfig config) throws Exception {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        return httpClient.execute(httpGet);
    }

    public static String get(String url, String charset, List<Header> headerList, int connTimeOut, int waitTimeOut) throws Exception {
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(url);
        try {
            if (charset == null)
                charset = "utf-8";
            if (headerList != null)
                httpGet.setHeaders(headerList.toArray(new Header[0]));
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connTimeOut).setSocketTimeout(waitTimeOut).build();
            httpGet.setConfig(requestConfig);
            response = httpClient.execute(httpGet);
            return EntityUtils.toString(response.getEntity(), charset);
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
            if (httpGet != null) {
                try {
                    httpGet.releaseConnection();
                } catch (Exception e) {
                    // TODO: handle exception
                }
            }
        }
    }
}
