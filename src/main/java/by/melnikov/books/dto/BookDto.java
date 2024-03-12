package by.melnikov.books.dto;

import by.melnikov.books.entity.Book;
import lombok.*;

import java.util.List;
import java.util.function.Function;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookDto {
    private int id;
    private String title;
    private AuthorDto author;
    private int price;
    private List<StoreDto> stores;
}
