package com.akak4456.vo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageVO {
	private int page;
	private int size;
	private String dest;
	public PageVO() {
		this.page = 1;
		this.size = PageConstant.DEFAULT_PAGE_SIZE;
	}
	public PageVO(int page) {
		this.setPage(page);
		this.size = PageConstant.DEFAULT_PAGE_SIZE;
	}
	public int getPage() {
		return this.page;
	}
	public void setPage(int page) {
		this.page = page >= 1 ? page: 1;
	}
	public int getSize() {
		return this.size;
	}
	public void setSize(int size) {
		this.size = size < PageConstant.DEFAULT_PAGE_SIZE || size > PageConstant.DEFAULT_MAX_PAGE_SIZE
				? PageConstant.DEFAULT_PAGE_SIZE : size;
	}
	public Pageable makePageble(int direction,String... props) {
		//props에는 데이터베이스 테이블 컬럼명이 들어가게 됨
		Sort.Direction dir = direction == 0?Sort.Direction.DESC:Sort.Direction.ASC;
		return PageRequest.of(this.page-1,this.size, dir,props);
	}
	public String getDest() {
		return this.dest;
	}
	public void setDest(String dest) {
		this.dest = dest;
	}
}
