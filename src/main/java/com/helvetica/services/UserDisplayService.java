package com.helvetica.services;

import com.helvetica.model.dao.UserDao;
import com.helvetica.model.dao.imp.JDBCDaoFactory;
import com.helvetica.model.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class UserDisplayService {

    private UserDao userDao;

    public UserDisplayService() {
        JDBCDaoFactory jdbcDaoFactory = new JDBCDaoFactory();
        this.userDao = jdbcDaoFactory.createUserDao();
    }

    public void displayUsers(HttpServletRequest request){
        request.setAttribute("users", userDao.findAll());
        request.getRequestDispatcher("/WEB-INF/user_display.jsp");
    }

    public User getByUsernameAndPassword(String username, String password){
        return userDao.findByUsernameAndPassword(username, password);
    }

    public void deleteUser(int id) {
        userDao.delete(id);
    }

    public void editUser(HttpServletRequest request){

        int id = Integer.parseInt(request.getParameter("id"));

        User userToEdit = userDao.findById(id);
        request.setAttribute("user", userToEdit);

        Optional<String> firstName = Optional.ofNullable(request.getParameter("firstNameEdit"));
        Optional<String> lastName = Optional.ofNullable(request.getParameter("lastNameEdit"));
        Optional<String> username = Optional.ofNullable(request.getParameter("loginEdit"));
        Optional<String> password = Optional.ofNullable(request.getParameter("passwordEdit"));

        userToEdit.setFirstName(firstName.orElse(userToEdit.getFirstName()));
        userToEdit.setLastName(lastName.orElse(userToEdit.getLastName()));
        userToEdit.setUsername(username.orElse(userToEdit.getUsername()));
        userToEdit.setPassword(password.orElse(userToEdit.getPassword()));

        userDao.update(userToEdit);

    }

}
