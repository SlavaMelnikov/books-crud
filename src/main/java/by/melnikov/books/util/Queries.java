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
                                                              SELECT s.store_id, s.address
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

}
