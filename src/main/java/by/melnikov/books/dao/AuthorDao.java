package by.melnikov.books.dao;

import by.melnikov.books.entity.Author;

public interface AuthorDao {
    Author findAuthorByName(String name);
}
