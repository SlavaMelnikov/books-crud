package by.melnikov.books.mapper;

import by.melnikov.books.dto.StoreDto;
import by.melnikov.books.entity.Store;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface StoreMapper {
    StoreMapper INSTANCE = Mappers.getMapper(StoreMapper.class);

    @Mapping(target = "id", ignore = true)
    StoreDto storeToStoreDto(Store store);

    @InheritInverseConfiguration
    Store storeDtoToStore(StoreDto storeDao);

    List<StoreDto> listStoresToListStoresDto(List<Store> stores);
}
