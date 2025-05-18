package com.chelchowskidawidjan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }


    boolean postComment(UUID userUUID, UUID fileUUID, String commentText) {
        //TODO Exception for invalid comment
        Comment comment = new Comment(userUUID, commentText, fileUUID);
        return commentRepository.persistCommentUpload(comment);
    }

    boolean deleteComment(UUID commentUUID) {
        return commentRepository.persistCommentRemoval(commentUUID);
    }
}
