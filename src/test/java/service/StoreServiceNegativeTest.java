package service;

import by.melnikov.books.dao.StoreDao;
import by.melnikov.books.dto.StoreDto;
import by.melnikov.books.entity.Store;
import by.melnikov.books.exception.ServiceException;
import by.melnikov.books.mapper.StoreMapper;
import by.melnikov.books.service.impl.StoreServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoreServiceNegativeTest {
    @Mock
    private StoreDao storeDao;
    @InjectMocks
    private StoreServiceImpl storeService;

    @Test
    @DisplayName("Добавление уже существующего магазина")
    public void testAddNewStoreNegative() {
        StoreDto storeDto = StoreDto.builder()
                .city("Existing Store")
                .build();
        Store store = StoreMapper.INSTANCE.storeDtoToStore(storeDto);
        when(storeDao.findStore(store)).thenReturn(store);
        assertThrows(ServiceException.class, () -> storeService.addNewStore(storeDto));
    }

    @Test
    @DisplayName("Удаление несуществующего магазина")
    public void testRemoveStoreNegative() {
        StoreDto storeDto = StoreDto.builder()
                .city("Existing Store")
                .build();
        Store store = StoreMapper.INSTANCE.storeDtoToStore(storeDto);
        when(storeDao.findStore(store)).thenReturn(null);
        assertThrows(ServiceException.class, () -> storeService.removeStore(storeDto));
    }
}
