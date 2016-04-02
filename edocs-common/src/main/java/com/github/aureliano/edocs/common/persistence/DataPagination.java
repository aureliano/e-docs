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

	public DataPagination<T> withOffset(Integer offset) {
		this.offset = offset;
		return this;
	}

	public Integer getLimit() {
		return limit;
	}

	public DataPagination<T> withLimit(Integer limit) {
		this.limit = limit;
		return this;
	}
}