package com.lazarev.service;

import java.util.List;

import com.lazarev.db.dao.MarkDAO;
import com.lazarev.db.dao.SubjectDAO;
import com.lazarev.db.dao.UserDAO;
import com.lazarev.db.entity.Department;
import com.lazarev.db.entity.Phase;
import com.lazarev.db.entity.StudentMark;
import com.lazarev.db.entity.Subject;
import com.lazarev.db.entity.User;
import com.lazarev.exception.MyAppException;
import com.lazarev.web.servlets.helper.ImgConverter;

public class MarkService {

	public void insertUserMark(String userLogin, Department department, List<StudentMark> marks) {

		if (PhaseService.getCurrentPhase() == Phase.DOCUMENT_SERVE) {

			int currentUserId = new UserDAO().getIdByEmail(userLogin);
			// check user diplom
			User u = new UserDAO().get(currentUserId);
			if (u.getDiplom().length() < 8) {
				throw new MyAppException("diplom is not valid");
			}
			else {
				//checking image for diplom
				 if (!ImgConverter.checkIsDiplomImageExistForUser(String.valueOf(u.getId()))) {
					 throw new MyAppException("no diplom screen");
				 }
			}

			boolean b = true;
			// add to unchecked marks
			for (StudentMark sm : marks) {
				b &= new MarkDAO().insertQueryToCheckByAdmin(sm, currentUserId);
			}

			if (b) {
				//todo checking is marks original and user dont modify marks list or s.o
				new MarkDAO().insertUserMarks(department, marks, userLogin);
			}
		} else {
			throw new MyAppException("incorrect operation for current phase");
		}
	}

	public void insertMarkForStudent(int userId, int subjectId, double mark) {
		new MarkDAO().insertUserMark(userId, subjectId, mark);
	}

	public List<Subject> getAllMarksSettedByAdmin() {
		return new SubjectDAO().getMarksSetedByAdmin();
	}
}
