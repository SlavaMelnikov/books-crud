package service;

import by.melnikov.books.dao.AuthorDao;
import by.melnikov.books.exception.ServiceException;
import by.melnikov.books.service.impl.AuthorServiceImpl;
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
class AuthorServiceNegativeTest {
    @Mock
    private AuthorDao authorDao;

    @InjectMocks
    private AuthorServiceImpl authorService;
    private final String TEST_INCORRECT_AUTHOR_NAME = "Non existing author";


    @Test
    @DisplayName("Поиск автора по неверному id")
    void testFindAuthorByIncorrectId() {
        when(authorDao.findAuthorById(-1)).thenReturn(null);
        assertThrows(ServiceException.class, () -> authorService.findAuthorById(-1));
        verify(authorDao).findAuthorById(-1);
    }

    @Test
    @DisplayName("Поиск автора по неверному имени")
    void testFindAuthorByIncorrectName() {
        when(authorDao.findAuthorByName(TEST_INCORRECT_AUTHOR_NAME)).thenReturn(null);
        assertThrows(ServiceException.class, () -> authorService.findAuthorByName(TEST_INCORRECT_AUTHOR_NAME));
        verify(authorDao).findAuthorByName(TEST_INCORRECT_AUTHOR_NAME);
    }
}
