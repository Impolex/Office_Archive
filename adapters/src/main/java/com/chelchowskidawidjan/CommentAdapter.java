package com.chelchowskidawidjan;

import java.time.LocalDateTime;
import java.util.UUID;

public class CommentAdapter {
    public CommentAdapter(){

    }

    public Comment pluginToDomain(AbstractPluginComment pluginComment){
        UUID authorUUID = UUID.fromString(pluginComment.getAuthorUUID());
        UUID commentUUID = UUID.fromString(pluginComment.getCommentUUID());
        String commentContent = pluginComment.getContent();
        LocalDateTime creationDate = pluginComment.getCreationDate();
        LocalDateTime modificationDate = pluginComment.getModificationDate();
        UUID fileUUID = UUID.fromString(pluginComment.getFileUUID());

        return new Comment(commentUUID, authorUUID, commentContent, fileUUID, creationDate, modificationDate);
    }

    public void domainToPlugin(Comment domainComment, AbstractPluginComment pluginComment){
        pluginComment.setCommentUUID(domainComment.getUuid().toString());
        pluginComment.setAuthor(domainComment.getAuthorUUID().toString());
        pluginComment.setFile(domainComment.getFileUUID().toString());
        pluginComment.setContent(domainComment.getComment());
        pluginComment.setCreationDate(domainComment.getCreationDate());
        pluginComment.setModificationDate(LocalDateTime.now());
    }


}
