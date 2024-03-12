package service;

import by.melnikov.books.dao.BookDao;
import by.melnikov.books.dto.BookDto;
import by.melnikov.books.dto.StoreDto;
import by.melnikov.books.entity.Author;
import by.melnikov.books.entity.Book;
import by.melnikov.books.entity.Store;
import by.melnikov.books.mapper.BookMapper;
import by.melnikov.books.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookDao mockBookDao;

    @InjectMocks
    private BookServiceImpl bookService;
    private Author author;
    private Book book;
    private BookDto bookDto;
    private final String TEST_AUTHOR_NAME = "Test Author";
    private final String TEST_BOOK_TITLE = "Test Book";

    @BeforeEach
    void setUp() {
        author = Author.builder()
                .id(1)
                .name(TEST_AUTHOR_NAME)
                .build();
        book = Book.builder()
                .id(1)
                .title(TEST_BOOK_TITLE)
                .author(author)
                .price(100)
                .build();
        bookDto = BookMapper.INSTANCE.bookToBookDto(book);
    }

    @Test
    @DisplayName("Поиск книги по id")
    void testFindBookById() {
        when(mockBookDao.findBookById(1)).thenReturn(book);
        BookDto bookDto = bookService.findBookById(1);
        assertEquals(TEST_BOOK_TITLE, bookDto.getTitle());
        verify(mockBookDao).findBookById(1);
    }

    @Test
    @DisplayName("Поиск книги по названию")
    void testFindBookByTitle() {
        when(mockBookDao.findBookByTitle(TEST_BOOK_TITLE)).thenReturn(book);
        BookDto bookDto = bookService.findBookByTitle(TEST_BOOK_TITLE);
        assertEquals(TEST_BOOK_TITLE, bookDto.getTitle());
        verify(mockBookDao).findBookByTitle(TEST_BOOK_TITLE);
    }

    @Test
    @DisplayName("Поиск всех магазинов с книгой")
    void testFindAllStoresWithBook() {
        List<Store> mockStores = new ArrayList<>();
        mockStores.add(Store.builder().city("Test City").build());
        when(mockBookDao.findAllStoresWithBook(book)).thenReturn(mockStores);
        when(mockBookDao.findBookByTitle(TEST_BOOK_TITLE)).thenReturn(book);
        List<StoreDto> storesDto = bookService.findAllStoresWithBook(bookDto);
        assertEquals(1, storesDto.size());
        assertEquals("Test City", storesDto.get(0).getCity());
        verify(mockBookDao).findAllStoresWithBook(book);
    }

    @Test
    @DisplayName("Удаление книги по id")
    void testRemoveBookById() {
        when(mockBookDao.findBookById(1)).thenReturn(book);
        BookDto bookDto = bookService.removeBookById(1);
        assertEquals(1, bookDto.getId());
        verify(mockBookDao).removeBookById(1);
    }

    @Test
    @DisplayName("Удаление книги по имени")
    void testRemoveBookByTitle() {
        when(mockBookDao.findBookByTitle(TEST_BOOK_TITLE)).thenReturn(book);
        BookDto bookDto = bookService.removeBookByTitle(TEST_BOOK_TITLE);
        assertEquals(TEST_BOOK_TITLE, bookDto.getTitle());
        verify(mockBookDao).removeBookByTitle(TEST_BOOK_TITLE);
    }

    @Test
    @DisplayName("Получение количества книг")
    void testCountBooks() {
        when(mockBookDao.countBooks()).thenReturn(10);
        int count = bookService.countBooks();
        assertEquals(10, count);
        verify(mockBookDao).countBooks();
    }
}
