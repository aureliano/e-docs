package com.github.aureliano.edocs.common.persistence;

public class DataPagination<T> {

	private T entity;
	private Integer offset;
	private Integer limit;
	
	public DataPagination() {}

	public T getEntity() {
		return entity;
	}

	public DataPagination<T> withEntity(T entity) {
		this.entity = entity;
		return this;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}