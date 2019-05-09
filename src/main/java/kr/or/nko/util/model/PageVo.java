package kr.or.nko.util.model;

import lombok.Data;

@Data
public class PageVo {
	private int page; 			// 페이지 번호
	private int pageSize; 		// 페이지당 사이즈
	
	private Integer ntc_div;		// 게시판 구분
	
	private String res_nm;		// 설문조사 제목
	private String res_state;	// 설문조사 상태
	
	private String data_man;	// 자료실 작성자
	private String data_nm;		// 자료실 제목

	public PageVo(){
		
	}
	public PageVo(int page, int pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}
	
	public int getPage() {
		return page == 0 ? 1 : page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize == 0 ? 10 : pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getNtc_div() {
		return ntc_div;
	}
	public void setNtc_div(Integer ntc_div) {
		this.ntc_div = ntc_div;
	}

	@Override
	public String toString() {
		return "PageVo [page=" + page + ", pageSize=" + pageSize + "]";
	}
	
}