package com.github.pagehelper;

import java.io.Serializable;
import java.util.List;

public class PageInfoSimple<T> implements Serializable {

	//总记录数
    private long total;
	 //结果集
    private List<T> rows;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	
	public PageInfoSimple() {
		// TODO Auto-generated constructor stub
	}
	
	public PageInfoSimple(List<T> rows,int total){
		this.rows=rows;
		this.total=total;
	}
}
