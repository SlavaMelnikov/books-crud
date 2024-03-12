create sequence author_id_seq
    as integer
    maxvalue 200000000;

alter sequence author_id_seq owner to postgres;

create sequence book_id_seq
    as integer
    maxvalue 200000000;

alter sequence book_id_seq owner to postgres;

create sequence store_id_seq
    as integer
    maxvalue 200000000;

alter sequence store_id_seq owner to postgres;

create table author
(
    author_id integer default nextval('author_id_seq'::regclass) not null
        constraint "Author_pk"
            primary key,
    name      varchar(255)                                       not null
        constraint unique_author_name
            unique
);

alter table author
    owner to postgres;

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

alter table book
    owner to postgres;

create table store
(
    store_id integer default nextval('store_id_seq'::regclass) not null
        constraint "Store_pk"
            primary key,
    city     varchar(255)                                      not null
        constraint unique_store_address
            unique
);

alter table store
    owner to postgres;

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

alter table store_has_books
    owner to postgres;

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

    -- Добавляем книгу в таблицу 'book'
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

alter procedure add_book(text, text, integer, text[]) owner to postgres;
