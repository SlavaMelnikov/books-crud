package by.melnikov.books.service.impl;

import by.melnikov.books.dao.AuthorDao;
import by.melnikov.books.dao.impl.AuthorDaoImpl;
import by.melnikov.books.dto.AuthorDto;
import by.melnikov.books.dto.BookDto;
import by.melnikov.books.entity.Author;
import by.melnikov.books.entity.Book;
import by.melnikov.books.exception.ServiceException;
import by.melnikov.books.mapper.AuthorMapper;
import by.melnikov.books.mapper.BookMapper;
import by.melnikov.books.service.AuthorService;

import java.util.List;

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
    public List<BookDto> findAllAuthorBooks(AuthorDto authorDto) {
        Author author = AuthorMapper.INSTANCE.authorDtoToAuthor(authorDto);
        findAuthorByName(author.getName());
        List<Book> allAuthorBooks = authorDao.findAllAuthorBooks(author);
        return BookMapper.INSTANCE.listBooksToListBooksDto(allAuthorBooks);
    }

    @Override
    public boolean addNewAuthor(AuthorDto authorDto) {
        Author author = AuthorMapper.INSTANCE.authorDtoToAuthor(authorDto);
        boolean wasAdded = false;
        try {
            findAuthorByName(author.getName());
        } catch (ServiceException e) {
            authorDao.addNewAuthor(author);
            wasAdded = true;
        }
        return wasAdded;
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
