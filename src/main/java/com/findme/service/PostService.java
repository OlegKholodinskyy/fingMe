package com.findme.service;

import com.findme.dao.PostDaoInterface;
import com.findme.exception.NotFoundException;
import com.findme.exception.ObjectExistException;
import com.findme.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;


public class PostService implements ServiceInterface<Post> {

    PostDaoInterface postDao;

    @Autowired
    public PostService(PostDaoInterface postDao) {
        this.postDao = postDao;
    }

    @Override
    public Post save(Post post) {
        return (Post) postDao.save(post);
    }

    @Override
    public Post get(Long id) throws ObjectExistException {
        Post post = (Post) postDao.findById(id);
        if (post == null) {
            throw new ObjectExistException("Plane with id: " + id + " not found in DB. ");
        }
        return post;
    }

    @Override
    public List<Post> getAll() {
        return null;
    }

    @Override
    public Post update(Post post) throws NotFoundException, ObjectExistException {
        // перевірка чи є такий post з заданним ІД
        Post postInDB = get(post.getId());
        if (postInDB == null) {
            throw new ObjectExistException("Post with id: " + post.getId() + " not found in DB. ");
        }
        // саме поновлення
        return (Post) postDao.update(post);
    }

    @Override
    public void delete(Long id) throws ObjectExistException {
        Post post = get(id);
        if (post == null) {
            throw new ObjectExistException("Post with id: " + id + "not found ib DB");
        }

        postDao.delete(post);

    }
}
