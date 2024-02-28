CREATE SEQUENCE address_id_seq
    AS INTEGER
    MAXVALUE 200000000;

ALTER SEQUENCE address_id_seq OWNER TO postgres;

CREATE SEQUENCE author_id_seq
    AS INTEGER
    MAXVALUE 200000000;

ALTER SEQUENCE author_id_seq OWNER TO postgres;

CREATE SEQUENCE book_id_seq
    AS INTEGER
    MAXVALUE 200000000;

ALTER SEQUENCE book_id_seq OWNER TO postgres;

CREATE SEQUENCE store_id_seq
    AS INTEGER
    MAXVALUE 200000000;

ALTER SEQUENCE store_id_seq OWNER TO postgres;

CREATE TABLE author
(
    author_id INTEGER DEFAULT NEXTVAL('author_id_seq'::regclass) NOT NULL
        CONSTRAINT "Author_pk"
            PRIMARY KEY,
    name      VARCHAR(255)                                       NOT NULL
);

ALTER TABLE author OWNER TO postgres;

CREATE TABLE book
(
    book_id INTEGER DEFAULT NEXTVAL('book_id_seq'::regclass) NOT NULL
        CONSTRAINT "Book_pk"
            PRIMARY KEY,
    title   VARCHAR(255)                                     NOT NULL,
    author  INTEGER                                          NOT NULL
        CONSTRAINT author
            REFERENCES author,
    price   INTEGER                                          NOT NULL
);

ALTER TABLE book OWNER TO postgres;

CREATE TABLE store
(
    store_id INTEGER DEFAULT NEXTVAL('store_id_seq'::regclass) NOT NULL
        CONSTRAINT "Store_pk"
            PRIMARY KEY,
    address  VARCHAR(255)                                      NOT NULL
);

ALTER TABLE store OWNER TO postgres;

CREATE TABLE store_has_books
(
    store_id INTEGER NOT NULL
        CONSTRAINT "Store_has_books___fk1"
            REFERENCES store
            ON DELETE CASCADE,
    book_id  INTEGER NOT NULL
        CONSTRAINT "Store_has_books___fk2"
            REFERENCES book
);

ALTER TABLE store_has_books OWNER TO postgres;

