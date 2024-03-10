package by.melnikov.books.dao;

import by.melnikov.books.entity.Book;
import by.melnikov.books.entity.Store;

import java.util.List;

public interface StoreDao {
    Store findStore(Store store);
    List<Store> findAllStores();
    List<Book> findAllBooksInStore(Store store);
    void addNewStore(Store store);
    void removeStore(Store store);
    int countStores();
}
