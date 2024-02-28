package by.melnikov.books.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AuthorDto {
    private int id;
    private String name;
    private List<BookDto> books;
}
