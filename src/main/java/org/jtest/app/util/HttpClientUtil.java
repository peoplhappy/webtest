package org.jtest.app.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import org.jtest.app.log.LogManager;
import org.jtest.app.model.requests.FileInfo;
import org.jtest.app.model.requests.RequestParamter;
import org.jtest.app.model.requests.ResponseResult;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;

/**
 * 执行http请求
 * 
 * @author pengchen
 *
 */
public class HttpClientUtil {
	private static Gson gson = JsonUtil.gson;
	private static CookieStore cookieStore = new BasicCookieStore();

	/**
	 * 发送httpget请求
	 * 
	 * @param url
	 * @param paramters
	 *            get只支持url传参
	 * @return
	 */

	public static ResponseResult httpGet(String url, Map<String, Object> header, RequestParamter paramters) {
		ResponseResult res = new ResponseResult();
		res.setExcuteResult(true);
		Map<String, Object> paramterdata = paramters.getParamterdata();
		String filepath = System.getProperty("user.dir") + File.separator + "downloads";
		String afterfixurl = generateUrlParams(paramterdata);
		if (afterfixurl.equals("?")) {
			afterfixurl = "";
		}
		url = url + afterfixurl;
		LogManager.getLogger().logInfo("requesturl is " + url);
		HttpGet httpGet = new HttpGet(url);
		// 产生请求头
		genarateHeaderGet(httpGet, header);
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		CloseableHttpResponse response = null;

		try {

			response = httpClient.execute(httpGet);
			if (response != null) {
				// 通过判断paramterdata中是否含有saveFileUrl，如果有则做下载操作，否则，正常返回
				res.setCode(response.getStatusLine().getStatusCode());
				if (paramterdata.containsKey("saveFileUrl")) {
					filepath = filepath + paramterdata.get("saveFileUrl").toString();
					// 如果父文件不存在直接创建文件夹
					if (!new File(filepath).exists()) {
						new File(filepath).getParentFile().mkdirs();
					}
					HttpEntity httpEntity = response.getEntity();
					long contentLength = httpEntity.getContentLength();
					InputStream is = httpEntity.getContent();
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					byte[] buffer = new byte[4096];
					int r = 0;
					long totalRead = 0;
					while ((r = is.read(buffer)) > 0) {
						output.write(buffer, 0, r);
						totalRead += r;
					}
					FileOutputStream fos = new FileOutputStream(filepath);
					output.writeTo(fos);
					output.flush();
					output.close();
					fos.close();
				} else {
					// 否则按照普通流程执行
					HttpEntity entity = response.getEntity();
					res.setResult(entityToString(entity));
				}

			}

		} catch (Exception e) {
			LogManager.getLogger().logError("request:get" + url + " throw an http error", e);
			res.setErrmessage(e.getMessage());
			res.setExcuteResult(false);
		} finally {
			try {
				httpClient.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				LogManager.getLogger().logError("request:get" + url + " throw an http error", e);
				res.setErrmessage(e.getMessage());
				res.setExcuteResult(false);
			}
		}

		return res;
	}

	/**
	 * 
	 * @param url
	 * @param header
	 * @param paramters
	 *            存储请求体参数，根据参数来制定实际请求体，可以上传文件
	 * @return
	 */

	public static ResponseResult httpPost(String url, Map<String, Object> header, RequestParamter paramters) {
		ResponseResult res = new ResponseResult();
		res.setExcuteResult(true);
		Map<String, Object> paramterdata = paramters.getParamterdata();
		
		// 产生请求url
		String afterfixurl = generateUrlParams(paramterdata);
		if (afterfixurl.equals("?")) {
			afterfixurl = "";
		}
		url = url + afterfixurl;
		LogManager.getLogger().logInfo("requesturl is " + url);
		HttpPost httpPost = new HttpPost(url);
		// 产生请求头
		genarateHeaderPost(httpPost, header);
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		CloseableHttpResponse response = null;

		try {
			if (paramters.getFiledata() != null && paramters.getFiledata().size() > 0) {
				MultipartEntityBuilder multpart = generateFileData(paramters.getFiledata(), paramters.getData());
				HttpEntity httpEntity = multpart.build();
				httpPost.setEntity(httpEntity);
			} else if (paramters.getData() != null && paramters.getData().size() > 0) {
				UrlEncodedFormEntity entity = genaratePairData(paramters.getData());
				httpPost.setEntity(entity);
			} else if (paramters.getJsondata() != null) {
				StringEntity strEntity = genarateStrEntity(paramters.getJsondata().toString());
				httpPost.setEntity(strEntity);
			}

			response = httpClient.execute(httpPost);
			if (response != null) {
				// 通过判断paramterdata中是否含有saveFileUrl，如果有则做下载操作，否则，正常返回
				res.setCode(response.getStatusLine().getStatusCode());
				HttpEntity entity = response.getEntity();
				res.setResult(entityToString(entity));

			}

		} catch (Exception e) {
			LogManager.getLogger().logError("throw an http error", e);
			res.setErrmessage(e.getMessage());
		} finally {
			try {
				httpClient.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				LogManager.getLogger().logError("throw an http error", e);
				res.setErrmessage(e.getMessage());
			}
		}

		return res;
	}

	/**
	 * delete请求,不支持上传文件
	 * 
	 * @param url
	 * @param header
	 * @param paramters
	 * @return
	 */

	public static ResponseResult httpDelete(String url, Map<String, Object> header, RequestParamter paramters) {
		ResponseResult res = new ResponseResult();
		res.setExcuteResult(true);
		Map<String, Object> paramterdata = paramters.getParamterdata();
		
		// 产生请求url
		String afterfixurl = generateUrlParams(paramterdata);
		if (afterfixurl.equals("?")) {
			afterfixurl = "";
		}
		url = url + afterfixurl;
		LogManager.getLogger().logInfo("requesturl is " + url);
		HttpDelete delete = new HttpDelete(url);
		// 产生请求头
		genarateHeaderDelete(delete, header);
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		CloseableHttpResponse response = null;
		try {

			response = httpClient.execute(delete);
			if (response != null) {
				// 通过判断paramterdata中是否含有saveFileUrl，如果有则做下载操作，否则，正常返回
				res.setCode(response.getStatusLine().getStatusCode());
				HttpEntity entity = response.getEntity();
				res.setResult(entityToString(entity));
			}

		} catch (Exception e) {
			LogManager.getLogger().logError("throw an http error", e);
			res.setErrmessage(e.getMessage());
		} finally {
			try {
				httpClient.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				LogManager.getLogger().logError("throw an http error", e);
				res.setErrmessage(e.getMessage());
			}
		}

		return res;
	}

	/**
	 * put请求,不支持上传文件
	 * 
	 * @param url
	 * @param header
	 * @param paramters
	 * @return
	 */

	public static ResponseResult httpPut(String url, Map<String, Object> header, RequestParamter paramters) {
		ResponseResult res = new ResponseResult();
		res.setExcuteResult(true);
		Map<String, Object> paramterdata = paramters.getParamterdata();
		
		// 产生请求url
		String afterfixurl = generateUrlParams(paramterdata);
		if (afterfixurl.equals("?")) {
			afterfixurl = "";
		}
		url = url + afterfixurl;
		LogManager.getLogger().logInfo("requesturl is " + url);
		HttpPut put = new HttpPut(url);
		// 产生请求头
		genarateHeaderPut(put, header);
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		CloseableHttpResponse response = null;
		try {

			if (paramters.getData() != null && paramters.getData().size() > 0) {
				UrlEncodedFormEntity entity = genaratePairData(paramters.getData());
				put.setEntity(entity);
			} else if (paramters.getJsondata() != null) {
				StringEntity strEntity = genarateStrEntity(paramters.getJsondata().toString());
				put.setEntity(strEntity);
			}
			response = httpClient.execute(put);
			if (response != null) {
				// 通过判断paramterdata中是否含有saveFileUrl，如果有则做下载操作，否则，正常返回
				res.setCode(response.getStatusLine().getStatusCode());
				HttpEntity entity = response.getEntity();
				res.setResult(entityToString(entity));
			}

		} catch (Exception e) {
			LogManager.getLogger().logError("throw an http error", e);
			res.setErrmessage(e.getMessage());
		} finally {
			try {
				httpClient.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				LogManager.getLogger().logError("throw an http error", e);
				res.setErrmessage(e.getMessage());
			}
		}

		return res;
	}
    
	/**
	 * option请求,不支持上传文件
	 * 
	 * @param url
	 * @param header
	 * @param paramters
	 * @return
	 */

	public static ResponseResult httpOption(String url, Map<String, Object> header, RequestParamter paramters) {
		ResponseResult res = new ResponseResult();
		res.setExcuteResult(true);
		Map<String, Object> paramterdata = paramters.getParamterdata();

		// 产生请求url
		String afterfixurl = generateUrlParams(paramterdata);
		if (afterfixurl.equals("?")) {
			afterfixurl = "";
		}
		url = url + afterfixurl;
		LogManager.getLogger().logInfo("requesturl is " + url);
		HttpOptions option= new HttpOptions(url);
		// 产生请求头
		genarateHeaderOption(option, header);
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(option);
			if (response != null) {
				// 通过判断paramterdata中是否含有saveFileUrl，如果有则做下载操作，否则，正常返回
				res.setCode(response.getStatusLine().getStatusCode());
				HttpEntity entity = response.getEntity();
				res.setResult(entityToString(entity));
			}

		} catch (Exception e) {
			LogManager.getLogger().logError("throw an http error", e);
			res.setErrmessage(e.getMessage());
		} finally {
			try {
				httpClient.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				LogManager.getLogger().logError("throw an http error", e);
				res.setErrmessage(e.getMessage());
			}
		}

		return res;
	}
    
	
	/**
	 * head请求,不支持上传文件
	 * 
	 * @param url
	 * @param header
	 * @param paramters
	 * @return
	 */

	public static ResponseResult httpHead(String url, Map<String, Object> header, RequestParamter paramters) {
		ResponseResult res = new ResponseResult();
		res.setExcuteResult(true);
		Map<String, Object> paramterdata = paramters.getParamterdata();

		// 产生请求url
		String afterfixurl = generateUrlParams(paramterdata);
		if (afterfixurl.equals("?")) {
			afterfixurl = "";
		}
		url = url + afterfixurl;
		LogManager.getLogger().logInfo("requesturl is " + url);
		HttpHead head= new HttpHead(url);
		// 产生请求头
		genarateHeaderHead(head, header);
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(head);
			if (response != null) {
				// 通过判断paramterdata中是否含有saveFileUrl，如果有则做下载操作，否则，正常返回
				res.setCode(response.getStatusLine().getStatusCode());
				HttpEntity entity = response.getEntity();
				res.setResult(entityToString(entity));
			}

		} catch (Exception e) {
			LogManager.getLogger().logError("throw an http error", e);
			res.setErrmessage(e.getMessage());
		} finally {
			try {
				httpClient.close();
				if (response != null) {
					response.close();
				}
			} catch (IOException e) {
				LogManager.getLogger().logError("throw an http error", e);
				res.setErrmessage(e.getMessage());
			}
		}

		return res;
	}
	
	/**
	 * 根据map产生url？key1=&key2=
	 * @param data
	 * @return
	 */
	private static String generateUrlParams(Map<String, Object> data) {
		String afterfixurl = "?";
		Set<String> keyset = data.keySet();
		for (String key : keyset) {
			if (data.get(key) != null) {
				afterfixurl = afterfixurl + key + "=" + data.get(key) + "&";
			}
		}
		if (afterfixurl.endsWith("&")) {
			afterfixurl = afterfixurl.substring(0, afterfixurl.length() - 1);
		}

		return afterfixurl;
	}

	private static String entityToString(HttpEntity entity) throws IOException {
		String result = null;
		if (entity != null) {
			long lenth = entity.getContentLength();
			if (lenth != -1 && lenth < 2048) {
				result = EntityUtils.toString(entity, "UTF-8");
			} else {
				InputStreamReader reader1 = new InputStreamReader(entity.getContent(), "UTF-8");
				CharArrayBuffer buffer = new CharArrayBuffer(2048);
				char[] tmp = new char[1024];
				int l;
				while ((l = reader1.read(tmp)) != -1) {
					buffer.append(tmp, 0, l);
				}
				result = buffer.toString();
			}
		}
		// System.out.println(result);
		return result;
	}

	private static void genarateHeaderPost(HttpPost post, Map<String, Object> header) {
		if(header==null){
			return;
		}
		Set<String> keyset = header.keySet();
		for (String key : keyset) {
			post.setHeader(key, header.get(key).toString());
		}
	}

	private static void genarateHeaderGet(HttpGet get, Map<String, Object> header) {
		if(header==null){
			return;
		}
		Set<String> keyset = header.keySet();
		for (String key : keyset) {
			get.setHeader(key, header.get(key).toString());
		}
	}

	private static void genarateHeaderDelete(HttpDelete delete, Map<String, Object> header) {
		if(header==null){
			return;
		}
		Set<String> keyset = header.keySet();
		for (String key : keyset) {
			delete.setHeader(key, header.get(key).toString());
		}
	}

	private static void genarateHeaderPut(HttpPut put, Map<String, Object> header) {
		if(header==null){
			return;
		}
		Set<String> keyset = header.keySet();
		for (String key : keyset) {
			put.setHeader(key, header.get(key).toString());
		}
	}

	private static void genarateHeaderOption(HttpOptions option, Map<String, Object> header) {	
		if(header==null){
			return;
		}
		Set<String> keyset = header.keySet();
		for (String key : keyset) {
			option.setHeader(key, header.get(key).toString());
		}
	}
	private static void genarateHeaderHead(HttpHead head, Map<String, Object> header) {
		if(header==null){
			return;
		}
		Set<String> keyset = header.keySet();
		for (String key : keyset) {
			head.setHeader(key, header.get(key).toString());
		}
	}
	/**
	 * 生成filedata用以上传文件 一旦包含filedata则将其
	 * 
	 * @param post
	 * @param filedata
	 */
	private static MultipartEntityBuilder generateFileData(Map<String, FileInfo> filedata,
			Map<String, Object> bodydata) {
		// 如果filedata为空，则返回空指针
		MultipartEntityBuilder multidata = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
		// 拼接multipart请求体
		for (Entry<String, FileInfo> entry : filedata.entrySet()) {
			multidata.addBinaryBody(entry.getKey(), new File(entry.getValue().getFilePath()),
					ContentType.create(entry.getValue().getContent_type()), entry.getValue().getFileName());
		}

		for (Entry<String, Object> entry : bodydata.entrySet()) {
			multidata.addTextBody(entry.getKey(), entry.getValue().toString(),
					ContentType.create("text/plain", Consts.UTF_8));
		}

		return multidata;
	}

	private static UrlEncodedFormEntity genaratePairData(Map<String, Object> pairdata)
			throws UnsupportedEncodingException {
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();

		for (Map.Entry<String, Object> key : pairdata.entrySet()) {
			pairs.add(new BasicNameValuePair(key.getKey(), key.getValue().toString()));
		}

		return new UrlEncodedFormEntity(pairs, "UTF-8");
	}

	private static StringEntity genarateStrEntity(String jsondata) {
		StringEntity strentity = new StringEntity(jsondata,ContentType.APPLICATION_JSON);
		return strentity;
	}

}
