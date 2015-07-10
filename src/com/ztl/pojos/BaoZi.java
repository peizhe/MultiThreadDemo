package com.ztl.pojos;

/**
 * 包子的封装类
 * 
 * @author zel
 * 
 */
public class BaoZi {
	// 编号，标志包了的唯 一性
	private String index;

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public BaoZi(String index) {
		this.index = index;
	}

}
