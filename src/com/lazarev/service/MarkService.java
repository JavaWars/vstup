package com.lazarev.service;

import java.util.List;

import com.lazarev.db.dao.MarkDAO;
import com.lazarev.db.entity.Department;
import com.lazarev.db.entity.Phase;
import com.lazarev.db.entity.StudentMark;
import com.lazarev.exception.MyAppException;

public class MarkService {

	public void insertUserMark(String userLogin, Department department, List<StudentMark> marks) {

		if (PhaseService.getCurrentPhase() == Phase.DOCUMENT_SERVE) {
			//check user diplom 
			//checking is marks original and user dont modify marks list or s.o
			// add to unchecked marks
			new MarkDAO().insertUserMarks(department, marks, userLogin);
		}
		else {
			throw new MyAppException("incorrect operation for current phase");
		}
	}

	public void insertMarkForStudent(int userId, int subjectId, double mark) {
		new MarkDAO().insertUserMark(userId,subjectId,mark);
	}

}
