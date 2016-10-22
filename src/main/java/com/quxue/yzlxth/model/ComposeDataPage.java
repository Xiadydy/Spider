package com.quxue.yzlxth.model;

import java.io.Serializable;
import java.util.List;

public class ComposeDataPage implements Serializable {

	private static final long serialVersionUID = 1L;

	private PageInfo pageInfo;

	private List<Article> list;

	public PageInfo getPageInfo() {
		return pageInfo;
	}

	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}

	public List<Article> getList() {
		return list;
	}

	public void setList(List<Article> list) {
		this.list = list;
	}

}
