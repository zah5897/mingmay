package com.mingmay.cc.model;

public class Msg {
	public String userid;
	public String msg;
	public	String date;
	public String from;

	public Msg(String userid, String msg, String date, String from) {
		this.userid = userid;
		this.msg = msg;
		this.date = date;
		this.from = from;
	}
}
