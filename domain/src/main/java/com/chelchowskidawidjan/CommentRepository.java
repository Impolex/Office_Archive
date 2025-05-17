package com.chelchowskidawidjan;

import java.util.Optional;

public interface CommentRepository {
    boolean persistCommentUpload(String[] commentUUID, String[] userUUID, String[] fileUUID, String[] content);

    boolean persistCommentRemoval(String[] commentUUID);

    Optional<Comment> getCommentByUUID(String commentUUID);

    Iterable<Comment> getCommentsOfFile(String fileUUID);

    Iterable<Comment> getCommentsOfUser(String userUUID);
}
