ALTER TABLE files drop constraint files_uploader_fkey;
ALTER TABLE fileComments drop constraint fileComments_author_fkey;
ALTER TABLE fileComments drop constraint fileComments_file_fkey;
ALTER TABLE filePermissions drop constraint filePermissions_user_fkey;
ALTER TABLE filePermissions drop constraint filePermissions_file_fkey;

ALTER TABLE users ALTER COLUMN uuid TYPE varchar[36];
ALTER TABLE users ALTER COLUMN passwordHash TYPE varchar[256];
ALTER TABLE users ALTER COLUMN passwordSalt TYPE varchar[12];

ALTER TABLE files ALTER COLUMN uuid TYPE varchar[36];
ALTER TABLE files ALTER COLUMN uploader TYPE varchar[36];

ALTER TABLE fileComments ALTER COLUMN uuid TYPE varchar[36];
ALTER TABLE fileComments ALTER COLUMN author TYPE varchar[36];
ALTER TABLE fileComments ALTER COLUMN file TYPE varchar[36];
ALTER TABLE fileComments ALTER COLUMN content TYPE varchar[256];

ALTER TABLE filePermissions ALTER COLUMN "user" TYPE varchar[36];
ALTER TABLE filePermissions ALTER COLUMN file TYPE varchar[36];

ALTER TABLE files ADD CONSTRAINT files_uploader_fkey FOREIGN KEY (uploader) REFERENCES users(uuid);
ALTER TABLE fileComments ADD CONSTRAINT fileComments_author_fkey FOREIGN KEY (author) REFERENCES users(uuid);
ALTER TABLE fileComments ADD CONSTRAINT fileComments_file_fkey FOREIGN KEY (file) REFERENCES files(uuid);
ALTER TABLE filePermissions ADD CONSTRAINT filePermissions_user_fkey FOREIGN KEY ("user") REFERENCES users(uuid);
ALTER TABLE filePermissions ADD CONSTRAINT filePermissions_file_fkey FOREIGN KEY (file) REFERENCES files(uuid);