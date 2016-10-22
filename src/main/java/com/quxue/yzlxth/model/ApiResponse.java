package com.quxue.yzlxth.model;

import java.io.Serializable;

public class ApiResponse implements Serializable {

	private Integer errno;

	private String error;

	private ComposeDataPage data;

	public Integer getErrno() {
		return errno;
	}

	public void setErrno(Integer errno) {
		this.errno = errno;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public ComposeDataPage getData() {
		return data;
	}

	public void setData(ComposeDataPage data) {
		this.data = data;
	}

}
