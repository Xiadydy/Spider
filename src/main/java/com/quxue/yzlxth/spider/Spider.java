package com.quxue.yzlxth.spider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.alibaba.fastjson.JSONReader;
import com.quxue.yzlxth.common.Config;
import com.quxue.yzlxth.model.ApiResponse;
import com.quxue.yzlxth.model.Article;

public class Spider {

	private CloseableHttpClient clients = HttpClients.createDefault();

	private String tag_id;

	private String url;

	private final List<Article> articles;

	public Spider(String tag_id, String url) {
		this.tag_id = tag_id;
		this.url = url;
		this.articles = new ArrayList<Article>();
	}

	/**
	 * 获取要拉取的全部文章
	 * @param pn
	 * @param rn
	 */
	public void getArticles(int pn, int rn) {
		String uri = generateUrl(url, pn, rn);
		HttpGet get = new HttpGet(uri);
		CloseableHttpResponse response = null;
		try {
			response = clients.execute(get);
		} catch (IOException e) {
			System.err.println("执行get方法请求数据失败");
			e.printStackTrace();
		}
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				JSONReader r = new JSONReader(reader)) {
			ApiResponse object = r.readObject(ApiResponse.class);
			List<Article> articles = object.getData().getList();
			List<Article> afterFilter = articles.stream().filter(a -> a.getType() == 1)
					.collect(Collectors.toList());
			this.articles.addAll(afterFilter);
		} catch (Exception e) {
			System.err.println("转换数据出错");
		}
	}

	/**
	 * 根据第一次拉倒的数据进行对该tag下的文章的全部拉取
	 * @param total
	 * @param rn
	 */
	public void getAllArticleGroupByTag(int total, int rn) {
		int totalPn = (total % rn == 0) ? total / rn : total / rn + 1;
		for (int pn = 1; pn <= totalPn; pn++) {
			getArticles(pn, rn);
		}
	}

	/**
	 * 开始爬数据
	 */
	public void getFirst() {
		String url = generateUrl(this.url, 1, 20);
		HttpGet get = new HttpGet(url);
		CloseableHttpResponse response = null;
		try {
			response = clients.execute(get);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				JSONReader r = new JSONReader(reader)) {
			ApiResponse object = r.readObject(ApiResponse.class);
			getAllArticleGroupByTag(object.getData().getPageInfo().getTotal(), 20);
		} catch (Exception e) {
		}
	}


	private String generateUrl(String url,int pn,int rn){
		return url + "?" + "pn=" + pn + "" + "&rn=" + rn + "&tag_id=" + tag_id;
	}

	public void writeToFile() {
		articles.forEach(article->{
			HttpGet get = new HttpGet(article.getUrl());
			try {
				CloseableHttpResponse response = clients.execute(get);
				write(article, response.getEntity().getContent());
			} catch (Exception e) {
				e.printStackTrace();
			}

		});
	}


	/**
	 *	将返回的text/html返写入到文件
	 * @param article
	 * @param stream
	 */
	public void write(Article article, InputStream stream) {
		String group = Config.childMap.get(article.getTags());
		if (group == null) {
			System.err.println(article.getTags());
		}
		File file = new File(Config.SAVE + group);
		if (!file.exists()) {
			file.mkdirs();
		}
		String base = file.getAbsolutePath();
		Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|]");
		Matcher matcher = pattern.matcher(article.getTitle());
		article.setTitle(matcher.replaceAll("")); // 将匹配到的非法字符以空替换
		String fileName = base + "\\" + article.getTitle() + ".html";
		System.out.println(fileName);
		File newFile = new File(fileName);
		try (FileOutputStream out = new FileOutputStream(newFile);
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream));) {
			String s = null;
			while ((s = reader.readLine()) != null) {
				String newStr = s.replace("\"//", "\"http://") + "\n";
				out.write(newStr.getBytes());
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * 程序入口
	 */
	public static void main(String[] args) {
		Spider spider = null;
		Config.init();
		ExecutorService executor = Executors.newFixedThreadPool(8);
		int b[] = Config.parentTag.stream().sorted((s1, s2) -> {
			return s1.compareTo(s2);
		}).mapToInt(Integer::valueOf).toArray();
		for (int i = b[0]; i <= b[b.length - 1]; i++) {
			spider = new Spider(String.valueOf(i), Config.ARTICLE_LIST_URL);
			SpiderThread run = new SpiderThread(spider);
			executor.execute(run);
		}
		executor.shutdown();

	}

}
