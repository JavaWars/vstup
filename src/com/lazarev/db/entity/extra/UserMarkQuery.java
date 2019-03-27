package com.lazarev.db.entity.extra;

public class UserMarkQuery {

	private String fio;
	private String diplom;
	private Double maxPosibleMark;
	private Double mark;
	private int userId;
	private String markOriginalName;
	private int queryId;
	private String diplomImg;
	
	public String getDiplomImg() {
		return diplomImg;
	}
	public void setDiplomImg(String diplomImg) {
		this.diplomImg = diplomImg;
	}
	public Double getMark() {
		return mark;
	}
	public void setMark(Double mark) {
		this.mark = mark;
	}
	public int getQueryId() {
		return queryId;
	}
	public void setQueryId(int queryId) {
		this.queryId = queryId;
	}
	public String getFio() {
		return fio;
	}
	public void setFio(String fio) {
		this.fio = fio;
	}
	
	public String getDiplom() {
		return diplom;
	}
	public void setDiplom(String diplom) {
		this.diplom = diplom;
	}
	public Double getMaxPosibleMark() {
		return maxPosibleMark;
	}
	public void setMaxPosibleMark(Double maxPosibleMark) {
		this.maxPosibleMark = maxPosibleMark;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getMarkOriginalName() {
		return markOriginalName;
	}
	public void setMarkOriginalName(String markOriginalName) {
		this.markOriginalName = markOriginalName;
	}
	@Override
	public String toString() {
		return "UserMarkQuery [fio=" + fio + ", diplom=" + diplom + ", maxPosibleMark="
				+ maxPosibleMark +  ", userId=" + userId + ", markOriginalName="
				+ markOriginalName + "]";
	}
}
