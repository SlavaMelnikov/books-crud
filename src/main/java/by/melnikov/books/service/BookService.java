package by.melnikov.books.service;

import by.melnikov.books.dto.BookDto;

public interface BookService {
    BookDto findBookById(int id);
    BookDto findBookByTitle(String title);
    void addNewBook(BookDto bookDto);
}
