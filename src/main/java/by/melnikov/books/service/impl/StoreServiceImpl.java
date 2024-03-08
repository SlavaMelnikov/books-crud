package by.melnikov.books.service.impl;

import by.melnikov.books.dao.StoreDao;
import by.melnikov.books.dao.impl.StoreDaoImpl;
import by.melnikov.books.dto.BookDto;
import by.melnikov.books.dto.StoreDto;
import by.melnikov.books.entity.Book;
import by.melnikov.books.entity.Store;
import by.melnikov.books.exception.ServiceException;
import by.melnikov.books.mapper.BookMapper;
import by.melnikov.books.mapper.StoreMapper;
import by.melnikov.books.service.StoreService;

import java.util.List;

public class StoreServiceImpl implements StoreService {
    private final StoreDao storeDao;

    public StoreServiceImpl() {
        storeDao = new StoreDaoImpl();
    }

    @Override
    public StoreDto findStore(StoreDto storeDto) {
        Store store = StoreMapper.INSTANCE.storeDtoToStore(storeDto);
        Store foundedStore = storeDao.findStore(store);
        return StoreMapper.INSTANCE.storeToStoreDto(foundedStore);
    }

    @Override
    public List<StoreDto> findAllStores() {
        List<Store> stores = storeDao.findAllStores();
        return StoreMapper.INSTANCE.listStoresToListStoresDto(stores);
    }

    @Override
    public List<BookDto> findAllBooksInStore(StoreDto storeDto) {
        Store store = StoreMapper.INSTANCE.storeDtoToStore(storeDto);
        List<Book> allBooksInStore = storeDao.findAllBooksInStore(store);
        return BookMapper.INSTANCE.listBooksToListBooksDto(allBooksInStore);
    }

    @Override
    public void addNewStore(StoreDto storeDto) {
        if (findStore(storeDto) != null) {
            throw new ServiceException(String.format("Store in %s already exist.", storeDto.getCity()));
        }
        Store store = StoreMapper.INSTANCE.storeDtoToStore(storeDto);
        storeDao.addNewStore(store);
    }

    @Override
    public void removeStore(StoreDto storeDto) {
        if (findStore(storeDto) == null) {
            throw new ServiceException(String.format("Can't remove store %s, because that store doesn't exist.", storeDto.getCity()));
        }
        Store store = StoreMapper.INSTANCE.storeDtoToStore(storeDto);
        storeDao.removeStore(store);
    }
}
