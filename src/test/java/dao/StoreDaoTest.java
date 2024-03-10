package dao;

import by.melnikov.books.connection.ConnectionPool;
import by.melnikov.books.dao.StoreDao;
import by.melnikov.books.dao.impl.StoreDaoImpl;
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
class StoreDaoTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres")
            .withInitScript("test-schema.sql");
    private StoreDao storeDao;

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
        storeDao = new StoreDaoImpl();
    }

    @Order(1)
    @Test
    @DisplayName("Получение количества магазинов")
    void shouldCountStores() {
        int numberOfStores = storeDao.countStores();
        assertEquals(4, numberOfStores);
    }

    @Order(2)
    @Test
    @DisplayName("Получение списка всех магазинов")
    void shouldFindAllStores() {
        List<Store> stores = storeDao.findAllStores();
        List<String> expected = Arrays.asList("Гомель", "Минск", "Брест", "Гродно");
        List<String> actual = new ArrayList<>();
        for (Store store : stores) {
            actual.add(store.getCity());
        }
        assertEquals(expected, actual);
    }

    @Order(3)
    @Test
    @DisplayName("Поиск магазина")
    void shouldFindStore() {
        Store testStore = Store.builder()
                .city("Минск")
                .build();
        Store foundStore = storeDao.findStore(testStore);
        assertEquals(testStore.getCity(), foundStore.getCity());
    }

    @Order(4)
    @Test
    @DisplayName("Получение всех книг в магазине")
    void shouldFindAllBooksInStore() {
        Store testStore = Store.builder()
                .city("Гомель")
                .books(new ArrayList<>())
                .build();
        List<Book> booksInStore = storeDao.findAllBooksInStore(testStore);
        List<String> expected = Arrays.asList("Тестовая книга 1");
        List<String> actual = new ArrayList<>();
        for (Book book : booksInStore) {
            actual.add(book.getTitle());
        }
        assertEquals(expected, actual);
    }

    @Order(5)
    @Test
    @DisplayName("Добавление нового магазина")
    void shouldAddNewStore() {
        Store newStore = Store.builder()
                .city("Москва")
                .build();
        int storesBeforeAdd = storeDao.countStores();
        storeDao.addNewStore(newStore);
        int storesAfterAdd = storeDao.countStores();
        assertEquals(storesBeforeAdd + 1, storesAfterAdd);
    }

    @Order(6)
    @Test
    @DisplayName("Удаление магазина")
    void shouldRemoveStore() {
        Store store = Store.builder()
                .city("Москва")
                .build();
        int storesBeforeRemoving = storeDao.countStores();
        storeDao.removeStore(store);
        int storeAfterRemoving = storeDao.countStores();
        assertEquals(storesBeforeRemoving - 1, storeAfterRemoving);
    }
}
