package by.melnikov.books.dao.impl;

import by.melnikov.books.connection.ConnectionPool;
import by.melnikov.books.dao.AuthorDao;
import by.melnikov.books.entity.Author;
import by.melnikov.books.entity.Book;
import by.melnikov.books.exception.DaoException;

import java.sql.*;
import java.util.List;

import static by.melnikov.books.util.ColumnNames.*;
import static by.melnikov.books.util.Queries.*;

public class AuthorDaoImpl implements AuthorDao {
    @Override
    public Author findAuthorById(int id) {
        Author author = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_AUTHOR_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                author = Author
                        .builder()
                        .id(resultSet.getInt(AUTHOR_ID))
                        .name(resultSet.getString(AUTHOR_NAME))
                        .build();
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying to get author by id %d. %s", id, e.getMessage()));
        }
        return author;
    }

    @Override
    public Author findAuthorByName(String name) {
        Author author = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_AUTHOR_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                author = Author
                        .builder()
                        .id(resultSet.getInt(AUTHOR_ID))
                        .name(resultSet.getString(AUTHOR_NAME))
                        .build();
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying to get author by \"%s\" name. %s", name, e.getMessage()));
        }
        return author;
    }

    @Override
    public List<Book> findAllAuthorBooks(Author author) {
        List<Book> allAuthorBooks = author.getBooks();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_AUTHOR_BOOKS)) {
            preparedStatement.setString(1, author.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = Book.builder()
                        .id(resultSet.getInt(BOOK_ID))
                        .title(resultSet.getString(BOOK_TITLE))
                        .price(resultSet.getInt(BOOK_PRICE))
                        .build();
                allAuthorBooks.add(book);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying to find all author's books. %s", e.getMessage()));
        }
        return allAuthorBooks;
    }

    @Override
    public void addNewAuthor(Author author) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_AUTHOR)) {
            preparedStatement.setString(1, author.getName());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying to add new author. %s", e.getMessage()));
        }
    }

    @Override
    public void removeAuthorById(int id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_AUTHOR_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying to remove author by id %d. %s", id, e.getMessage()));
        }
    }

    @Override
    public void removeAuthorByName(String name) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_AUTHOR_BY_NAME)) {
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying to remove author by name \"%s\". %s",
                    name,
                    e.getMessage()));
        }
    }
}
