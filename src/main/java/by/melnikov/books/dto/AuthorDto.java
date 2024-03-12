package by.melnikov.books.dto;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthorDto {
    private int id;
    private String name;
    private List<BookDto> books;
}
