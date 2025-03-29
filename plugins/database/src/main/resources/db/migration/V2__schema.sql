CREATE TYPE permissions as ENUM
    (
        'NO_PERMISSIONS',
        'READ',
        'WRITE'
    );

CREATE TYPE fileType as ENUM
    (
        'DOC',
        'DOCX',
        'RTF',
        'PDF',
        'WPD',
        'JPEG',
        'PNG',
        'GIF',
        'HEIF',
        'AAC',
        'MP3',
        'WAV',
        'AMV',
        'MPEG',
        'FLV',
        'AVI',
        'C',
        'JAVA',
        'PY',
        'JS',
        'TS',
        'HTML',
        'ASP',
        'CSS',
        'XPS',
        'ISO',
        'RAR',
        'TAR',
        'GZ',
        'SEVENZ',
        'ZIP',
        'TXT',
        'CSV'
    );

CREATE TABLE users
(
    uuid char[36] PRIMARY KEY NOT NULL,
    objectName char[20] NOT NULL,
    passwordHash char[256] NOT NULL,
    passwordSalt char[12] NOT NULL,
    isAdmin bool NOT NULL
);

CREATE TABLE files
(
    uuid char[36] PRIMARY KEY NOT NULL,
    objectName char[30] NOT NULL,
    fileType fileType NOT NULL,
    uploader char[100] REFERENCES users(uuid) NOT NULL,
    creationDate TIMESTAMP NOT NULL,
    modificationDate TIMESTAMP NOT NULL
);

CREATE TABLE fileComments
(
    uuid char[36] PRIMARY KEY NOT NULL,
    author char[100] REFERENCES users(uuid) NOT NULL,
    file char[100] REFERENCES files(uuid) NOT NULL,
    content char[100] NOT NULL
);

CREATE TABLE filePermissions
(
    "user" char[100] REFERENCES users(uuid) NOT NULL,
    file char[100] REFERENCES files(uuid) NOT NULL,
    permission permissions NOT NULL
);
