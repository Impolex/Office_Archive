/*
 * This file is generated by jOOQ.
 */
package com.chelchowskidawidjan.generated.tables.records;


import com.chelchowskidawidjan.generated.enums.Filetype;
import com.chelchowskidawidjan.generated.tables.Files;

import java.time.LocalDateTime;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class FilesRecord extends UpdatableRecordImpl<FilesRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.files.uuid</code>.
     */
    public void setUuid(String[] value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.files.uuid</code>.
     */
    public String[] getUuid() {
        return (String[]) get(0);
    }

    /**
     * Setter for <code>public.files.objectname</code>.
     */
    public void setObjectname(String[] value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.files.objectname</code>.
     */
    public String[] getObjectname() {
        return (String[]) get(1);
    }

    /**
     * Setter for <code>public.files.uploader</code>.
     */
    public void setUploader(String[] value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.files.uploader</code>.
     */
    public String[] getUploader() {
        return (String[]) get(2);
    }

    /**
     * Setter for <code>public.files.creationdate</code>.
     */
    public void setCreationdate(LocalDateTime value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.files.creationdate</code>.
     */
    public LocalDateTime getCreationdate() {
        return (LocalDateTime) get(3);
    }

    /**
     * Setter for <code>public.files.modificationdate</code>.
     */
    public void setModificationdate(LocalDateTime value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.files.modificationdate</code>.
     */
    public LocalDateTime getModificationdate() {
        return (LocalDateTime) get(4);
    }

    /**
     * Setter for <code>public.files.filetype</code>.
     */
    public void setFiletype(Filetype value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.files.filetype</code>.
     */
    public Filetype getFiletype() {
        return (Filetype) get(5);
    }

    /**
     * Setter for <code>public.files.content</code>.
     */
    public void setContent(byte[] value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.files.content</code>.
     */
    public byte[] getContent() {
        return (byte[]) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<String[]> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached FilesRecord
     */
    public FilesRecord() {
        super(Files.FILES);
    }

    /**
     * Create a detached, initialised FilesRecord
     */
    public FilesRecord(String[] uuid, String[] objectname, String[] uploader, LocalDateTime creationdate, LocalDateTime modificationdate, Filetype filetype, byte[] content) {
        super(Files.FILES);

        setUuid(uuid);
        setObjectname(objectname);
        setUploader(uploader);
        setCreationdate(creationdate);
        setModificationdate(modificationdate);
        setFiletype(filetype);
        setContent(content);
        resetTouchedOnNotNull();
    }
}
