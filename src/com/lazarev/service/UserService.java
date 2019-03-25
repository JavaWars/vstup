package com.lazarev.service;

import com.lazarev.db.entity.Role;
import com.lazarev.db.dao.CityDAO;
import com.lazarev.db.dao.MarkDAO;
import com.lazarev.db.dao.RoleDAO;
import com.lazarev.db.dao.UserDAO;
import com.lazarev.db.entity.User;
import com.lazarev.db.entity.extra.UserWithMarks;
import com.lazarev.util.EmailService;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class UserService {
	private static Logger logger = Logger.getLogger(UserService.class);

	public User insertUser(String email, String cityArea, String fio, String password, String city, String diplom,
			String phone, String birthday) {

		User newUser = new User();
		newUser.setEmail(email);
		newUser.setCityArea(cityArea);
		newUser.setFio(fio);
		newUser.setPassword(password);
		Role role = Role.USER;
		newUser.setRoleId(new RoleDAO().getRoleIdByRoleName(role.getName()));
		newUser.setCityId(new CityDAO().get(city));
		newUser.setDiplom(diplom);
		newUser.setPhone(phone);

		logger.trace("birthday" + birthday);

		newUser.setBirthday(birthday);
		logger.trace("newUser" + newUser);

		UserDAO userDb = new UserDAO();
		userDb.insert(newUser);

		EmailService.getInstance().sendMessage(newUser.getEmail(), "Welcome in vstup system",
				"welcome my dear user description");

		return newUser;
	}

	public boolean existUserEmail(String email) {
		UserDAO userDao = new UserDAO();
		return userDao.exist(email);
	}

	public List<UserWithMarks> getNotBunnedByExample(String fio, String email, String diplom, int page) {
		int count_per_page = 10;

		if (page <= 0)
			page = 1;

		int start = (page - 1) * count_per_page;
		int fin = page * count_per_page;

		List <User> listOfUser=new UserDAO().getAllNotBanned(fio, email, diplom, start, fin);
		List<UserWithMarks> result=new LinkedList();
		
		for (User u:listOfUser) {
			UserWithMarks uwm=new UserWithMarks(u);
			uwm.setStudentMarks(new MarkDAO().getAllMarksForUserWithId(u.getId()));
			result.add(uwm);
		}
		
		return result;
	}

	public List<User> getBannedByExample(String fio, String email, String diplom, int page) {
		int count_per_page = 10;

		if (page <= 0)
			page = 1;

		int start = (page - 1) * count_per_page;
		int fin = page * count_per_page;

		return new UserDAO().getAllBanned(fio, email, diplom, start, fin);
	}
}
