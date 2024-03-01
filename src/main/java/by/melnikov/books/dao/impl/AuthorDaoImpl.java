package by.melnikov.books.dao.impl;

import by.melnikov.books.connection.ConnectionPool;
import by.melnikov.books.dao.AuthorDao;
import by.melnikov.books.entity.Author;
import by.melnikov.books.exception.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static by.melnikov.books.util.ColumnNames.*;
import static by.melnikov.books.util.Queries.FIND_AUTHOR_BY_NAME;

public class AuthorDaoImpl implements AuthorDao {
    @Override
    public Author findAuthorByName(String name) {
        Author author = null;
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_AUTHOR_BY_NAME)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeQuery();
            ResultSet resultSet = preparedStatement.getResultSet();
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
}
