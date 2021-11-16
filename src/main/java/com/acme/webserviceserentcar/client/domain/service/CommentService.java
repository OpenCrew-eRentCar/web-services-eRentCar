package com.acme.webserviceserentcar.client.domain.service;

import com.acme.webserviceserentcar.client.domain.model.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CommentService {
    List<Comment> getAll();
    Page<Comment> getAll(Pageable pageable);
    Comment getById(Long clientId);
    Comment create(Comment request);
    Comment update(Long clientId, Comment request);
    ResponseEntity<?> delete(Long clientId);
}
