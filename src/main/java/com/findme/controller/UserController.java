package com.findme.controller;

import com.findme.exception.BadRequestException;
import com.findme.exception.NotFoundException;
import com.findme.exception.ObjectExistException;
import com.findme.utils.GeneralMapper;
import com.findme.models.User;
import com.findme.service.ServiceInterface;
import com.findme.utils.UserValidator;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
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
            model.addAttribute("user", user);
            return "profile";
         } catch (ObjectExistException | NotFoundException e) {
            model.addAttribute("codeError", "not found - 404");
            model.addAttribute("methodName", new Object() {}.getClass().getEnclosingMethod().getName());
            return "errorPersistence";
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
    public ResponseEntity<String> update(HttpServletRequest req, BindingResult result) {
        try {
            User user = generalMapper.mappingObject(req, User.class);
            //  есть ли уже такой пользователь с тем же номером телефона
            userValidator.validate(user, result);

            if (result.hasErrors()) {
                StringBuilder sb = new StringBuilder("");
                List<FieldError> errors = result.getFieldErrors();
                for (FieldError error : errors) {
                    sb.append(error.getObjectName() + " - " + error.getDefaultMessage() + "\n");
                }
                return new ResponseEntity<>(sb.toString(), HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<String>("User was updated succesfully. " + serviceInterface.update(user).toString(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<String>("IOException : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //500
        } catch (HibernateException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  //500
        } catch (ObjectExistException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /*
           http://localhost:8080//users/deleteUser?idUser=1005
    */
    @DeleteMapping(path = "/users/deleteUser", produces = "text/plain")
    public ResponseEntity<String> delete(HttpServletRequest req) throws BadRequestException {
        String idUser = req.getParameter("idUser");
        try {
            serviceInterface.delete(Long.parseLong(idUser));
            return new ResponseEntity<String>("User id: " + idUser + " was deleted.", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>("IOException " + e.getMessage(), HttpStatus.BAD_REQUEST);
         } catch (HibernateException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  //500
        } catch (ObjectExistException | NotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}


