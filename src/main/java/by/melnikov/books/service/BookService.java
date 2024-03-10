package by.melnikov.books.service;

import by.melnikov.books.dto.BookDto;
import by.melnikov.books.dto.StoreDto;

import java.util.List;

public interface BookService {
    BookDto findBookById(int id);
    BookDto findBookByTitle(String title);
    List<StoreDto> findAllStoresWithBook(BookDto bookDto);
    void addNewBook(BookDto bookDto);
    boolean updatePrice(BookDto bookDto);
    BookDto removeBookById(int id);
    BookDto removeBookByTitle(String title);
    int countBooks();
}
