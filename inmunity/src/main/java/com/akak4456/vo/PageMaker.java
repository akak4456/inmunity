package com.akak4456.vo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import lombok.Getter;
@Getter
public class PageMaker <T> {
	private Page<T> result;
	
	private Pageable prevPage;
	private Pageable nextPage;
	
	private int currentPageNum;
	private Pageable currentPage;
	
	private int totalPageNum;
	
	private List<Pageable> pageList;
	
	private boolean accessible;
	
	public PageMaker(Page<T> result) {
		this.result = result;
		this.currentPage = result.getPageable();
		this.currentPageNum = currentPage.getPageNumber() + 1;
		this.totalPageNum = result.getTotalPages();
		this.pageList = new ArrayList<>();
		this.prevPage = null;
		this.nextPage = null;
		this.accessible = currentPageNum <= totalPageNum;
		calcPage();
	}
	
	public void calcPage() {
		int rangeNum = (this.currentPageNum % PageConstant.DEFAULT_PAGE_SIZE == 0?
						this.currentPageNum / PageConstant.DEFAULT_PAGE_SIZE - 1:
						this.currentPageNum / PageConstant.DEFAULT_PAGE_SIZE);
		int startNum = rangeNum*PageConstant.DEFAULT_PAGE_SIZE + 1;
		int endNum = (rangeNum+1)*PageConstant.DEFAULT_PAGE_SIZE;
		if(endNum > this.totalPageNum)
			endNum = this.totalPageNum;
		Pageable startPage = this.currentPage;
		for(int i=startNum;i<this.currentPageNum;i++) {
			startPage = startPage.previousOrFirst();
		}
		if(rangeNum > 0) {
			this.prevPage = startPage.previousOrFirst();
		}
		for(int i=startNum;i<=endNum;i++) {
			this.pageList.add(startPage);
			startPage = startPage.next();
		}
		if(startPage.getPageNumber() < totalPageNum) {
			this.nextPage = startPage;
		}
	}
}
