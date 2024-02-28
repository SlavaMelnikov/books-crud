package by.melnikov.books.mapper;

import by.melnikov.books.dto.BookDto;
import by.melnikov.books.entity.Book;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {AuthorMapper.class, StoreMapper.class})
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "id", ignore = true)
    BookDto bookToBookDto(Book book);

    @InheritInverseConfiguration
    Book bookDtoToBook(BookDto bookDto);

    List<BookDto> listBooksToListBooksDto(List<Book> books);
}
