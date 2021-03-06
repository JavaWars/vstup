package com.lazarev.web.json;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.lazarev.db.entity.Department;
import com.lazarev.db.entity.DepartmentSubject;
import com.lazarev.db.entity.StudentMark;

//todo change this
public class JsonExtracter {

	private static final Logger LOGGER = Logger.getLogger(JsonExtracter.class);

	public static void extractDepartmnet(String str, Department department, List<DepartmentSubject> marks) {

		LOGGER.debug("extract new department"+str);
		JSONObject root = new JSONObject(str);
		department.setName(root.getString("departmentName"));
		department.setPlaceGov(root.getInt("placesGov"));
		department.setTotaPlace(root.getInt("placesTotal"));

		JSONArray arr = root.getJSONArray("json");
		for (int i = 0; i < arr.length(); i++) {
			DepartmentSubject mark = new DepartmentSubject();
			mark.setName(arr.getJSONObject(i).getJSONObject("mark").getString("name"));
			mark.setMaxMark(arr.getJSONObject(i).getJSONObject("mark").getDouble("markMaxValue"));
			mark.setUserEntered(arr.getJSONObject(i).getJSONObject("mark").getBoolean("is_user_entered"));
			marks.add(mark);
		}
	}

	public static void extractExistingDepartmnet(String str, Department departmnet, List<DepartmentSubject> marks) {
		LOGGER.debug("extract existing department"+str);
		extractDepartmnet(str,departmnet,marks);
		departmnet.setId(new JSONObject(str).getInt("id"));
	}

	public static void extractUserMarks(String str, Department department, List<StudentMark> marks) {
		LOGGER.debug("extract users marks for department"+str);
		//{"json":[{"mark":{"userMark":"10","id":"5"}}],"departmentId":10}

		JSONObject root = new JSONObject(str);
		department.setId(root.getInt("departmentId"));
		
		JSONArray arr = root.getJSONArray("json");
		for (int i = 0; i < arr.length(); i++) {
			StudentMark studentMark=new StudentMark();
			studentMark.setId(arr.getJSONObject(i).getJSONObject("mark").getInt("id"));
			studentMark.setMark(arr.getJSONObject(i).getJSONObject("mark").getDouble("userMark"));
			marks.add(studentMark);
		}
	}

	public static List<Department> extractDepartmnetPriorityList(String jsonQuery) {
		LOGGER.debug("extract priority list from user"+jsonQuery);
		
		List<Department> result=new LinkedList();
		
		JSONObject root = new JSONObject(jsonQuery);
		JSONArray arr = root.getJSONArray("order");
		for (int i = 0; i < arr.length(); i++) {
			Department d=new Department();
			d.setId(arr.getInt(i));
			result.add(d);
		}
		
		return result;
	}
}
