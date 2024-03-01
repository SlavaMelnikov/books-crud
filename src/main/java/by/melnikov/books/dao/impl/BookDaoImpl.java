package by.melnikov.books.dao.impl;

import by.melnikov.books.connection.ConnectionPool;
import by.melnikov.books.dao.BookDao;
import by.melnikov.books.entity.Author;
import by.melnikov.books.entity.Book;
import by.melnikov.books.entity.Store;
import by.melnikov.books.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static by.melnikov.books.util.ColumnNames.*;
import static by.melnikov.books.util.Queries.*;

public class BookDaoImpl implements BookDao {
    @Override
    public Book findBookById(int id) {
        Book book = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BOOK_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
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
            throw new DaoException(String.format("Error while trying to get book by id %d. %s", id, e.getMessage()));
        }
        return book;
    }

    @Override
    public Book findBookByTitle(String title) {
        Book book = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BOOK_BY_TITLE)) {
            preparedStatement.setString(1, title);
            ResultSet resultSet = preparedStatement.executeQuery();
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
            throw new DaoException(String.format("Error while trying to get book by title \"%s\". %s", title, e.getMessage()));
        }
        return book;
    }

    @Override
    public List<Store> findAllStoresWithBook(Book book) {
        return null;
    }

    @Override
    public void addNewBook(Book book) {
        try (Connection connection = ConnectionPool.getConnection();
             CallableStatement callableStatement = connection.prepareCall(ADD_NEW_BOOK)) {
            List<String> storesAddresses = new ArrayList<>();
            book.getStores().stream().forEach(store -> storesAddresses.add(store.getAddress()));
            Array storesSqlArray = connection.createArrayOf("text", storesAddresses.toArray());
            callableStatement.setString(1, book.getTitle());
            callableStatement.setString(2, book.getAuthor().getName());
            callableStatement.setInt(3, book.getPrice());
            callableStatement.setArray(4, storesSqlArray);
            callableStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying to add new book. %s", e.getMessage()));
        }
    }

    @Override
    public void updatePrice(Book book) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRICE)) {
            preparedStatement.setInt(1, book.getPrice());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying to update book price. %s", e.getMessage()));
        }
    }


    @Override
    public void removeBookById(int id) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BOOK_BY_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying to remove book by id %d. %s", id, e.getMessage()));
        }
    }

    @Override
    public void removeBookByTitle(String title) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BOOK_BY_TITLE)) {
            preparedStatement.setString(1, title);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying to remove book by title %s. %s",
                    title,
                    e.getMessage()));
        }
    }
}
