package com.wanding.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class HttpPostUtil {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpPostUtil.class);

	// 传输超时时间，默认10秒
	private int socketTimeout = 100000;

	// 连接超时时间，默认300秒
	private int connectTimeout = 300000;

	// 从池中拿连接的等待时间，默认2秒
	private int connectReqTimeout = 2000;

	private CloseableHttpClient httpClient;

	public HttpPostUtil() {
		initHttpClient();
	}

	private void initHttpClient() {
		final PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(100);
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout)
				.setConnectionRequestTimeout(connectReqTimeout).setSocketTimeout(socketTimeout).build();
		HttpRequestRetryHandler retryHandler = new StandardHttpRequestRetryHandler();

		httpClient = HttpClients.custom().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig)
				.setRetryHandler(retryHandler).build();

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					connectionManager.closeExpiredConnections();
					connectionManager.closeIdleConnections(300, TimeUnit.SECONDS);
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}, 0, 1000 * 20);
	}

	public String doPostWithJson(String url, Object object, String contentType) throws Exception {
		String json = (String) JSONObject.toJSONString(object);
		LOGGER.info("API，postDataJson：{}", json);
		StringEntity postEntity = new StringEntity(json, "UTF-8");
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", contentType);
		httpPost.setEntity(postEntity);
		String result = httpExecute(httpPost);
		return result;
	}

	private String httpExecute(HttpPost httpPost) throws Exception {
		CloseableHttpResponse response = null;
		String result = null;
		try {
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");

		} catch (Exception e) {
			LOGGER.error("http post throw Exception");
			throw e;

		} finally {
			httpPost.abort();
			if (null != response) {
				response.close();
			}
		}
		return result;
	}
	
	
	public static String sendPost(String requestUrl,String outputStr){
		return httpRequest(requestUrl, "POST", outputStr);
	}

	public static String sendGet(String requestUrl,String outputStr){
		return httpRequest(requestUrl, "GET", outputStr);
	}

	/**
	 * 发起http请求并获取结果
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr){
		StringBuffer buffer = new StringBuffer();
		try {

			URL url = new URL(requestUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			httpUrlConn.setRequestProperty("accept", "*/*");
			httpUrlConn.setRequestProperty("connection", "Keep-Alive");
			httpUrlConn.setRequestProperty("accept-charset", "UTF-8");
			httpUrlConn.setRequestProperty("content-type", "application/json;charset=utf-8");
			httpUrlConn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if ("GET".equalsIgnoreCase(requestMethod))
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.flush();
				outputStream.close();
			}

			// 记录http请求返回的状态码
			LOGGER.debug(String.valueOf(httpUrlConn.getResponseCode()));
			//System.out.println("连接DMZ返回编码："+httpUrlConn.getResponseCode());
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
		} catch (Exception ce) {
			ce.printStackTrace();
			LOGGER.error("连接DMZ错误信息", ce);
		}
		return buffer.toString();
	}
}
