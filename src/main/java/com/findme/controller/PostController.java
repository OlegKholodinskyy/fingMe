package com.findme.controller;


import com.findme.exception.NotFoundException;
import com.findme.exception.ObjectExistException;
import com.findme.models.Post;
import com.findme.service.ServiceInterface;
import com.findme.utils.GeneralMapper;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class PostController {

    private GeneralMapper generalMapper;
    private ServiceInterface serviceInterface;

    @Autowired
    public PostController(GeneralMapper generalMapper, ServiceInterface serviceInterface) {
        this.generalMapper = generalMapper;
        this.serviceInterface = serviceInterface;
    }


    @RequestMapping(method = RequestMethod.POST, value = "/posts/newPost", produces = "text/plain")
    public ResponseEntity<String> save(HttpServletRequest request) {
        try {
            Post post = generalMapper.mappingObject(request, Post.class);
            return new ResponseEntity<String>("Post saved: " + serviceInterface.save(post).toString(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<String>("IOException : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);//500
        } catch (HibernateException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  //500
        }
    }


    @RequestMapping(method = RequestMethod.DELETE, value = "/posts/deletePost", produces = "text/plain")
    public ResponseEntity<String> delPlane(HttpServletRequest req) {
        String idPost = req.getParameter("idPost");
        try {
            serviceInterface.delete(Long.parseLong(idPost));
            return new ResponseEntity<String>("Post id: " + idPost + " was deleted.", HttpStatus.OK);
        } catch (IllegalArgumentException | NotFoundException e) {
            return new ResponseEntity<String>("IOException " + e.getMessage(), HttpStatus.BAD_REQUEST);
         } catch (ObjectExistException e) {
            return new ResponseEntity<String>("Post id: " + idPost + " was not deleted. Post with id: " + idPost + " not found in DB.\n" + e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (HibernateException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  //500
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getPost", produces = "text/plain")
    public ResponseEntity<String> getPlane(HttpServletRequest req) {
        String idPost = req.getParameter("idPost");
        Post post = null;
        try {
            post = (Post) serviceInterface.get(Long.parseLong(idPost));
            return new ResponseEntity<String>("Post id: " + idPost + " found in DB. " + post.toString(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>("IOException " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ObjectExistException | NotFoundException e) {
            return new ResponseEntity<String>("Post id: " + idPost + " persist exception. ", HttpStatus.BAD_REQUEST);
        } catch (HibernateException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  //500
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/posts/updatePost", produces = "text/plain")
    public ResponseEntity<String> updatePlane(HttpServletRequest request) {
        try {
            Post post = generalMapper.mappingObject(request, Post.class);
            return new ResponseEntity<String>("Post was updated succesfully. " + serviceInterface.update(post).toString(), HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<String>("IOException : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); //500
        } catch (ObjectExistException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (HibernateException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);  //500
        } catch (NotFoundException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
