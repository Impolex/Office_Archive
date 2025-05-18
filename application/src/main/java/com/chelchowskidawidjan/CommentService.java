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

    Comment getCommentByUUID(UUID id) {
        return commentRepository.getCommentByUUID(id);
    }

    boolean postComment(User user, File file, String commentText) {
        //TODO Exception for invalid comment
        Comment comment = new Comment(user, commentText, file);
        return commentRepository.persistCommentUpload(comment);
    }

    boolean deleteComment(UUID commentUUID) {
        return commentRepository.persistCommentRemoval(commentUUID);
    }

    boolean editComment(UUID commentUUID, String commentText) {
        Comment comment = getCommentByUUID(commentUUID);
        comment.updateComment(commentText);
        return commentRepository.persistCommentEdit(comment);
    }

    List<Comment> getAllCommentsOfFile(UUID fileUUID) {
        return commentRepository.getCommentsOfFile(fileUUID);
    }

    List<Comment> getAllCommentsOfUser(UUID userUUID) {
        return commentRepository.getCommentsOfUser(userUUID);
    }

}
