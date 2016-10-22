package com.quxue.yzlxth.model;

import java.io.Serializable;

public class PageInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int total; //总数

	private int pn; //当前页码

	private int rn; //行数

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPn() {
		return pn;
	}

	public void setPn(int pn) {
		this.pn = pn;
	}

	public int getRn() {
		return rn;
	}

	public void setRn(int rn) {
		this.rn = rn;
	}

}
