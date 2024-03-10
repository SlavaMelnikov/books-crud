package by.melnikov.books.dao;

import by.melnikov.books.entity.Book;
import by.melnikov.books.entity.Store;

import java.util.List;

public interface BookDao {
    Book findBookById(int id);
    Book findBookByTitle(String title);
    List<Store> findAllStoresWithBook(Book book);
    void addNewBook(Book book);
    void updatePrice(Book book);
    void removeBookById(int id);
    void removeBookByTitle(String title);
    int countBooks();
}
