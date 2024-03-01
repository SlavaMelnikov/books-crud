package by.melnikov.books.service.impl;

import by.melnikov.books.dao.AuthorDao;
import by.melnikov.books.dao.impl.AuthorDaoImpl;
import by.melnikov.books.dto.AuthorDto;
import by.melnikov.books.entity.Author;
import by.melnikov.books.exception.ServiceException;
import by.melnikov.books.mapper.AuthorMapper;
import by.melnikov.books.service.AuthorService;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorDao authorDao;

    public AuthorServiceImpl() {
        authorDao = new AuthorDaoImpl();
    }

    @Override
    public AuthorDto findAuthorById(int id) {
        Author author = authorDao.findAuthorById(id);
        if (author == null) {
            throw new ServiceException(String.format("Not found author with id %d. ", id));
        }
        return AuthorMapper.INSTANCE.authorToAuthorDto(author);
    }

    @Override
    public AuthorDto findAuthorByName(String name) {
        Author author = authorDao.findAuthorByName(name);
        if (author == null) {
            throw new ServiceException(String.format("Not found author with name \"%s\". ", name));
        }
        return AuthorMapper.INSTANCE.authorToAuthorDto(author);
    }

    @Override
    public void addAuthor(AuthorDto authorDto) {
        Author author = AuthorMapper.INSTANCE.authorDtoToAuthor(authorDto);
        authorDao.addAuthor(author);
    }

    @Override
    public void updateName(AuthorDto authorDto) {
        Author author = AuthorMapper.INSTANCE.authorDtoToAuthor(authorDto);
        if (author.getName() == null) {
            throw new ServiceException("You can't update name. Name is null or incorrect");
        }
        authorDao.updateName(author);
    }

    @Override
    public AuthorDto removeAuthorById(int id) {
        Author author = authorDao.findAuthorById(id);
        if (author == null) {
            throw new ServiceException(String.format("Not found author with id: %d", id));
        }
        authorDao.removeAuthorById(id);
        return AuthorMapper.INSTANCE.authorToAuthorDto(author);
    }

    @Override
    public AuthorDto removeAuthorByName(String name) {
        Author author = authorDao.findAuthorByName(name);
        if (author == null) {
            throw new ServiceException(String.format("Not found author with name \"%s\". ", name));
        }
        authorDao.removeAuthorByName(name);
        return AuthorMapper.INSTANCE.authorToAuthorDto(author);
    }
}
