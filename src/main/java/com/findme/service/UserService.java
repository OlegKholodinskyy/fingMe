package com.findme.service;

import com.findme.dao.DaoInterface;
import com.findme.exception.BadRequestException;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class UserService {

    DaoInterface userDao;

    @Autowired
    public UserService(DaoInterface userDao) {
        this.userDao = userDao;
    }
    public User get(Long userId) {
        User user = (User) userDao.findById(userId);
        return user;
    }

    @Transactional
    public User saveUser(User user) throws BadRequestException {
        // перевірити чи вже є такий
        checkIfExist(user);
        Date date = new Date();
        user.setDateRegistered(date);
        return (User) userDao.save(user);
    }

    private void checkIfExist(User user) throws BadRequestException {
        if(userDao.checkIfPresent(user)){
            throw new BadRequestException("User is already present in DB.");
        }
    }
}
