package com.findme.controller;

import com.findme.exception.BadRequestException;
import com.findme.exception.InternalServerError;
import com.findme.exception.NotFoundException;
import com.findme.utils.GeneralMapper;
import com.findme.models.User;
import com.findme.service.ServiceInterface;
import com.findme.utils.UserValidator;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    private GeneralMapper generalMapper;
    private ServiceInterface serviceInterface;
    private UserValidator userValidator;

    @Autowired
    public UserController(GeneralMapper generalMapper, ServiceInterface serviceInterface, UserValidator userValidator) {
        this.generalMapper = generalMapper;
        this.serviceInterface = serviceInterface;
        this.userValidator = userValidator;
    }

//catch (InternalServerError e)  - не позволяет добавить . Пишет никогда не будет такого.

    @GetMapping(path = "/user/{userId}")
    public String profile(Model model, @PathVariable String userId) {
        User user = null;
        try {
            user = (User) serviceInterface.get(Long.parseLong(userId));

            if (user == null) {
                model.addAttribute("userId", userId);
                return "errorNotFound";
            }
            model.addAttribute("user", user);
            return "profile";
        } catch (NumberFormatException e) {
            model.addAttribute("userId", userId);
            return "errorNumberFormat";
        }
    }

    @GetMapping(path = "/users")
    public String allUsers(Model model) {
        List<User> users = serviceInterface.getAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping(path = "/users/newUser")
    public ResponseEntity<String> newUser(@ModelAttribute @Valid User user, BindingResult result) {
        try {
            userValidator.validate(user, result);
            if (result.hasErrors()) {
                StringBuilder sb = new StringBuilder("");
                List<FieldError> errors = result.getFieldErrors();
                for (FieldError error : errors) {
                    sb.append(error.getObjectName() + " - " + error.getDefaultMessage() + "\n");
                }
                return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<String>("User saved: " + serviceInterface.save(user), HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<String>("IOException : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/users/newUser")
    public String getNewUser() {
        return "sign_up";
    }

    @PutMapping(path = "/users/updateUser", produces = "text/plain")
    public ResponseEntity<String> update(HttpServletRequest req){
        try {
            User user = generalMapper.mappingObject(req, User.class);
            return new ResponseEntity<String>("User was updated succesfully. " + serviceInterface.update(user).toString().toString(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<String>("IOException : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //500
         } catch (HibernateException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  //500
         }
    }

    @DeleteMapping(path = "/users/deleteUser", produces = "text/plain")
    public ResponseEntity<String>  delete(HttpServletRequest req)throws BadRequestException{
        //todo
       return  null;
    }
}


