package com.yuansong.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

public class HttpUtils {
	
	private final Logger logger = Logger.getLogger(HttpUtils.class);
	
	private static final String CHARSET = "UTF-8";
	
	
	public String httpGet(String url) throws IOException {
		StringBuffer buffer = new StringBuffer();

		URL realUrl = new URL(url);
        HttpURLConnection httpUrlConn = (HttpURLConnection) realUrl.openConnection();  

        httpUrlConn.setConnectTimeout(30 * 1000);
        httpUrlConn.setReadTimeout(30 * 1000);
        httpUrlConn.setUseCaches(false);  
        httpUrlConn.setRequestMethod("GET");
        
        httpUrlConn.setRequestProperty("Content-Type", "application/json");
        httpUrlConn.setRequestProperty("Accept", "application/json");
        httpUrlConn.setRequestProperty("Accept-Charset", CHARSET);
        httpUrlConn.setRequestProperty("Accept-Language", "zh");
        
        httpUrlConn.connect();
        
        
        
        InputStream inputStream = null;
        if(httpUrlConn.getResponseCode() ==HttpURLConnection.HTTP_OK  ||
        		httpUrlConn.getResponseCode() ==HttpURLConnection.HTTP_CREATED  ||
        		httpUrlConn.getResponseCode() ==HttpURLConnection.HTTP_ACCEPTED) {
        	inputStream =httpUrlConn.getInputStream(); 
        }
        else {
        	inputStream =httpUrlConn.getErrorStream();
        }
       
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, CHARSET);  
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
        
        return buffer.toString();
	}
	
	
//	public String httpGet(String url) {
//		StringBuffer buffer = new StringBuffer();  
//        try {  
//            URL realUrl = new URL(url);  
//            HttpURLConnection httpUrlConn = (HttpURLConnection) realUrl.openConnection();  
//   
//            httpUrlConn.setDoOutput(false);  
//            httpUrlConn.setDoInput(true);  
//            httpUrlConn.setUseCaches(false);  
//   
//            httpUrlConn.setRequestMethod("GET");  
//            httpUrlConn.connect();  
//   
//            // 将返回的输入流转换成字符串  
//            InputStream inputStream = httpUrlConn.getInputStream();  
//            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, CHARSET);  
//            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
//   
//            String str = null;  
//            while ((str = bufferedReader.readLine()) != null) {  
//                buffer.append(str);  
//            }  
//            bufferedReader.close();  
//            inputStreamReader.close();  
//            // 释放资源  
//            inputStream.close();  
//            inputStream = null;  
//            httpUrlConn.disconnect();  
//   
//        } catch (Exception e) {  
//        	logger.error("发送GET请求出现异常！" + e.getMessage());
//	        e.printStackTrace();
//        }  
//        return buffer.toString(); 
//	}
	
	public String httpPostJson(String url, String data) throws IOException {
		OutputStreamWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) realUrl.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");    // POST方法
            
            conn.setRequestProperty("Content-Type", "application/json");
	        conn.setRequestProperty("Accept", "application/json");
	        conn.setRequestProperty("Accept-Charset", CHARSET);
	        conn.setRequestProperty("Accept-Language", "zh");
             
            conn.connect();
             
            out = new OutputStreamWriter(conn.getOutputStream(), CHARSET);
            out.write(data);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),CHARSET));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }catch (IOException ex) {
        	logger.error("发送 POST 请求出现异常！"+ex.getMessage());
        	ex.printStackTrace();
        	throw ex;
        }
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
            	logger.error(ex.getMessage());
                ex.printStackTrace();
            }
        }
        return result;
	}
	
	
//	public String httpPostJson(String url, String data) {
//		OutputStreamWriter out = null;
//        BufferedReader in = null;
//        String result = "";
//        try {
//            URL realUrl = new URL(url);
//            HttpURLConnection conn = null;
//            conn = (HttpURLConnection) realUrl.openConnection();
//
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            conn.setRequestMethod("POST");    // POST方法
//             
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            conn.setRequestProperty("Content-Type", "application/json; charset=" + CHARSET);
//             
//            conn.connect();
//             
//            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
//            out.write(data);
//            out.flush();
//            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//        } catch (Exception e) {
//        	logger.error("发送 POST 请求出现异常！"+e.getMessage());
//            e.printStackTrace();
//        }
//        finally{
//            try{
//                if(out!=null){
//                    out.close();
//                }
//                if(in!=null){
//                    in.close();
//                }
//            }
//            catch(IOException ex){
//            	logger.error(ex.getMessage());
//                ex.printStackTrace();
//            }
//        }
//        return result;
//	}
}
