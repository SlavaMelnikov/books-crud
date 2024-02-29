package by.melnikov.books.dao.impl;

import by.melnikov.books.connection.ConnectionPool;
import by.melnikov.books.dao.BookDao;
import by.melnikov.books.entity.Author;
import by.melnikov.books.entity.Book;
import by.melnikov.books.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static by.melnikov.books.util.ColumnNames.*;
import static by.melnikov.books.util.Queries.FIND_BOOK_BY_ID;
import static by.melnikov.books.util.Queries.FIND_BOOK_BY_TITLE;

public class BookDaoImpl implements BookDao {
    @Override
    public Book findBookById(int id) {
        Book book = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BOOK_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                Author author = Author
                        .builder()
                        .id(resultSet.getInt(AUTHOR_ID))
                        .name(resultSet.getString(AUTHOR_NAME))
                        .build();
                book = Book.builder()
                        .id(id)
                        .title(resultSet.getString(BOOK_TITLE))
                        .author(author)
                        .price(resultSet.getInt(BOOK_PRICE))
                        .build();
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying to get book by id %d: %s", id, e.getMessage()));
        }
        return book;
    }

    @Override
    public Book findBookByTitle(String title) {
        Book book = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BOOK_BY_TITLE)) {
            preparedStatement.setString(1, title);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                Author author = Author
                        .builder()
                        .id(resultSet.getInt(AUTHOR_ID))
                        .name(resultSet.getString(AUTHOR_NAME))
                        .build();
                book = Book.builder()
                        .id(resultSet.getInt(BOOK_ID))
                        .title(resultSet.getString(BOOK_TITLE))
                        .author(author)
                        .price(resultSet.getInt(BOOK_PRICE))
                        .build();
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying to get book by title \"%s\": %s", title, e.getMessage()));
        }
        return book;
    }

    @Override
    public void addNewBook(Book book) {

    }
}
