package by.melnikov.books.service;

import by.melnikov.books.dto.AuthorDto;

public interface AuthorService {
    AuthorDto findAuthorById(int id);
    AuthorDto findAuthorByName(String name);
    void addAuthor(AuthorDto authorDto);
    void updateName(AuthorDto authorDto);
    AuthorDto removeAuthorById(int id);
    AuthorDto removeAuthorByName(String name);
}
