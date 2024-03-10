package by.melnikov.books.dao;

import by.melnikov.books.entity.Author;
import by.melnikov.books.entity.Book;

import java.util.List;

public interface AuthorDao {
    Author findAuthorById(int id);
    Author findAuthorByName(String name);
    List<Book> findAllAuthorBooks(Author author);
    void addNewAuthor(Author author);
    void removeAuthorById(int id);
    void removeAuthorByName(String name);
    int countAuthors();
}
