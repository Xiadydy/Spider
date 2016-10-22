package com.quxue.yzlxth.spider;

public class SpiderThread implements Runnable {

	private Spider spider;

	public SpiderThread(Spider spider) {
		this.spider = spider;
	}

	@Override
	public void run() {
		spider.getFirst();
		spider.writeToFile();

	}

}
