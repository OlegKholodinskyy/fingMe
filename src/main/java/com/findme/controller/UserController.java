package com.findme.controller;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.helpers.GeneralMapper;
import com.findme.models.User;
import com.findme.service.UserService;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class UserController {

    private GeneralMapper generalMapper;
    private UserService userSevice;

    @Autowired
    public UserController(GeneralMapper generalMapper, UserService userService) {
        this.generalMapper = generalMapper;
        this.userSevice = userService;
    }


    @RequestMapping(path = "/user/{userId}", method = RequestMethod.GET)
    public String profile(Model model, @PathVariable String userId) throws NotFoundException, BadRequestException, InternalServerError {
        User user = null;
        try {
            user = userSevice.get(Long.parseLong(userId));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("unable to process request");
        } catch (HibernateException e){
            throw new InternalServerError("Hibernate exception");
        }
        if (user == null)
            throw new NotFoundException("User not found  " + userId);

        model.addAttribute("user", user);
        return "profile";
    }

    /*
   {"firstName":"Test",
   "lastName":"Temp",
   "phone":"0953931165"}
    */
    @RequestMapping(path = "/saveUser", method = RequestMethod.POST)
    public ResponseEntity<String> save(HttpServletRequest request) {
        try {
            User user = generalMapper.mappingObject(request, User.class);
            return new ResponseEntity<String>("User saved: " + userSevice.saveUser(user).toString(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<String>("IOException : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);//500
        } catch (BadRequestException e) {
            return new ResponseEntity<String>("BadRequestException : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (HibernateException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  //500
        }
    }

}
