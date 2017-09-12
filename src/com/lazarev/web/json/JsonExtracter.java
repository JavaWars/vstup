package com.lazarev.web.json;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.lazarev.db.entity.Department;
import com.lazarev.db.entity.DepartmentSubject;

public class JsonExtracter {

	private static final Logger LOGGER = Logger.getLogger(JsonExtracter.class);

	public static void extractDepartmnet(String str, Department departmnet, List<DepartmentSubject> marks) {
		LOGGER.debug("extract new department"+str);
		JSONObject root = new JSONObject(str);
		departmnet.setName(root.getString("departmentName"));
		departmnet.setPlaceDov(root.getInt("placesGov"));
		departmnet.setTotaPlace(root.getInt("placesTotal"));

		JSONArray arr = root.getJSONArray("json");
		for (int i = 0; i < arr.length(); i++) {
			DepartmentSubject mark = new DepartmentSubject();
			mark.setName(arr.getJSONObject(i).getJSONObject("mark").getString("name"));
			mark.setScale(arr.getJSONObject(i).getJSONObject("mark").getDouble("scale"));
			// System.out.println(arr.getJSONObject(i).getJSONObject("mark").getInt("id"));
			marks.add(mark);
		}
	}

	public static void extractExistingDepartmnet(String str, Department departmnet, List<DepartmentSubject> marks) {
		LOGGER.debug("extract existing department"+str);
		extractDepartmnet(str,departmnet,marks);
		departmnet.setId(new JSONObject(str).getInt("id"));
	}
}
