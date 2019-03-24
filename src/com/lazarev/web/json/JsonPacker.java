package com.lazarev.web.json;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.lazarev.db.dao.DepartmentDAO;
import com.lazarev.db.dao.SubjectDAO;
import com.lazarev.db.entity.Department;
import com.lazarev.db.entity.DepartmentSubject;

//todo change this
public class JsonPacker {

	private static final Logger LOGGER=Logger.getLogger(JsonPacker.class);
	
	public static String packExistingDeprtment(int departmentId) {

		Department department = new DepartmentDAO().get(departmentId);

		JSONObject departmentJson = new JSONObject();
		departmentJson.put("departmentName", department.getName());
		departmentJson.put("totalPlaces", department.getTotaPlace());
		departmentJson.put("govPlace", department.getPlaceGov());
		departmentJson.put("id", department.getId());

		
		JSONArray marksJson = new JSONArray();
		
		List<DepartmentSubject> marks = new SubjectDAO().getAllSubjectsForDepartmentId(department.getId());
		for (int i = 0; i < marks.size(); i++) {
			JSONObject markJson = new JSONObject();
			DepartmentSubject s=marks.get(i);
			markJson.put("markName", s.getName());
			markJson.put("markMaxValue", s.getMaxMark());
			markJson.put("isUserEntered", s.getUserEntered());
			marksJson.put(markJson);	
		}
		departmentJson.put("marks", marksJson);

		return departmentJson.toString();
	}

	public static String listToJsonArray(List<String> cityList) {

		JSONArray array=new JSONArray();
		for (String s:cityList){
			array.put(s);
		}
		String result=array.toString();
		LOGGER.trace(result);
		return result;
	}

}
