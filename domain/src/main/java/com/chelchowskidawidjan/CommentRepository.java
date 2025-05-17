package com.chelchowskidawidjan;

import java.util.List;
import java.util.UUID;

public interface CommentRepository {
    boolean persistCommentUpload(Comment comment);

    boolean persistCommentEdit(Comment comment);

    boolean persistCommentRemoval(UUID commentUUID);

    Comment getCommentByUUID(UUID commentUUID);

    List<Comment> getCommentsOfFile(UUID fileUUID);

    List<Comment> getCommentsOfUser(UUID userUUID);
}
