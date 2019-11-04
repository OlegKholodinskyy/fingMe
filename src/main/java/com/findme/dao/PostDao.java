package com.findme.dao;

import com.findme.models.Post;
import javafx.geometry.Pos;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class PostDao implements PostDaoInterface<Post> {

    private String getAllString = "SELECT * FROM POST";

    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Post save(Post post) {
        entityManager.persist(post);
        return post;
    }

    @Override
    public Post findById(Long id) {
        return entityManager.find(Post.class, id);
    }

    @Override
    public Post update(Post post) {
        return entityManager.merge(post);
    }

    @Override
    public void delete(Post post) throws DataIntegrityViolationException {
        entityManager.remove(post);
    }


    @Override
    public List<Post> getAll() {
        List<Post> posts = entityManager.createNativeQuery(getAllString, Post.class).getResultList();
        return posts;
    }
}
