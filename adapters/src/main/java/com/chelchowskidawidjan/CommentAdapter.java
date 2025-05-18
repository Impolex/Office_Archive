package com.chelchowskidawidjan;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

    public <T extends AbstractPluginComment> T domainToPlugin(Comment domainComment, Class<T> pluginCommentClass){
        try {
            String commentUUID = domainComment.getUuid().toString();
            String authorUUID = domainComment.getAuthorUUID().toString();
            String fileUUID = domainComment.getFileUUID().toString();
            String content = domainComment.getComment();
            LocalDateTime creationDate = domainComment.getCreationDate();
            LocalDateTime modificationDate = domainComment.getModifiedDate();

            Constructor<T> constructor = pluginCommentClass.getConstructor(String.class, String.class, String.class, String.class,
                    LocalDateTime.class, LocalDateTime.class);

            return constructor.newInstance(commentUUID, authorUUID, fileUUID, content, creationDate, modificationDate);
        }
        catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
