package com.findme.service;

import com.findme.dao.UserDaoInterface;
import com.findme.exception.BadRequestException;
import com.findme.exception.NotFoundException;
import com.findme.models.User;
import com.findme.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService implements ServiceInterface<User> {

    UserDaoInterface userDao;

    @Autowired
    public UserService(UserDaoInterface userDao) {
        this.userDao = userDao;
    }

    public User get(Long userId) throws NotFoundException {
        User user = (User) userDao.findById(userId);
        if (user== null){
            throw new NotFoundException("User id : " + userId + " not found.");
        }
        return user;
    }

    @Override
    @Transactional
    public User save(User user ) {
        Date date = new Date();
        user.setDateRegistered(date);
        user.setDateLastActive(date);
        return (User) userDao.save(user);
    }

    @Override
    public List<User> getAll() {
        List<User> users = userDao.getAll();
        return users;
    }

    @Override
    public User update(User user) throws NotFoundException {

        User userInDB = get(user.getId());

        if (userInDB == null) {
            throw new NotFoundException("User with id: " + user.getId() + " not found in DB. ");
        }

        return (User) userDao.update(user);
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        userDao.delete(get(id));
    }



}