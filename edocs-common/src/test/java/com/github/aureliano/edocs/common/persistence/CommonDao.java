package com.github.aureliano.edocs.common.persistence;

import java.util.List;

public class CommonDao implements IDao<CommonEntity> {

	@Override
	public CommonEntity save(CommonEntity entity) {
		return null;
	}

	@Override
	public void delete(CommonEntity entity) {}

	@Override
	public void delete(Integer id) {}

	@Override
	public CommonEntity find(Integer id) {
		return null;
	}

	@Override
	public List<CommonEntity> search(DataPagination<CommonEntity> dp) {
		return null;
	}

	@Override
	public List<CommonEntity> search(String query) {
		return null;
	}
}