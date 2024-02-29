package by.melnikov.books.mapper;

import by.melnikov.books.dto.AuthorDto;
import by.melnikov.books.entity.Author;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDto authorToAuthorDto(Author author);

    @InheritInverseConfiguration
    Author authorDtoToAuthor(AuthorDto authorDto);

    List<AuthorDto> listAuthorsToListAuthorsDto(List<Author> authors);
}
