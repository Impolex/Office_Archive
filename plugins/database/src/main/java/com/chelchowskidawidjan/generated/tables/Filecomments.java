/*
 * This file is generated by jOOQ.
 */
package com.chelchowskidawidjan.generated.tables;


import com.chelchowskidawidjan.generated.Keys;
import com.chelchowskidawidjan.generated.Public;
import com.chelchowskidawidjan.generated.tables.Files.FilesPath;
import com.chelchowskidawidjan.generated.tables.Users.UsersPath;
import com.chelchowskidawidjan.generated.tables.records.FilecommentsRecord;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.InverseForeignKey;
import org.jooq.Name;
import org.jooq.Path;
import org.jooq.PlainSQL;
import org.jooq.QueryPart;
import org.jooq.Record;
import org.jooq.SQL;
import org.jooq.Schema;
import org.jooq.Select;
import org.jooq.Stringly;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class Filecomments extends TableImpl<FilecommentsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.filecomments</code>
     */
    public static final Filecomments FILECOMMENTS = new Filecomments();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<FilecommentsRecord> getRecordType() {
        return FilecommentsRecord.class;
    }

    /**
     * The column <code>public.filecomments.uuid</code>.
     */
    public final TableField<FilecommentsRecord, String[]> UUID = createField(DSL.name("uuid"), SQLDataType.VARCHAR.array().nullable(false), this, "");

    /**
     * The column <code>public.filecomments.author</code>.
     */
    public final TableField<FilecommentsRecord, String[]> AUTHOR = createField(DSL.name("author"), SQLDataType.VARCHAR.array().nullable(false), this, "");

    /**
     * The column <code>public.filecomments.file</code>.
     */
    public final TableField<FilecommentsRecord, String[]> FILE = createField(DSL.name("file"), SQLDataType.VARCHAR.array().nullable(false), this, "");

    /**
     * The column <code>public.filecomments.content</code>.
     */
    public final TableField<FilecommentsRecord, String[]> CONTENT = createField(DSL.name("content"), SQLDataType.VARCHAR.array().nullable(false), this, "");

    /**
     * The column <code>public.filecomments.creationdate</code>.
     */
    public final TableField<FilecommentsRecord, LocalDateTime> CREATIONDATE = createField(DSL.name("creationdate"), SQLDataType.LOCALDATETIME(6), this, "");

    /**
     * The column <code>public.filecomments.modificationdate</code>.
     */
    public final TableField<FilecommentsRecord, LocalDateTime> MODIFICATIONDATE = createField(DSL.name("modificationdate"), SQLDataType.LOCALDATETIME(6), this, "");

    private Filecomments(Name alias, Table<FilecommentsRecord> aliased) {
        this(alias, aliased, (Field<?>[]) null, null);
    }

    private Filecomments(Name alias, Table<FilecommentsRecord> aliased, Field<?>[] parameters, Condition where) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table(), where);
    }

    /**
     * Create an aliased <code>public.filecomments</code> table reference
     */
    public Filecomments(String alias) {
        this(DSL.name(alias), FILECOMMENTS);
    }

    /**
     * Create an aliased <code>public.filecomments</code> table reference
     */
    public Filecomments(Name alias) {
        this(alias, FILECOMMENTS);
    }

    /**
     * Create a <code>public.filecomments</code> table reference
     */
    public Filecomments() {
        this(DSL.name("filecomments"), null);
    }

    public <O extends Record> Filecomments(Table<O> path, ForeignKey<O, FilecommentsRecord> childPath, InverseForeignKey<O, FilecommentsRecord> parentPath) {
        super(path, childPath, parentPath, FILECOMMENTS);
    }

    /**
     * A subtype implementing {@link Path} for simplified path-based joins.
     */
    public static class FilecommentsPath extends Filecomments implements Path<FilecommentsRecord> {

        private static final long serialVersionUID = 1L;
        public <O extends Record> FilecommentsPath(Table<O> path, ForeignKey<O, FilecommentsRecord> childPath, InverseForeignKey<O, FilecommentsRecord> parentPath) {
            super(path, childPath, parentPath);
        }
        private FilecommentsPath(Name alias, Table<FilecommentsRecord> aliased) {
            super(alias, aliased);
        }

        @Override
        public FilecommentsPath as(String alias) {
            return new FilecommentsPath(DSL.name(alias), this);
        }

        @Override
        public FilecommentsPath as(Name alias) {
            return new FilecommentsPath(alias, this);
        }

        @Override
        public FilecommentsPath as(Table<?> alias) {
            return new FilecommentsPath(alias.getQualifiedName(), this);
        }
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : Public.PUBLIC;
    }

    @Override
    public UniqueKey<FilecommentsRecord> getPrimaryKey() {
        return Keys.FILECOMMENTS_PKEY;
    }

    @Override
    public List<ForeignKey<FilecommentsRecord, ?>> getReferences() {
        return Arrays.asList(Keys.FILECOMMENTS__FILECOMMENTS_AUTHOR_FKEY, Keys.FILECOMMENTS__FILECOMMENTS_FILE_FKEY);
    }

    private transient UsersPath _users;

    /**
     * Get the implicit join path to the <code>public.users</code> table.
     */
    public UsersPath users() {
        if (_users == null)
            _users = new UsersPath(this, Keys.FILECOMMENTS__FILECOMMENTS_AUTHOR_FKEY, null);

        return _users;
    }

    private transient FilesPath _files;

    /**
     * Get the implicit join path to the <code>public.files</code> table.
     */
    public FilesPath files() {
        if (_files == null)
            _files = new FilesPath(this, Keys.FILECOMMENTS__FILECOMMENTS_FILE_FKEY, null);

        return _files;
    }

    @Override
    public Filecomments as(String alias) {
        return new Filecomments(DSL.name(alias), this);
    }

    @Override
    public Filecomments as(Name alias) {
        return new Filecomments(alias, this);
    }

    @Override
    public Filecomments as(Table<?> alias) {
        return new Filecomments(alias.getQualifiedName(), this);
    }

    /**
     * Rename this table
     */
    @Override
    public Filecomments rename(String name) {
        return new Filecomments(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Filecomments rename(Name name) {
        return new Filecomments(name, null);
    }

    /**
     * Rename this table
     */
    @Override
    public Filecomments rename(Table<?> name) {
        return new Filecomments(name.getQualifiedName(), null);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Filecomments where(Condition condition) {
        return new Filecomments(getQualifiedName(), aliased() ? this : null, null, condition);
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Filecomments where(Collection<? extends Condition> conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Filecomments where(Condition... conditions) {
        return where(DSL.and(conditions));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Filecomments where(Field<Boolean> condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Filecomments where(SQL condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Filecomments where(@Stringly.SQL String condition) {
        return where(DSL.condition(condition));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Filecomments where(@Stringly.SQL String condition, Object... binds) {
        return where(DSL.condition(condition, binds));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    @PlainSQL
    public Filecomments where(@Stringly.SQL String condition, QueryPart... parts) {
        return where(DSL.condition(condition, parts));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Filecomments whereExists(Select<?> select) {
        return where(DSL.exists(select));
    }

    /**
     * Create an inline derived table from this table
     */
    @Override
    public Filecomments whereNotExists(Select<?> select) {
        return where(DSL.notExists(select));
    }
}
