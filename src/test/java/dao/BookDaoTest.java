package dao;

import by.melnikov.books.connection.ConnectionPool;
import by.melnikov.books.dao.BookDao;
import by.melnikov.books.dao.impl.BookDaoImpl;
import by.melnikov.books.entity.Author;
import by.melnikov.books.entity.Book;
import by.melnikov.books.entity.Store;
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
public class BookDaoTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres")
            .withInitScript("test-schema.sql");
    private BookDao bookDao;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        HikariConfig testConfig = new HikariConfig();
        testConfig.setJdbcUrl(postgres.getJdbcUrl());
        testConfig.setUsername(postgres.getUsername());
        testConfig.setPassword(postgres.getPassword());
        HikariDataSource testDataSource = new HikariDataSource(testConfig);
        ConnectionPool.turnOnTestContainersConnections(testDataSource);
        bookDao = new BookDaoImpl();
    }

    @Order(1)
    @Test
    @DisplayName("Получение количества книг")
    public void shouldCountBooks() {
        int numberOfBooks = bookDao.countBooks();
        assertEquals(3, numberOfBooks);
    }

    @Order(2)
    @Test
    @DisplayName("Получение книги по id")
    public void shouldFindBookById() {
        Book testBook = bookDao.findBookById(1);
        assertEquals(1, testBook.getId());
    }

    @Order(3)
    @Test
    @DisplayName("Получение книги по названию")
    public void shouldFindBookByTitle() {
        Book testBook = bookDao.findBookByTitle("Тестовая книга 1");
        assertEquals("Тестовая книга 1", testBook.getTitle());
    }

    @Order(4)
    @Test
    @DisplayName("Изменение цены книги")
    public void shouldUpdateBookPrice() {
        Book testBook = Book.builder()
                .title("Тестовая книга 1")
                .price(1000)
                .build();
        bookDao.updatePrice(testBook);
        assertEquals(bookDao.findBookByTitle("Тестовая книга 1").getPrice(), 1000);
    }

    @Order(5)
    @Test
    @DisplayName("Поиск всех магазинов с книгой")
    public void shouldFindAllStoresWithBook() {
        Book book = bookDao.findBookByTitle("Тестовая книга 2");
        book.setStores(new ArrayList<>());
        List<Store> stores = bookDao.findAllStoresWithBook(book);
        List<String> expected = Arrays.asList("Минск", "Брест");
        List<String> actual = Arrays.asList(stores.get(0).getCity(), stores.get(1).getCity());
        assertEquals(expected, actual);
    }

    @Order(6)
    @Test
    @DisplayName("Добавление новой книги")
    public void shouldAddNewBook() {
        Author newBookAuthor = Author.builder()
                .name("Автор новой книги")
                .build();
        Book newBook = Book.builder()
                .title("Новая книга")
                .author(newBookAuthor)
                .price(99)
                .stores(new ArrayList<>())
                .build();
        int booksBeforeAdd = bookDao.countBooks();
        bookDao.addNewBook(newBook);
        int booksAfterAdd = bookDao.countBooks();
        assertEquals(booksBeforeAdd + 1, booksAfterAdd);
    }

    @Order(7)
    @Test
    @DisplayName("Удаление книги по id")
    public void shouldRemoveBookById() {
        int booksBeforeRemoving = bookDao.countBooks();
        bookDao.removeBookById(2);
        int booksAfterRemoving = bookDao.countBooks();
        assertEquals(booksBeforeRemoving - 1, booksAfterRemoving);
    }

    @Order(8)
    @Test
    @DisplayName("Удаление книги по названию")
    public void shouldRemoveBookByTitle() {
        int booksBeforeRemoving = bookDao.countBooks();
        bookDao.removeBookByTitle("Тестовая книга 3");
        int booksAfterRemoving = bookDao.countBooks();
        assertEquals(booksBeforeRemoving - 1, booksAfterRemoving);
    }
}
