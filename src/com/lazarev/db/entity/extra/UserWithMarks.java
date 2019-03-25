package com.lazarev.db.entity.extra;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import com.lazarev.db.entity.StudentMark;
import com.lazarev.db.entity.User;

public class UserWithMarks extends User {

	private List<StudentMark> studentMarks=new LinkedList<>();

	public UserWithMarks(User u) {
		setId(u.getId());
		setFio(u.getFio());
		setEmail(u.getEmail());
		setDiplom(u.getDiplom());
		setPhone(u.getPhone());
		setName(u.getName());
		setBirthday(u.getBirthday());
	}

	public List<StudentMark> getStudentMarks() {
		return studentMarks;
	}

	public void setStudentMarks(List<StudentMark> studentMarks) {
		this.studentMarks = studentMarks;
	}
}
