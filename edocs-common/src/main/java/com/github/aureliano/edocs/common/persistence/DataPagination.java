package com.github.aureliano.edocs.common.persistence;

public class DataPagination<T> {

	private IEntity entity;
	private Integer offset;
	private Integer limit;
	
	public DataPagination() {}

	public IEntity getEntity() {
		return entity;
	}

	public DataPagination<T> withEntity(IEntity entity) {
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