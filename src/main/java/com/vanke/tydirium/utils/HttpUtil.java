package com.vanke.tydirium.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.vanke.tydirium.web.controller.BaseController;

public class HttpUtil extends BaseController{

	private static final int CONNECT_TIMEOUT = 5000;
	private static final int CONNECTION_REQUEST_TIMEOUT = 5000;
	private static final int SOCKET_TIMEOUT = 5000;
	
	public static String sendGet(String url) throws ClientProtocolException, IOException {
		return sendGet(url, null);
	}

	public static String sendGet(String url, Map<String,Object> headersMap) throws ClientProtocolException, IOException {
		String content = "";
		logger.info("url:::" + url);
		Long currentTime = System.currentTimeMillis();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		try {
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeaders(buildHeader(headersMap));// 设置header		
			CloseableHttpResponse response = httpclient.execute(httpGet);
			try {
				HttpEntity entity = response.getEntity();
				InputStream instream = entity.getContent();
				content = IOUtils.toString(instream);
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode < 500) {
				logger.info("response(statusCode: " + statusCode + "):::" + content);
			} else {
				logger.info("response(statusCode: " + statusCode + "):::");
			}
		} finally {
			httpclient.close();
			logger.info("REQ_TIME_COST: " + (System.currentTimeMillis() - currentTime) + "ms, URL:::" + url);
		}
		return content;
	}

	public static String sendPost(String url, Map<String, String> params, Map<String, Object> headersMap) throws ClientProtocolException, IOException {
		String content = null;
		logger.info("url:::" + url);
		Long currentTime = System.currentTimeMillis();
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeaders(buildHeader(headersMap));// 设置header
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			CloseableHttpResponse response = httpclient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			try {
				HttpEntity entity = response.getEntity();
				content = IOUtils.toString(entity.getContent());
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
			if (statusCode < 500) {
				logger.info("response(statusCode: " + statusCode + "):::" + content);
			} else {
				logger.info("response(statusCode: " + statusCode + "):::");
			}
		} finally {
			httpclient.close();
			logger.info("REQ_TIME_COST: " + (System.currentTimeMillis() - currentTime) + "ms, URL:::" + url);
		}
		return content;
	}

	public static String sendPost(String url, String params, Map<String, Object> headersMap) throws ClientProtocolException, IOException {
		String content = null;
		logger.info("url:::" + url);
		logger.info("param:::" + params);
		Long currentTime = System.currentTimeMillis();
		if (headersMap != null) {
			for (Map.Entry<String, Object> entry : headersMap.entrySet()) {
				logger.info("header:::Key = " + entry.getKey() + ", Value = "
						+ (entry.getValue() != null ? entry.getValue().toString() : "null"));
			}
		}
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT)
				.setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
		CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
			
		try {
			HttpPost httpPost = new HttpPost(url);
			httpPost.setHeaders(buildHeader(headersMap));// 设置header
			StringEntity stringEntity = new StringEntity(params, "UTF-8");
			stringEntity.setContentType("application/json");
			httpPost.setEntity(stringEntity);
			CloseableHttpResponse response = httpclient.execute(httpPost);
			try {
				HttpEntity entity = response.getEntity();
				content = IOUtils.toString(entity.getContent());
				EntityUtils.consume(entity);
			} finally {
				response.close();
			}
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode < 500) {
				logger.info("response(statusCode: " + statusCode + "):::" + content);
			} else {
				logger.info("response(statusCode: " + statusCode + "):::");
			}
		} finally {
			httpclient.close();
			logger.info("REQ_TIME_COST: " + (System.currentTimeMillis() - currentTime) + "ms, URL:::" + url);
		}
		return content;
	}
	
	/**
	 * 将Map转换成Header[]
	 * @param headers
	 * @return
	 */
	private static Header[] buildHeader(Map<String, Object> headers) {
		if (headers == null) {
			return null;
		}
		List<Header> headerList = new ArrayList<Header>(headers.size());
		for (Map.Entry<String, Object> entry : headers.entrySet()) {
			Header header = new BasicHeader(entry.getKey(), entry.getValue().toString());
			headerList.add(header);
		}
		return headerList.toArray(new Header[headers.size()]);
	}

	/**
	 * 获取用户请求的ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestIp(HttpServletRequest request) {
		if (request == null)
			return "";

		String requestIp = request.getRemoteAddr();

		String x_real_ip = request.getHeader("X-Real-IP");
		if (x_real_ip != null && x_real_ip.length() > 0) {
			requestIp = x_real_ip;
		}
		return requestIp;
	}
}
