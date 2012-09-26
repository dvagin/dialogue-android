package com.AndroidRSSReader;
//@author Rocknglory studio
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

public class InternetReader {

	   private HttpClient httpclient;
	   private SchemeRegistry httpreg;
	   private HttpParams params;
	   

	   public static boolean checkConnection(Context ctx){
	       ConnectivityManager conMgr =  (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
	       NetworkInfo i = conMgr.getActiveNetworkInfo();
	       if(i==null){
	           Log.d("lbs","i is null");
	           return false;
	       }else{
	           if(i.isConnected()){
	               Log.d("lbs","is connected");
	               return true;
	           }else{
	               Log.d("lbs","is disconnected");
	               return false;
	           }
	       }
	       //
	       /*if(i.isConnected()){
	           Log.d("lbs","is connected");
	       }else{
	           Log.d("lbs","is disconnected");
	       }*/
	   }
	   
	   public InternetReader(){
	       httpreg = new SchemeRegistry();
	       params = new BasicHttpParams();
	       
	       HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	       HttpProtocolParams.setContentCharset(params, "utf-8");
	       params.setBooleanParameter("http.protocol.expect-continue", false); 
	       //registers schemes for both http and https
	       httpreg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	       /*final SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
	       sslSocketFactory.setHostnameVerifier(SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
	       registry.register(new Scheme("https", sslSocketFactory, 443));*/
	       
	       ClientConnectionManager cm = new ThreadSafeClientConnManager(params, httpreg);
	       httpclient = new DefaultHttpClient(cm,params);
	   }
	   
	   
	   public HttpResponse initAction(List<NameValuePair> pairslist,Links link){
	          
	          HttpPost httppost = new HttpPost(link.toString());
	          Log.d("lbs","http post constructed");
	          try {
	              UrlEncodedFormEntity form = new UrlEncodedFormEntity(pairslist,HTTP.UTF_8);
	              //UrlEncodedFormEntity form = new UrlEncodedFormEntity()
	              form.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
	              form.setContentEncoding("UTF-8");
	              //httppost.setEntity(new StringEntity("","UTF-8"));
	              
	              HttpResponse response = httpclient.execute(httppost);
	              return response;
	          } catch (UnsupportedEncodingException e) {
	              // TODO Auto-generated catch block
	              e.printStackTrace();
	          } catch (ClientProtocolException e) {
	              // TODO Auto-generated catch block
	              e.printStackTrace();
	          } catch (IOException e) {
	              // TODO Auto-generated catch block
	              e.printStackTrace();
	          }/**/
	          return null;

	      }
	   
	   public HttpResponse initAction(String rawbody,Links link){
	          
	          HttpPost httppost = new HttpPost(link.toString());
	          Log.d("lbs","http post constructed");
	          try {
	              //UrlEncodedFormEntity form = new UrlEncodedFormEntity(pairslist,HTTP.UTF_8);
	              //UrlEncodedFormEntity form = new UrlEncodedFormEntity()
	              /*form.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
	              form.setContentEncoding("UTF-8");*/
	              httppost.setEntity(new StringEntity(rawbody,"UTF-8"));
	              httppost.setHeader("Content-type", "application/x-www-form-urlencoded");
	              
	              HttpResponse response = httpclient.execute(httppost);
	              return response;
	          } catch (UnsupportedEncodingException e) {
	              // TODO Auto-generated catch block
	              e.printStackTrace();
	          } catch (ClientProtocolException e) {
	              // TODO Auto-generated catch block
	              e.printStackTrace();
	          } catch (IOException e) {
	              // TODO Auto-generated catch block
	              e.printStackTrace();
	          }/**/
	          return null;

	      }
	   

	public enum Links {
	   LINKONE {
	       public String toString() {
	           return "http://rocknglory.ru/1.xml";
	       }
	   }
	}
	   
	   public String setRequest(List<NameValuePair> pairslist, Links link){
	       Log.d("lbs","setRequest");
	       
	       HttpResponse httpresponse = initAction(pairslist, link);
	       if(httpresponse != null){
	           try {
	               InputStream is = httpresponse.getEntity().getContent();
	               //is.close();
	               String returnstr = InternetReader.convertStreamToString(is);
	               //String html = EntityUtils.toString(httpresponse.getEntity() /*, Encoding*/ );
	               //
	               httpresponse.getEntity().consumeContent();
	               return returnstr;
	           } catch (IllegalStateException e) {
	               e.printStackTrace();
	           } catch (IOException e) {
	               e.printStackTrace();
	           }
	       }
	       return null;
	   }
	   
	   public void reqFinished(String outstr){
	       Log.d("lbs",outstr);
	   }
	   
	   public void setAsyncRequest(final List<NameValuePair> pairslist, final Links link){
	       Runnable async = new Runnable(){
	           @Override
	           public void run() {
	               HttpResponse httpresponse = initAction(pairslist, link);
	               if(httpresponse != null){
	                   try {
	                       InputStream is = httpresponse.getEntity().getContent();
	                       String returnstr = InternetReader.convertStreamToString(is);
	                       httpresponse.getEntity().consumeContent();
	                       reqFinished(returnstr);
	                   } catch (IllegalStateException e) {
	                       e.printStackTrace();
	                   } catch (IOException e) {
	                       e.printStackTrace();
	                   }
	               }
	               
	           }
	           
	       };
	       new Thread(async).start();
	   }
	   
	   public void setAsyncRequest(final String rawbody, final Links link){
	       Runnable async = new Runnable(){
	           @Override
	           public void run() {
	               HttpResponse httpresponse = initAction(rawbody, link);
	               if(httpresponse != null){
	                   try {
	                       InputStream is = httpresponse.getEntity().getContent();
	                       String returnstr = InternetReader.convertStreamToString(is);
	                       httpresponse.getEntity().consumeContent();
	                       reqFinished(returnstr);
	                   } catch (IllegalStateException e) {
	                       e.printStackTrace();
	                   } catch (IOException e) {
	                       e.printStackTrace();
	                   }
	               }
	               
	           }
	           
	       };
	       new Thread(async).start();
	   }
	   
	   static String convertStreamToString(InputStream is) {

	       BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	       StringBuilder sb = new StringBuilder();

	       String line = null;
	       try {
	           while ((line = reader.readLine()) != null) {
	               sb.append((line + "\n"));
	           }
	       } catch (IOException e) {
	           e.printStackTrace();
	       } finally {
	               try {
	                   is.close();
	               } catch (IOException e) {
	                   // TODO Auto-generated catch block
	                   e.printStackTrace();
	               }
	       }
	       return sb.toString();
	   }
	   
	   
	   public static Document getDomElement(String xml){
	       Document doc = null;
	       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	       try {

	           DocumentBuilder db = dbf.newDocumentBuilder();

	           InputSource is = new InputSource();
	               is.setCharacterStream(new StringReader(xml));
	               doc = db.parse(is);

	           } catch (ParserConfigurationException e) {
	               Log.e("Error: ", e.getMessage());
	               return null;
	           } catch (SAXException e) {
	               Log.e("Error: ", e.getMessage());
	               return null;
	           } catch (IOException e) {
	               Log.e("Error: ", e.getMessage());
	               return null;
	           }
	               // return DOM
	           return doc;
	   }
	   
	   public static final String getElementValue( Node elem ) {
	       Node child;
	       if( elem != null){
	           if (elem.hasChildNodes()){
	               for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
	                   if( child.getNodeType() == Node.TEXT_NODE  ){
	                       return child.getNodeValue();
	                   }
	               }
	           }
	       }
	       return "";
	}

	   
	   public static String getValue(Element item, String str) {
	       NodeList n = item.getElementsByTagName(str);
	       return getElementValue(n.item(0));
	   }

	   
	   public static ContentValues parseXml(String answer,String rootElement){
		   ContentValues cv = new ContentValues();
		   Document doc = InternetReader.getDomElement(answer);
		   NodeList nl = doc.getElementsByTagName(rootElement);
		   NodeList nlc = nl.item(0).getChildNodes();
		   for(int i = 0;i<nlc.getLength();i++){
			   cv.put(nlc.item(i).getNodeName(), InternetReader.getValue((Element)nl.item(0),nlc.item(i).getNodeName()));
			   Log.d("ekv","name "+nlc.item(i).getNodeName()+" value "+InternetReader.getValue((Element)nl.item(0),nlc.item(i).getNodeName()));
		   }
		return cv;
	   }
	   
	   
	   
	    

	}
