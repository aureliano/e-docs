package com.github.aureliano.edocs.common.persistence;

import java.util.List;

@SuppressWarnings("hiding")
public interface IDao<IEntity> {

	public abstract IEntity save(IEntity entity);
	
	public abstract void delete(IEntity entity);
	
	public abstract void delete(Integer id);
	
	public abstract IEntity find(Integer id);
	
	public abstract List<IEntity> search(DataPagination<IEntity> dataPagination);
	
	public abstract List<IEntity> search(String query);
}