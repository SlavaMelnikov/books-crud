package by.melnikov.books.util;

public class Queries {
    private Queries() {
    }

    /*
    Queries for BookDao
     */
    public static final String FIND_BOOK_BY_ID = """
                                                    SELECT b.book_id, b.title, a.author_id, a.name, b.price
                                                    FROM book b
                                                    JOIN author a ON b.author = a.author_id
                                                    WHERE b.book_id = ?
                                                 """;
    public static final String FIND_BOOK_BY_TITLE = """
                                                        SELECT b.book_id, b.title, a.author_id, a.name, b.price
                                                        FROM book b
                                                        JOIN author a ON b.author = a.author_id
                                                        WHERE b.title = ?
                                                    """;
    public static final String FIND_ALL_STORES_WITH_BOOK = """
                                                              SELECT s.store_id, s.city
                                                              FROM store s
                                                              JOIN store_has_books sb ON s.store_id = sb.store_id
                                                              JOIN book b ON sb.book_id = b.book_id
                                                              WHERE b.title = ?;
                                                           """;
    public static final String ADD_NEW_BOOK = "CALL add_book(?, ?, ?, ?)";

    public static final String UPDATE_PRICE = """
                                                 UPDATE book
                                                 SET price = ?
                                                 WHERE title = ?
                                              """;
    public static final String REMOVE_BOOK_BY_ID = """
                                                      DELETE FROM book
                                                      WHERE book_id = ?
                                                   """;
    public static final String REMOVE_BOOK_BY_TITLE = """
                                                         DELETE FROM book
                                                         WHERE title = ?
                                                      """;
    public static final String COUNT_BOOKS = "SELECT COUNT(book_id) FROM book";

    /*
    Queries for AuthorDao
     */
    public static final String FIND_AUTHOR_BY_ID = """
                                                      SELECT * FROM author
                                                      WHERE author_id = ?
                                                   """;
    public static final String FIND_AUTHOR_BY_NAME = """
                                                        SELECT * FROM author
                                                        WHERE name = ?
                                                     """;
    public static final String FIND_ALL_AUTHOR_BOOKS = """
                                                          SELECT book.book_id, book.title, book.price
                                                          FROM book
                                                          INNER JOIN author ON book.author = author.author_id
                                                          WHERE author.name = ?;
                                                       """;

    public static final String ADD_NEW_AUTHOR = """
                                                   INSERT INTO author (name) VALUES (?)
                                                """;
    public static final String REMOVE_AUTHOR_BY_ID = """
                                                        DELETE FROM author
                                                        WHERE author_id = ?
                                                     """;
    public static final String REMOVE_AUTHOR_BY_NAME = """
                                                          DELETE FROM author
                                                          WHERE name = ?
                                                       """;
    public static final String COUNT_AUTHORS = "SELECT COUNT(author_id) FROM author";

    /*
    Queries for StoreDao
     */
    public static final String FIND_STORE = """
                                               SELECT * FROM store
                                               WHERE city = ?
                                            """;
    public static final String FIND_ALL_STORES = """
                                                    SELECT * FROM store
                                                 """;
    public static final String FIND_ALL_BOOKS_IN_STORE = """
                                                            SELECT b.book_id, b.title, b.price, a.name
                                                            FROM store s
                                                            JOIN store_has_books sb ON s.store_id = sb.store_id
                                                            JOIN book b ON sb.book_id = b.book_id
                                                            JOIN author a ON b.author = a.author_id
                                                            WHERE s.city = ?;
                                                         """;
    public static final String ADD_NEW_STORE = """
                                                  INSERT INTO store (city) VALUES (?)
                                               """;
    public static final String REMOVE_STORE = """
                                                 DELETE FROM store
                                                 WHERE city = ?
                                              """;
    public static final String COUNT_STORES = "SELECT COUNT(store_id) FROM store";
}
