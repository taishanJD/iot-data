package com.quarkdata.data.util;

import com.quarkdata.data.util.common.mapper.JsonMapper;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 基于 httpclient 4.3.1版本的 http工具类
 * @author mcSui
 *
 */
public class HttpTookit {

    private static final CloseableHttpClient httpClient;
    public static final String CHARSET = "UTF-8";
    
    static Logger logger = Logger.getLogger(HttpTookit.class);

    static {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(10000).build();
        httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
    }

    public static String doGet(String url, Map<String, String> params){
        return doGet(url, params,CHARSET);
    }
    public static String doPost(String url, Map<String, String> params){
        return doPost(url, params,CHARSET);
    }
    /**
     * HTTP Get 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params	请求的参数
     * @param charset	编码格式
     * @return	页面内容
     */
    public static String doGet(String url,Map<String,String> params,String charset){
    	if(StringUtils.isBlank(url)){
    		return null;
    	}
    	try {
    		if(params != null && !params.isEmpty()){
    			List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
    			for(Map.Entry<String,String> entry : params.entrySet()){
    				String value = entry.getValue();
    				if(value != null){
    					pairs.add(new BasicNameValuePair(entry.getKey(),value));
    				}
    			}
    			url += "?" + EntityUtils.toString(new UrlEncodedFormEntity(pairs, charset));
    		}
    		//logger.debug("url is:"+url);
    		HttpGet httpGet = new HttpGet(url);
    		CloseableHttpResponse response = httpClient.execute(httpGet);
    		int statusCode = response.getStatusLine().getStatusCode();
    		if (statusCode != 200) {
    			httpGet.abort();
    			throw new RuntimeException("HttpClient,error status code :" + statusCode);
    		}
    		HttpEntity entity = response.getEntity();
    		String result = null;
    		if (entity != null){
    			result = EntityUtils.toString(entity, "utf-8");
    		}
    		EntityUtils.consume(entity);
    		response.close();
    		return result;
    	} catch (Exception e) {
			logger.error("HttpClient get request error : ", e);
			ResultCode<String> reslut = new ResultCode<String>();
			reslut.setCode(Messages.API_ERROR_CODE);
			reslut.setMsg(Messages.API_ERROR_MSG);
			return JsonMapper.toJsonString(reslut);
    	}
    }
    
    /**
     * HTTP Post 获取内容
     * @param url  请求的url地址 ?之前的地址
     * @param params	请求的参数
     * @param charset	编码格式
     * @return	页面内容
     */
    public static String doPost(String url,Map<String,String> params,String charset){
    	if(StringUtils.isBlank(url)){
    		return null;
    	}
    	try {
    		List<NameValuePair> pairs = null;
    		if(params != null && !params.isEmpty()){
    			pairs = new ArrayList<NameValuePair>(params.size());
    			for(Map.Entry<String,String> entry : params.entrySet()){
    				String value = entry.getValue();
    				if(value != null){
    					pairs.add(new BasicNameValuePair(entry.getKey(),value));
    				}
    			}
    		}
    		HttpPost httpPost = new HttpPost(url);
    		if(pairs != null && pairs.size() > 0){
    			httpPost.setEntity(new UrlEncodedFormEntity(pairs,CHARSET));
    		}
    		CloseableHttpResponse response = httpClient.execute(httpPost);
    		int statusCode = response.getStatusLine().getStatusCode();
    		if (statusCode != 200) {
    			httpPost.abort();
    			throw new RuntimeException("HttpClient,error status code :" + statusCode);
    		}
    		HttpEntity entity = response.getEntity();
    		String result = null;
    		if (entity != null){
    			result = EntityUtils.toString(entity, "utf-8");
    		}
    		EntityUtils.consume(entity);
    		response.close();
    		return result;
    	} catch (Exception e) {
			logger.error("HttpClient post request error : ", e);
			ResultCode<String> reslut = new ResultCode<String>();
			reslut.setCode(Messages.API_ERROR_CODE);
			reslut.setMsg(Messages.API_ERROR_MSG);
			return JsonMapper.toJsonString(reslut);
    	}
    }

    /**
     * HTTP Post json
     * @param url
     * @param object
     * @return
     */
    public static String doPostJson(String url, Object object){
        if(StringUtils.isBlank(url)){
            return null;
        }
        try {
            String jsonStr = JsonMapper.toJsonString(object);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(jsonStr, CHARSET));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != 200) {
                httpPost.abort();
                throw new RuntimeException("HttpClient,error status code :" + statusCode);
            }
            HttpEntity entity = response.getEntity();
            String result = null;
            if (entity != null){
                result = EntityUtils.toString(entity, "utf-8");
            }
            EntityUtils.consume(entity);
            response.close();
            return result;
        } catch (Exception e) {
			logger.error("HttpClient post request error : ", e);
			ResultCode<String> reslut = new ResultCode<String>();
			reslut.setCode(Messages.API_ERROR_CODE);
			reslut.setMsg(Messages.API_ERROR_MSG);
			return JsonMapper.toJsonString(reslut);
        }
    }

    public static String getParams(Map<String, String> params){
		if(params != null && !params.isEmpty()){
			List<NameValuePair> pairs = new ArrayList<NameValuePair>(params.size());
			for(Map.Entry<String,String> entry : params.entrySet()){
				String value = entry.getValue();
				if(value != null){
					pairs.add(new BasicNameValuePair(entry.getKey(),value));
				}
			}
			try {
				return EntityUtils.toString(new UrlEncodedFormEntity(pairs, CHARSET));
			} catch (IOException e) {
				return "";
			}
		}
		return "";
	}
    public static void main(String []args){
    	String getData = doGet("http://www.baidu.com/",null);
    	System.out.println(getData);
    	System.out.println("----------------------分割线-----------------------");
    }
    
}