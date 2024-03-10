DROP TABLE IF EXISTS book CASCADE;
DROP TABLE IF EXISTS author CASCADE;
DROP TABLE IF EXISTS store CASCADE;
DROP TABLE IF EXISTS store_has_books CASCADE;

create sequence author_id_seq
    as integer
    maxvalue 200000000;

create sequence book_id_seq
    as integer
    maxvalue 200000000;

create sequence store_id_seq
    as integer
    maxvalue 200000000;

create table author
(
    author_id integer default nextval('author_id_seq'::regclass) not null
        constraint "Author_pk"
            primary key,
    name      varchar(255)                                       not null
        constraint unique_author_name
            unique
);

create table book
(
    book_id integer default nextval('book_id_seq'::regclass) not null
        constraint "Book_pk"
            primary key,
    title   varchar(255)
        constraint unique_book_title
            unique,
    author  integer                                          not null
        constraint author
            references author
            on delete cascade,
    price   integer                                          not null
);

create table store
(
    store_id integer default nextval('store_id_seq'::regclass) not null
        constraint "Store_pk"
            primary key,
    city     varchar(255)                                      not null
        constraint unique_store_address
            unique
);

create table store_has_books
(
    store_id integer not null
        constraint "Store_has_books___fk1"
            references store
            on delete cascade,
    book_id  integer not null
        constraint "Store_has_books___fk2"
            references book
            on delete cascade
);

create procedure add_book(IN _title text, IN _author_name text, IN _price integer, IN _stores_cities text[])
    language plpgsql
as
$$
DECLARE
    _book_id INT;
    _author_id INT;
    _store_id INT;
    _city TEXT;
BEGIN
    -- Добавляем автора в таблицу 'author', если его там еще нет
    INSERT INTO author (name)
    VALUES (_author_name)
    ON CONFLICT (name) DO NOTHING;

    -- Получаем ID автора
    SELECT author_id INTO _author_id FROM author WHERE name = _author_name;

    -- Добавляем книгу в таблицу 'book' если ее там еще нет
    INSERT INTO book (title, author, price)
    VALUES (_title, _author_id, _price)
    ON CONFLICT (title) DO NOTHING;

    -- Получаем ID книги
    SELECT book_id INTO _book_id FROM book WHERE author = _author_id;

    -- Для каждого адреса магазина в списке
    FOREACH _city IN ARRAY _stores_cities
        LOOP
            -- Добавляем магазин в таблицу 'store', если его там еще нет
            INSERT INTO store (city)
            VALUES (_city)
            ON CONFLICT (city) DO NOTHING;

            -- Получаем ID магазина
            SELECT store_id INTO _store_id FROM store WHERE city = _city;

            -- Получаем ID книги
            SELECT book_id INTO _book_id FROM book WHERE title = _title;

            -- Добавляем связь между магазином и книгой в таблицу 'store_has_books'
            INSERT INTO store_has_books (store_id, book_id)
            VALUES (_store_id, _book_id);
        END LOOP;
END;
$$;

INSERT INTO author (name) VALUES ('Иван Иванов');
INSERT INTO book (title, price, author) VALUES ('Тестовая книга 1', 100, currval('author_id_seq'));
INSERT INTO store (city) VALUES ('Гомель');
INSERT INTO store_has_books (store_id, book_id) VALUES (currval('store_id_seq'), currval('book_id_seq'));

INSERT INTO author (name) VALUES ('Петр Петров');
INSERT INTO book (title, price, author) VALUES ('Тестовая книга 2', 200, currval('author_id_seq'));
INSERT INTO store (city) VALUES ('Минск');
INSERT INTO store_has_books (store_id, book_id) VALUES (currval('store_id_seq'), currval('book_id_seq'));
INSERT INTO store (city) VALUES ('Брест');
INSERT INTO store_has_books (store_id, book_id) VALUES (currval('store_id_seq'), currval('book_id_seq'));

INSERT INTO author (name) VALUES ('Сергей Сергеев');
INSERT INTO book (title, price, author) VALUES ('Тестовая книга 3', 300, currval('author_id_seq'));
INSERT INTO store (city) VALUES ('Гродно');
INSERT INTO store_has_books (store_id, book_id) VALUES (currval('store_id_seq'), currval('book_id_seq'));


