package com.AndroidRSSReader;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.text.Html;
import android.util.Log;

public class RssItem {

	private String title;
	private String description;
	private Date pubDate;
	private String link;
	private String detail;

	public RssItem(String title, String description, Date pubDate, String link,String detail) {
		this.title = title;
		this.description = description;
		this.pubDate = pubDate;
		this.link = link;
		this.detail = detail;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public String getDetail(){
		return detail;
	}
	
	public void setDetail(String detail){
		this.detail = detail;
	}

	@Override
	public String toString() {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd - hh:mm:ss");

		String result = getTitle() + "   ( " + sdf.format(this.getPubDate()) + " )" + "\n"+getDescription();
		return result;
	}

	public static ArrayList<RssItem> getRssItems(String feedUrl) {

		ArrayList<RssItem> rssItems = new ArrayList<RssItem>();

		try {
			//open an URL connection make GET to the server and 
			//take xml RSS data
			URL url = new URL(feedUrl);
			Log.d("log","before conn");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream is = conn.getInputStream();
				Log.d("log","after conn");
				//DocumentBuilderFactory, DocumentBuilder are used for 
				//xml parsing
				DocumentBuilderFactory dbf = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();

				//using db (Document Builder) parse xml data and assign
				//it to Element
				Document document = db.parse(is);
				Element element = document.getDocumentElement();

				//take rss nodes to NodeList
				NodeList nodeList = element.getElementsByTagName("item");

				if (nodeList.getLength() > 0) {
					for (int i = 0; i < nodeList.getLength(); i++) {
						
						//take each entry (corresponds to <item></item> tags in 
						//xml data
						
						Element entry = (Element) nodeList.item(i);
						
						Element _titleE = (Element) entry.getElementsByTagName(
								"title").item(0);
						Element _descriptionE = (Element) entry
								.getElementsByTagName("description").item(0);
						Element _pubDateE = (Element) entry
								.getElementsByTagName("pubDate").item(0);
						Element _linkE = (Element) entry.getElementsByTagName(
								"link").item(0);
						Log.d("taaaag","length "+_descriptionE.getChildNodes().getLength());
						Log.d("taaaag",_descriptionE.getFirstChild().getNodeValue());
						String _title = _titleE.getFirstChild().getNodeValue();
						String _description = _descriptionE.getFirstChild().getNodeValue();
						String _detail = _descriptionE.getFirstChild().getNodeValue();
						int idx1 = _description.indexOf("</p>");
						int idx2 = _description.indexOf("</div>");
						Log.d("view","string is "+_description.substring(idx1+4, idx2));
						Log.d("view","idx1 is "+idx1 + " and idx2 is "+idx2);
						Date _pubDate = new Date(_pubDateE.getFirstChild().getNodeValue());
						String _link = _linkE.getFirstChild().getNodeValue();
						_description = _description.substring(idx1+4, idx2);
						idx1 = _description.indexOf("<p>", _description.indexOf("<p>")+4);
						idx2 = _description.indexOf("</p>",idx1);
						_description = _description.substring(idx1+4, idx2);
						//create RssItemObject and add it to the ArrayList
						RssItem rssItem = new RssItem(_title, _description,
								_pubDate, _link, _detail);

						rssItems.add(rssItem);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rssItems;
	}
}