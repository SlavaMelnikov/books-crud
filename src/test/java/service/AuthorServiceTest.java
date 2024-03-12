package service;

import by.melnikov.books.dao.AuthorDao;
import by.melnikov.books.dto.AuthorDto;
import by.melnikov.books.dto.BookDto;
import by.melnikov.books.entity.Author;
import by.melnikov.books.entity.Book;
import by.melnikov.books.mapper.AuthorMapper;
import by.melnikov.books.service.impl.AuthorServiceImpl;
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
class AuthorServiceTest {
    @Mock
    private AuthorDao mockAuthorDao;

    @InjectMocks
    private AuthorServiceImpl authorService;
    private Author author;
    private AuthorDto authorDto;
    private final String TEST_AUTHOR_NAME = "Test Author";
    private final String TEST_BOOK_TITLE = "Test Book";

    @BeforeEach
    void setUp() {
        author = Author.builder()
                .id(1)
                .name(TEST_AUTHOR_NAME)
                .build();
        authorDto = AuthorMapper.INSTANCE.authorToAuthorDto(author);
    }

    @Test
    @DisplayName("Поиск автора по id")
    void testFindAuthorById() {
        when(mockAuthorDao.findAuthorById(1)).thenReturn(author);
        AuthorDto authorDto = authorService.findAuthorById(1);
        assertEquals(TEST_AUTHOR_NAME, authorDto.getName());
        verify(mockAuthorDao).findAuthorById(1);
    }

    @Test
    @DisplayName("Поиск автора по имени")
    void testFindAuthorByName() {
        when(mockAuthorDao.findAuthorByName(TEST_AUTHOR_NAME)).thenReturn(author);
        AuthorDto authorDto = authorService.findAuthorByName(TEST_AUTHOR_NAME);
        assertEquals(TEST_AUTHOR_NAME, authorDto.getName());
        verify(mockAuthorDao).findAuthorByName(TEST_AUTHOR_NAME);
    }

    @Test
    @DisplayName("Поиск всех книг автора")
    void testFindAllAuthorBooks() {
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(Book.builder().title(TEST_BOOK_TITLE).build());
        when(mockAuthorDao.findAllAuthorBooks(author)).thenReturn(mockBooks);
        when(mockAuthorDao.findAuthorByName(author.getName())).thenReturn(author);
        List<BookDto> booksDto = authorService.findAllAuthorBooks(authorDto);
        assertEquals(1, booksDto.size());
        assertEquals(TEST_BOOK_TITLE, booksDto.get(0).getTitle());
        verify(mockAuthorDao).findAllAuthorBooks(author);
    }

    @Test
    @DisplayName("Удаление автора по id")
    void testRemoveAuthorById() {
        when(mockAuthorDao.findAuthorById(1)).thenReturn(author);
        AuthorDto authorDto = authorService.removeAuthorById(1);
        assertEquals(1, authorDto.getId());
        verify(mockAuthorDao).removeAuthorById(1);
    }

    @Test
    @DisplayName("Удаление автора по имени")
    void testRemoveAuthorByName() {
        when(mockAuthorDao.findAuthorByName(TEST_AUTHOR_NAME)).thenReturn(author);
        AuthorDto authorDto = authorService.removeAuthorByName(TEST_AUTHOR_NAME);
        assertEquals(TEST_AUTHOR_NAME, authorDto.getName());
        verify(mockAuthorDao).removeAuthorByName(TEST_AUTHOR_NAME);
    }

    @Test
    @DisplayName("Получение количества авторов")
    void testCountAuthors() {
        when(mockAuthorDao.countAuthors()).thenReturn(10);
        int count = authorService.countAuthors();
        assertEquals(10, count);
        verify(mockAuthorDao).countAuthors();
    }
}
