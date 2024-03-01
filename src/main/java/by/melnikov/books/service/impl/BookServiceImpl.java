package by.melnikov.books.service.impl;

import by.melnikov.books.dao.AuthorDao;
import by.melnikov.books.dao.BookDao;
import by.melnikov.books.dao.StoreDao;
import by.melnikov.books.dao.impl.AuthorDaoImpl;
import by.melnikov.books.dao.impl.BookDaoImpl;
import by.melnikov.books.dao.impl.StoreDaoImpl;
import by.melnikov.books.dto.BookDto;
import by.melnikov.books.entity.Book;
import by.melnikov.books.exception.ServiceException;
import by.melnikov.books.mapper.BookMapper;
import by.melnikov.books.service.BookService;


public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final StoreDao storeDao;
    private final AuthorDao authorDao;

    public BookServiceImpl() {
        bookDao = new BookDaoImpl();
        storeDao = new StoreDaoImpl();
        authorDao = new AuthorDaoImpl();
    }

    @Override
    public BookDto findBookById(int id) {
        Book book = bookDao.findBookById(id);
        if (book == null) {
            throw new ServiceException(String.format("Not found book with id %d. ",  id));
        }
        return BookMapper.INSTANCE.bookToBookDto(book);
    }

    @Override
    public BookDto findBookByTitle(String title) {
        Book book = bookDao.findBookByTitle(title);
        if (book == null) {
            throw new ServiceException(String.format("Not found book with title %s. ",  title));
        }
        return BookMapper.INSTANCE.bookToBookDto(book);
    }

    @Override
    public void addNewBook(BookDto bookDto) {
        Book book = BookMapper.INSTANCE.bookDtoToBook(bookDto);
        bookDao.addNewBook(book);
    }

    @Override
    public void updatePrice(BookDto bookDto) {
        Book book = BookMapper.INSTANCE.bookDtoToBook(bookDto);
        if (book.getPrice() < 0) {
            throw new ServiceException("You can't make price negative");
        }
        bookDao.updatePrice(book);
    }

    @Override
    public BookDto removeBookById(int id) {
        Book book = bookDao.findBookById(id);
        if (book == null) {
            throw new ServiceException(String.format("Not found book with id: %d",  id));
        }
        bookDao.removeBookById(book.getId());
        return BookMapper.INSTANCE.bookToBookDto(book);
    }

    @Override
    public BookDto removeBookByTitle(String title) {
        Book book = bookDao.findBookByTitle(title);
        if (book == null) {
            throw new ServiceException(String.format("Not found book with title: %s. ",  title));
        }
        bookDao.removeBookByTitle(book.getTitle());
        return BookMapper.INSTANCE.bookToBookDto(book);
    }
}
