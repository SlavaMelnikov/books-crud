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
    public static final String ADD_BOOK_TO_STORES = "CALL add_book(?, ?, ?, ?)";

    /*
    Queries for AuthorDao
     */
    public static final String FIND_AUTHOR_BY_NAME = """
                                                        SELECT * FROM author
                                                        WHERE name = ?
                                                     """;

}
