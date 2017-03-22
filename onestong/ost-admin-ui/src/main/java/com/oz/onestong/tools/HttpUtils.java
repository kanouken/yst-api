package com.oz.onestong.tools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class HttpUtils {
	
	
	public static String  GET(String url) {
		
		String result = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(2000).setConnectTimeout(2000).build();
		get.setConfig(requestConfig);
		
		try {
			//HttpEntity re = new StringEntity(content);
			//get.setHeader("Content-Type", "application/json; charset=utf-8");
		
			
			//httppost.setEntity(re);
			HttpResponse response = httpClient.execute(get);
			String responseStr = EntityUtils.toString(response.getEntity());

			
			if (response.getStatusLine().getStatusCode() == 200) {
				result = responseStr;
				
			} else {
				result =null;
				
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return  result;
		
		
	}
	
	
	public static void main(String[] args) {
		String reult =  HttpUtils.GET("http://www.easybots.cn/api/holiday.php?m=201506,201507");
		System.out.println(reult);
	}
	
}
