package by.melnikov.books.service;

import by.melnikov.books.dto.BookDto;
import by.melnikov.books.dto.StoreDto;

import java.util.List;

public interface StoreService {
    StoreDto findStore(StoreDto storeDto);
    List<StoreDto> findAllStores();
    List<BookDto> findAllBooksInStore(StoreDto storeDto);
    void addNewStore(StoreDto storeDto);
    void removeStore(StoreDto storeDto);
}
