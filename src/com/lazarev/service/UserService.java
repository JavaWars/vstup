package com.lazarev.service;

import com.lazarev.db.Role;
import com.lazarev.db.dao.CityDAO;
import com.lazarev.db.dao.RoleDAO;
import com.lazarev.db.dao.UserDAO;
import com.lazarev.db.entity.User;
import com.lazarev.util.EmailService;
import org.apache.log4j.Logger;

public class UserService {
    private static Logger logger = Logger.getLogger(UserService.class);

    public User insertUser(String email, String cityArea, String fio, String password, String city, String diplom, String phone){

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
        logger.trace("newUser" + newUser);

        UserDAO userDb = new UserDAO();
        userDb.insert(newUser);

        EmailService.getInstance().sendMessage(newUser.getEmail(), "Welcome in vstup system",
                "welcome my dear user description");

        return newUser;
    }


    public boolean existUserEmail(String email){
        UserDAO userDao = new UserDAO();
        return userDao.exist(email);
    }
}
