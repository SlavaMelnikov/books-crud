package by.melnikov.books.dao;

import by.melnikov.books.entity.Book;

public interface BookDao {
    Book findBookById(int id);
    Book findBookByTitle(String title);
    void addNewBook(Book book);

}
