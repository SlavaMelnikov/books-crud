package by.melnikov.books.service.impl;

import by.melnikov.books.dao.AuthorDao;
import by.melnikov.books.dao.impl.AuthorDaoImpl;
import by.melnikov.books.dto.AuthorDto;
import by.melnikov.books.entity.Author;
import by.melnikov.books.mapper.AuthorMapper;
import by.melnikov.books.service.AuthorService;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    public AuthorServiceImpl() {
        authorDao = new AuthorDaoImpl();
    }
    @Override
    public AuthorDto findAuthorByName(String name) {
        Author author = authorDao.findAuthorByName(name);
        return AuthorMapper.INSTANCE.authorToAuthorDto(author);
    }
}
