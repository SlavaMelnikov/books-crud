package service;

import by.melnikov.books.dao.BookDao;
import by.melnikov.books.dto.BookDto;
import by.melnikov.books.exception.ServiceException;
import by.melnikov.books.service.impl.BookServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceNegativeTest {
    @Mock
    private BookDao bookDao;
    @InjectMocks
    private BookServiceImpl bookService;
    private final String TEST_INCORRECT_BOOK_TITLE = "Non existing book title";

    @Test
    @DisplayName("Поиск книги по неверному id")
    void testFindBookByIncorrectId() {
        when(bookDao.findBookById(-1)).thenReturn(null);
        assertThrows(ServiceException.class, () -> bookService.findBookById(-1));
        verify(bookDao).findBookById(-1);
    }

    @Test
    @DisplayName("Поиск книги по неверному названию")
    void testFindAuthorByIncorrectName() {
        when(bookDao.findBookByTitle(TEST_INCORRECT_BOOK_TITLE)).thenReturn(null);
        assertThrows(ServiceException.class, () -> bookService.findBookByTitle(TEST_INCORRECT_BOOK_TITLE));
        verify(bookDao).findBookByTitle(TEST_INCORRECT_BOOK_TITLE);
    }

    @Test
    @DisplayName("Изменение цены на неверную")
    public void testUpdatePriceNegative() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Test Book");
        bookDto.setPrice(-100);
        assertThrows(ServiceException.class, () -> bookService.updatePrice(bookDto));
    }
}
