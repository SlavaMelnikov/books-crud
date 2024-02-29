package by.melnikov.books.service;

import by.melnikov.books.dto.AuthorDto;

public interface AuthorService {
    AuthorDto findAuthorByName(String name);
}
