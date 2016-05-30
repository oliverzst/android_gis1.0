package com.pdt.android_gis.security;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.ContactsContract.Data;
import android.util.Log;

import com.google.gson.Gson;
import com.pdt.android_gis.util.Constants;
import com.pdt.android_gis.util.LogUtil;
import com.pdt.android_gis.util.PropertiesUtil;

/**
 * 
 * Restful
 * 
 * @author Chasel.li
 *
 */
public class RestTemplate {

	private static final String LOGTAG = LogUtil.makeLogTag(RestTemplate.class);
	private HttpClient client = null;
	private String path = "http://192.168.31.156:8080/quickstart/login";
	private Gson gson;
	private static List<State> states;
	
	public RestTemplate () {
		HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 50 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
		client = new DefaultHttpClient(httpParams);
		gson = new Gson();
	}
	
	/**
	 * Restful get
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String httpGetDemo(String username, String password) throws UnsupportedEncodingException {
		String url;
	    url = "{username"+":"+username+"}";
	    String uri = URLEncoder.encode(url, "utf-8"); 
	    System.out.println(path+"/"+url);
		HttpGet httpGet = new HttpGet(path+"/"+url);
		HttpResponse response = null;
		StringBuffer result = new StringBuffer();
		try {
			response = client.execute(httpGet);
		} catch (ClientProtocolException e) {
			return "httpGetDemo Request ClientProtocolException :" + e.getMessage();
		} catch (IOException e) {
			return "httpGetDemo Request IOException :" + e.getMessage();
		}
		try {
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String responseResult = "";
			while ((responseResult = reader.readLine()) != null) {
				result.append(responseResult);
			}
			reader.close();
		} catch (IllegalStateException e) {
			return "httpGetDemo Response IllegalStateException" + e.getMessage();
		} catch (IOException e) {
			return "httpGetDemo Response IOException" + e.getMessage();
		}
		Log.i(LOGTAG, result.toString());
		return result.toString();
	}
	
	/**
	 * Restful post
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws JSONException 
	 */
	public String httpPostDemo(String username, String password) throws UnsupportedEncodingException, JSONException {

//		String url;
//	    url = "{username"+":"+username+"}";
//	    String uri = URLEncoder.encode(url, "utf-8");
//	    System.out.println(path+"/"+url);
//		JSONObject jsonObj = new JSONObject();
//		jsonObj.put("username", username);
//		StringEntity entity = new StringEntity(jsonObj.toString(),HTTP.UTF_8);
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
		StringBuffer IP = new StringBuffer();
		IP.append("http://");
		IP.append(Constants.ipaddress);
		IP.append(":8080/quickstart/mobilelogin");
		path = IP.toString();
		HttpPost httpPost = new HttpPost(path);		
//		entity.setContentType("text/html");
		StringBuffer result = new StringBuffer();
		HttpResponse response = null;
//		HttpEntity httpEntity = null;
//		final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
//		State state = new State();
//		state.setName("username");
//		state.setAbbreviation(username);
//		params.add(new BasicNameValuePair("states", gson.toJson(state)));
//		try {
//			httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
//		} catch (UnsupportedEncodingException e) {
//			return "HttpPostDemo request httpEntity UnsupportedEncodingException:" + e.getMessage();
//		}
		httpPost.setEntity(entity);
		try {
			response = client.execute(httpPost);
		} catch (ClientProtocolException e) {
			return "httpPostDemo Request ClientProtocolException:" + e.getMessage();
		} catch (IOException e) {
			return "httpPostDemo Request IOException" + e.getMessage();
		}
		try {
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String responseResult = "";
			while ((responseResult = reader.readLine()) != null) {
				result.append(responseResult);
			}
			reader.close();
		} catch (IllegalStateException e) {
			return "httpPostDemo Response IllegalStateException:" + e.getMessage();
		} catch (IOException e) {
			return "httpPostDemo Response IOException" + e.getMessage();
		}
		Log.i(LOGTAG, result.toString());
		return result.toString();
	}
	
	public String httpPostDemo1(String baseid, String domain, double longtitude,double latitude,double rss
			,int rssul,int rssdl, String rssdate) throws UnsupportedEncodingException, JSONException {

		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("baseid", baseid));
		params.add(new BasicNameValuePair("domain", domain));
		params.add(new BasicNameValuePair("longtitude", String.valueOf(longtitude)));
		params.add(new BasicNameValuePair("latitude", String.valueOf(latitude)));
		params.add(new BasicNameValuePair("rss", String.valueOf(rss)));
		params.add(new BasicNameValuePair("rssul", String.valueOf(rssul)));
		params.add(new BasicNameValuePair("rssdl", String.valueOf(rssdl)));
		params.add(new BasicNameValuePair("rssdate", rssdate));
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
		StringBuffer IP = new StringBuffer();
		IP.append("http://");
		IP.append(Constants.ipaddress);
		IP.append(":8080/quickstart/coverdata/create");
		path = IP.toString();
		HttpPost httpPost = new HttpPost(path);		

		StringBuffer result = new StringBuffer();
		HttpResponse response = null;

		httpPost.setEntity(entity);
		try {
			response = client.execute(httpPost);
		} catch (ClientProtocolException e) {
			return "httpPostDemo Request ClientProtocolException:" + e.getMessage();
		} catch (IOException e) {
			return "httpPostDemo Request IOException" + e.getMessage();
		}
		try {
//			HttpEntity entity1 = response.getEntity();
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String responseResult = "";
			while ((responseResult = reader.readLine()) != null) {
				result.append(responseResult);
			}
			reader.close();
		} catch (IllegalStateException e) {
			return "httpPostDemo Response IllegalStateException:" + e.getMessage();
		} catch (IOException e) {
			return "httpPostDemo Response IOException" + e.getMessage();
		}
		Log.i(LOGTAG, result.toString());
		return result.toString();
	}
	
	public String httpPostDemo2() throws UnsupportedEncodingException, JSONException {
		
		StringBuffer IP = new StringBuffer();
		IP.append("http://");
		IP.append(Constants.ipaddress);
		IP.append(":8080/quickstart/baseinfo");
		path = IP.toString();
		HttpPost httpPost = new HttpPost(path);		

		StringBuffer result = new StringBuffer();
		HttpResponse response = null;

		try {
			response = client.execute(httpPost);
		} catch (ClientProtocolException e) {
			return "httpPostDemo Request ClientProtocolException:" + e.getMessage();
		} catch (IOException e) {
			return "httpPostDemo Request IOException" + e.getMessage();
		}
		try {
//			HttpEntity entity1 = response.getEntity();
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String responseResult = "";
			while ((responseResult = reader.readLine()) != null) {
				result.append(responseResult);
			}
			reader.close();
		} catch (IllegalStateException e) {
			return "httpPostDemo Response IllegalStateException:" + e.getMessage();
		} catch (IOException e) {
			return "httpPostDemo Response IOException" + e.getMessage();
		}
		Log.i(LOGTAG, result.toString());
		return result.toString();
	}
	
	/**
	 * Restful put
	 * 
	 * 
	 * @return 
	 */
	public String httpPutDemo(String username, String password) {
		StringBuilder url = new StringBuilder(path);
	    url.append("/{" + username + "}/{" + password + "}");
	    System.out.println(url);
		StringBuffer result = new StringBuffer();
		HttpPut httpPut = new HttpPut(url.toString()+"?state=hello");
		HttpResponse response = null;
//		HttpEntity httpEntity = null;
//		final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
//		State state = new State();
//		state.setName("鍗椾含");
//		state.setAbbreviation("NJ");
//		params.add(new BasicNameValuePair("state", gson.toJson(state)));
//		try {
//			httpEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
//		} catch (UnsupportedEncodingException e) {
//			return "httpPutDemo request httpEntity UnsupportedEncodingException:" + e.getMessage();
//		} 
//		httpPut.setEntity(httpEntity);
		try {
			response = client.execute(httpPut);
		} catch (ClientProtocolException e) {
			return "httpPutDemo Request ClientProtocolException";
		} catch (IOException e) {
			return "httpPutDemo Request IOException";
		}
		try {
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String responseResult = "";
			while ((responseResult = reader.readLine()) != null) {
				result.append(responseResult);
			}
			reader.close();
		} catch (IllegalStateException e) {
			return "httpPutDemo Response IllegalStateException:" + e.getMessage();
		} catch (IOException e) {
			return "httpPutDemo Response IOException:" + e.getMessage();
		}
		Log.i(LOGTAG, result.toString());
		return result.toString();
	}
	
	/**
	 * Restful delete
	 * @return 
	 */
	public String httpDeleteDemo(String username, String password) {
		StringBuilder url = new StringBuilder(path);
	    url.append("/{" + username + "}/{" + password + "}");
	    System.out.println(url);
		StringBuffer result = new StringBuffer();
		final Map<String, String> params = new HashMap<String, String>();
		params.put("id", "1");
		HttpDelete httpDelete = new HttpDelete(packageUri(url.toString(), params));
		HttpResponse response = null;
		try {
			response = client.execute(httpDelete);
		} catch (ClientProtocolException e) {
			return "httpDeleteDemo Request ClientProtocolException:" + e.getMessage();
		} catch (IOException e) {
			return "httpDeleteDemo Request IOException:" + e.getMessage();
		}
		try {
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String responseResult = "";
			while ((responseResult = reader.readLine()) != null) {
				result.append(responseResult);
			}
			reader.close();
		} catch (IllegalStateException e) {
			return "httpDeleteDemo Response IllegalStateException:" + e.getMessage();
		} catch (IOException e) {
			return "httpDeleteDemo Response IOException:" + e.getMessage();
		}
		Log.i(LOGTAG, result.toString());
		return result.toString();
	}
	
	/**
	 * URL, 
	 * @param uri 
	 * @param params
	 * @return 
	 */
	@SuppressWarnings("rawtypes")
	public String packageUri(String uri, Map<String, String> params) {
		String paramStr = "";
		Iterator<?> iter = params.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = entry.getKey().toString();
			String val = entry.getValue().toString();
			try {//
				paramStr += paramStr = "&" + URLEncoder.encode(key, HTTP.UTF_8) + "=" + URLEncoder.encode(val, HTTP.UTF_8);
			} catch (UnsupportedEncodingException e) {
				return "packageUri UnsupportedEncodingException:" + e.getMessage();
			}
		}
		if (!paramStr.equals("")) {
			paramStr = paramStr.replaceFirst("&", "?");
			uri += paramStr;
		}
		return uri;
	}
	
	 /**
     * @return a List of states
     */
    private List<State> getStates() {
        if (states == null) {
            states = new ArrayList<State>();
            states.add(new State("北京", "BJ"));
            states.add(new State("上海", "SH"));
            states.add(new State("广州", "GZ"));
            states.add(new State("深圳", "SZ"));
        }
        return states;
    }
}
