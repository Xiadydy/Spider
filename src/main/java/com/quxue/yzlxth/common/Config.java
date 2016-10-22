package com.quxue.yzlxth.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;

public class Config {

	public static final String ARTICLE_LIST_URL = "http://api.tengyue360.com/api/content/document/list";

	public static final String INDEX = "http://api.tengyue360.com/api/common/homedata";

	public static final ArrayList<String> parentTag = new ArrayList<String>();

	public static final HashMap<String, String> childMap = new HashMap<String, String>();

	public static final String SAVE = "C:/资料";

	public static void init() {

		CloseableHttpClient clients = HttpClients.createDefault();
		HttpGet get = new HttpGet(INDEX);
		CloseableHttpResponse response = null;
		try {
			response = clients.execute(get);
		} catch (IOException e1) {
			System.err.println("拉取数据失败");
			e1.printStackTrace();
		}

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				JSONReader r = new JSONReader(reader)) {
			JSONObject object = JSONObject.parseObject(r.readString());
			JSONObject data = (JSONObject) object.get("data");
			JSONArray tags = (JSONArray) data.get("tags");
			tags.forEach(map -> {
				JSONObject m = null;
				if (map instanceof JSONObject) {
					m = (JSONObject) map;
				}
				String parentId = (String) m.get("id");
				String parentName = (String) m.get("name");
				parentTag.add(parentId);
				JSONArray o = (JSONArray) m.get("children");
				o.forEach(c -> {
					JSONObject b = null;
					if (c instanceof JSONObject) {
						b = (JSONObject) c;
					}
					String key = parentId + "," + b.getString("id");
					String value = "/" + parentName + "/" + b.getString("name");
					childMap.put(key, value);
				});
			});
			for (String a : childMap.keySet()) {
				System.out.println(a);
				System.out.println(childMap.get(a));

			}
			System.out.println(childMap.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
