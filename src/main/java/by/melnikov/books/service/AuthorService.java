package by.melnikov.books.service;

import by.melnikov.books.dto.AuthorDto;
import by.melnikov.books.dto.BookDto;

import java.util.List;

public interface AuthorService {
    AuthorDto findAuthorById(int id);
    AuthorDto findAuthorByName(String name);
    List<BookDto> findAllAuthorBooks(AuthorDto authorDto);
    boolean addNewAuthor(AuthorDto authorDto);
    AuthorDto removeAuthorById(int id);
    AuthorDto removeAuthorByName(String name);
}
