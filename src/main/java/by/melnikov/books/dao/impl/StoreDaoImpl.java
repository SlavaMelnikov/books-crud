package by.melnikov.books.dao.impl;

import by.melnikov.books.connection.ConnectionPool;
import by.melnikov.books.dao.StoreDao;
import by.melnikov.books.entity.Book;
import by.melnikov.books.entity.Store;
import by.melnikov.books.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static by.melnikov.books.util.ColumnNames.*;
import static by.melnikov.books.util.Queries.*;

public class StoreDaoImpl implements StoreDao {

    @Override
    public Store findStore(Store store) {
        Store foundStore = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_STORE)) {
            preparedStatement.setString(1, store.getCity());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                foundStore = Store.builder()
                        .id(resultSet.getInt(STORE_ID))
                        .city(resultSet.getString(STORE_CITY))
                        .build();
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying to find store. %s", e.getMessage()));
        }
        return foundStore;
    }

    @Override
    public List<Store> findAllStores() {
        List<Store> stores = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_STORES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Store store = Store.builder()
                        .id(resultSet.getInt(STORE_ID))
                        .city(resultSet.getString(STORE_CITY))
                        .build();
                stores.add(store);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying to get all stores. %s", e.getMessage()));
        }
        return stores;
    }

    @Override
    public List<Book> findAllBooksInStore(Store store) {
        List<Book> allBooksInStore = store.getBooks();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BOOKS_IN_STORE)) {
            preparedStatement.setString(1, store.getCity());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = Book.builder()
                        .id(resultSet.getInt(BOOK_ID))
                        .title(resultSet.getString(BOOK_TITLE))
                        .price(resultSet.getInt(BOOK_PRICE))
                        .build();
                allBooksInStore.add(book);
            }
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying to find all books in store. %s", e.getMessage()));
        }
        return allBooksInStore;
    }

    @Override
    public void addNewStore(Store store) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_STORE)) {
            preparedStatement.setString(1, store.getCity());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying to add new store. %s", e.getMessage()));
        }
    }

    @Override
    public void removeStore(Store store) {
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_STORE)) {
            preparedStatement.setString(1, store.getCity());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DaoException(String.format("Error while trying remove store. %s", e.getMessage()));
        }
    }
}
