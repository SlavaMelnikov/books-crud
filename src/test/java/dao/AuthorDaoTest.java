package dao;

import by.melnikov.books.connection.ConnectionPool;
import by.melnikov.books.dao.AuthorDao;
import by.melnikov.books.dao.impl.AuthorDaoImpl;
import by.melnikov.books.entity.Author;
import by.melnikov.books.entity.Book;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthorDaoTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres")
            .withInitScript("test-schema.sql");
    private AuthorDao authorDao;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
        ConnectionPool.turnOffTestContainersConnections();
    }

    @BeforeEach
    void setUp() {
        HikariConfig testConfig = new HikariConfig();
        testConfig.setJdbcUrl(postgres.getJdbcUrl());
        testConfig.setUsername(postgres.getUsername());
        testConfig.setPassword(postgres.getPassword());
        HikariDataSource testDataSource = new HikariDataSource(testConfig);
        ConnectionPool.turnOnTestContainersConnections(testDataSource);
        authorDao = new AuthorDaoImpl();
    }

    @Order(1)
    @Test
    @DisplayName("Получение количества авторов")
    void testCountAuthors() {
        int numberOfAuthors = authorDao.countAuthors();
        assertEquals(3, numberOfAuthors);
    }

    @Order(2)
    @Test
    @DisplayName("Получение автора по id")
    void testFindAuthorById() {
        Author testAuthor = authorDao.findAuthorById(1);
        assertEquals(1, testAuthor.getId());
    }

    @Order(3)
    @Test
    @DisplayName("Получение автора по имени")
    void testFindAuthorByName() {
        Author testAuthor = authorDao.findAuthorByName("Иван Иванов");
        assertEquals("Иван Иванов", testAuthor.getName());
    }

    @Order(4)
    @Test
    @DisplayName("Поиск всех книг автора")
    void testFindAllStoresWithBook() {
        Author author = authorDao.findAuthorByName("Иван Иванов");
        author.setBooks(new ArrayList<>());
        List<Book> books = authorDao.findAllAuthorBooks(author);
        List<String> expected = Arrays.asList("Тестовая книга 1");
        List<String> actual = Arrays.asList(books.get(0).getTitle());
        assertEquals(expected, actual);
    }

    @Order(5)
    @Test
    @DisplayName("Добавление нового автора")
    void testAddNewAuthor() {
        Author newAuthor = Author.builder()
                .name("Новый автор")
                .build();
        int authorsBeforeAdd = authorDao.countAuthors();
        authorDao.addNewAuthor(newAuthor);
        int authorsAfterAdd = authorDao.countAuthors();
        assertEquals(authorsBeforeAdd + 1, authorsAfterAdd);
    }

    @Order(6)
    @Test
    @DisplayName("Удаление автора по id")
    void testRemoveAuthorById() {
        int authorsBeforeRemoving = authorDao.countAuthors();
        authorDao.removeAuthorById(2);
        int authorsAfterRemoving = authorDao.countAuthors();
        assertEquals(authorsBeforeRemoving - 1, authorsAfterRemoving);
    }

    @Order(7)
    @Test
    @DisplayName("Удаление автора по названию")
    void testRemoveAuthorByName() {
        int authorsBeforeRemoving = authorDao.countAuthors();
        authorDao.removeAuthorByName("Новый автор");
        int authorsAfterRemoving = authorDao.countAuthors();
        assertEquals(authorsBeforeRemoving - 1, authorsAfterRemoving);
    }
}
