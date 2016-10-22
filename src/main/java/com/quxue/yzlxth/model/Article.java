package com.quxue.yzlxth.model;

import java.io.Serializable;

public class Article implements Serializable {

	private static final long serialVersionUID = 1L;

	private String document_id;

	private String id;

	private String title;

	private String tags;

	private Integer score;

	private String description;

	private String cover_pic_url;

	private Integer type;

	private String url;

	private Long create_ts;

	private Integer view_count;

	private Integer share_count;

	private Integer fav_count;

	public String getDocument_id() {
		return document_id;
	}

	public void setDocument_id(String document_id) {
		this.document_id = document_id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCover_pic_url() {
		return cover_pic_url;
	}

	public void setCover_pic_url(String cover_pic_url) {
		this.cover_pic_url = cover_pic_url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getCreate_ts() {
		return create_ts;
	}

	public void setCreate_ts(Long create_ts) {
		this.create_ts = create_ts;
	}

	public Integer getView_count() {
		return view_count;
	}

	public void setView_count(Integer view_count) {
		this.view_count = view_count;
	}

	public Integer getShare_count() {
		return share_count;
	}

	public void setShare_count(Integer share_count) {
		this.share_count = share_count;
	}

	public Integer getFav_count() {
		return fav_count;
	}

	public void setFav_count(Integer fav_count) {
		this.fav_count = fav_count;
	}
	
}
