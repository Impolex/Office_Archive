package com.chelchowskidawidjan;

import java.util.List;
import java.util.UUID;

public interface CommentRepository {
    boolean persistCommentUpload(Comment comment);

    boolean persistCommentRemoval(UUID commentUUID);
}
