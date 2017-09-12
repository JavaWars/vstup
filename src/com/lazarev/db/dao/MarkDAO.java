package com.lazarev.db.dao;

import java.util.List;

import com.lazarev.db.entity.Subject;

public class MarkDAO extends DAO<Subject,Integer> {

	@Override
	public Subject get(Integer key) {
		return null;
	}

	@Override
	public List<Subject> getAll() {
		return null;
	}

	@Override
	public boolean delete(Integer id) {
		return false;
	}

	@Override
	public boolean update(Subject entity) {
		return false;
	}

	@Override
	public boolean insert(Subject entity) {
		return false;
	}

}
