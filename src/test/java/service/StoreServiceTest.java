package service;

import by.melnikov.books.dao.StoreDao;

import by.melnikov.books.dto.BookDto;
import by.melnikov.books.dto.StoreDto;
import by.melnikov.books.entity.Book;
import by.melnikov.books.entity.Store;
import by.melnikov.books.mapper.StoreMapper;
import by.melnikov.books.service.impl.StoreServiceImpl;
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
class StoreServiceTest {
    @Mock
    private StoreDao mockStoreDao;

    @InjectMocks
    private StoreServiceImpl storeService;
    private Store store;
    private StoreDto storeDto;
    private final String TEST_BOOK_TITLE = "Test Book";
    private final String TEST_CITY = "Test City";

    @BeforeEach
    void setUp() {
        store = Store.builder()
                .id(1)
                .city(TEST_CITY)
                .build();
        storeDto = StoreMapper.INSTANCE.storeToStoreDto(store);
    }

    @Test
    @DisplayName("Поиск магазина")
    void testFindStore() {
        when(mockStoreDao.findStore(store)).thenReturn(store);
        StoreDto storeDto = storeService.findStore(this.storeDto);
        assertEquals(TEST_CITY, storeDto.getCity());
        verify(mockStoreDao).findStore(store);
    }

    @Test
    @DisplayName("Поиск всех магазинов")
    void testFindAllStores() {
        List<Store> mockStores = new ArrayList<>();
        mockStores.add(Store.builder().city(TEST_CITY).build());
        when(mockStoreDao.findAllStores()).thenReturn(mockStores);
        List<StoreDto> storesDto = storeService.findAllStores();
        assertEquals(1, storesDto.size());
        assertEquals(TEST_CITY, storesDto.get(0).getCity());
        verify(mockStoreDao).findAllStores();
    }

    @Test
    @DisplayName("Поиск всех книг автора")
    void testFindAllBooksInStore() {
        List<Book> mockBooks = new ArrayList<>();
        mockBooks.add(Book.builder().title(TEST_BOOK_TITLE).build());
        when(mockStoreDao.findAllBooksInStore(store)).thenReturn(mockBooks);
        List<BookDto> booksDto = storeService.findAllBooksInStore(storeDto);
        assertEquals(1, booksDto.size());
        assertEquals(TEST_BOOK_TITLE, booksDto.get(0).getTitle());
        verify(mockStoreDao).findAllBooksInStore(store);
    }

    @Test
    @DisplayName("Получение количества авторов")
    void testCountAuthors() {
        when(mockStoreDao.countStores()).thenReturn(10);
        int count = storeService.countStores();
        assertEquals(10, count);
        verify(mockStoreDao).countStores();
    }
}
