package com.acme.webserviceserentcar.client.service;

import com.acme.webserviceserentcar.client.domain.model.entity.Comment;
import com.acme.webserviceserentcar.client.domain.persistence.ClientRepository;
import com.acme.webserviceserentcar.client.domain.persistence.CommentRepository;
import com.acme.webserviceserentcar.client.domain.service.CommentService;
import com.acme.webserviceserentcar.shared.exception.ResourceNotFoundException;
import com.acme.webserviceserentcar.shared.exception.ResourceValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {
    private static final String ENTITY = "Comment";
    private final ClientRepository clientRepository;
    private final CommentRepository commentRepository;
    private final Validator validator;

    public CommentServiceImpl(ClientRepository clientRepository, CommentRepository commentRepository, Validator validator) {
        this.clientRepository = clientRepository;
        this.commentRepository = commentRepository;
        this.validator = validator;
    }


    @Override
    public List<Comment> getAll() {
        return commentRepository.findAll();
    }

    @Override
    public Page<Comment> getAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public Comment getById(Long clientId) {
        return commentRepository.findById(clientId).orElseThrow(() -> new ResourceNotFoundException(ENTITY, clientId));
    }

    @Override
    public Comment create(Comment request) {
        Set<ConstraintViolation<Comment>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY,violations );

        return commentRepository.save(request);
    }

    @Override
    public Comment update(Long clientId, Comment request) {
        Set<ConstraintViolation<Comment>> violations = validator.validate(request);

        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return commentRepository.findById(clientId).map(comment ->
                commentRepository.save(comment.withDate(request.getDate())
                        .withStars(request.getStars())
                        .withDate(request.getDate()))).orElseThrow(() -> new ResourceNotFoundException(ENTITY, clientId));
    }

    @Override
    public ResponseEntity<?> delete(Long clientId) {
        return commentRepository.findById(clientId).map(comment -> {
            commentRepository.delete(comment);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, clientId));
    }
}
