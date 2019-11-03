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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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


    @GetMapping(path = "/user/{userId}")
    public String profile(Model model, @PathVariable String userId) throws NotFoundException, BadRequestException, InternalServerError {
        User user = null;
        try {
            user = (User) serviceInterface.get(Long.parseLong(userId));
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

    @GetMapping (path = "/users")
    public String allUsers (Model model){
        List<User> users = serviceInterface.getAll();
       model.addAttribute("users",users ) ;
       return "users";
    }

    @PostMapping (path = "/users/newUser")
    public String newUser(@ModelAttribute @Valid  User user, BindingResult result) {

        userValidator.validate(user,result);
        if(result.hasErrors()){
            return "/sign_up";
        }

        serviceInterface.save(user);
        return "redirect:/users";
    }

    @GetMapping(path = "/users/newUser")
    public String getNewUser (){
        return "sign_up";
    }



}
