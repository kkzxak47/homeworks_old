package com.zhongxun.bean;

import org.apache.lucene.search.ScoreDoc;

public class HitsBean {
	String field;
	int pageSize;
	String keyword;
	ScoreDoc lastDoc;
	int targetPage;
	int showingResult;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public ScoreDoc getLastDoc() {
		return lastDoc;
	}
	public void setLastDoc(ScoreDoc lastDoc) {
		this.lastDoc = lastDoc;
	}
	public int getTargetPage() {
		return targetPage;
	}
	public void setTargetPage(int targetPage) {
		this.targetPage = targetPage;
	}
	public int getShowingResult() {
		return showingResult;
	}
	public void setShowingResult(int showingResult) {
		this.showingResult = showingResult;
	}
}
