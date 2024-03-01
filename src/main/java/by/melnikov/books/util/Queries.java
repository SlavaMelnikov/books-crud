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


    public static final String REMOVE_AUTHOR_BY_ID = """
                                                        DELETE FROM author
                                                        WHERE author_id = ?
                                                     """;
    public static final String REMOVE_AUTHOR_BY_NAME = """
                                                            DELETE FROM author
                                                            WHERE name = ?
                                                        """;

}
