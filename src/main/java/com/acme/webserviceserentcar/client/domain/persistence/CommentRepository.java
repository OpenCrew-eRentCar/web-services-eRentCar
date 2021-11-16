package com.acme.webserviceserentcar.client.domain.persistence;

import com.acme.webserviceserentcar.client.domain.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
