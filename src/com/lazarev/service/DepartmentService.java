package com.lazarev.service;

import java.util.List;

import com.lazarev.db.dao.DepartmentDAO;
import com.lazarev.db.entity.Department;
import com.lazarev.db.entity.DepartmentSubject;
import com.lazarev.db.entity.Phase;
import com.lazarev.exception.MyAppException;

public class DepartmentService {

	public void insertDepWithMarks(Department department, List<DepartmentSubject> marks) {

		if (PhaseService.getCurrentPhase() == Phase.PRE_REGISTRATION) {
			if (department.getPlaceGov() > department.getTotaPlace())
				throw new MyAppException("places gov > places total");
			else
				new DepartmentDAO().insertDepartment(department, marks);
		} else {
			throw new MyAppException("Wrond phase for this operation");
		}
	}

}
