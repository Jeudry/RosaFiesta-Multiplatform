CREATE TABLE articles
(
    id   BYTEA        NOT NULL,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(10)  NOT NULL,
    CONSTRAINT pk_articles PRIMARY KEY (id)
);