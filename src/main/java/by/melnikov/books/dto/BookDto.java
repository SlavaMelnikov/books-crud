package by.melnikov.books.dto;

import by.melnikov.books.entity.Book;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
public class BookDto {
    private int id;
    private String title;
    private AuthorDto author;
    private int price;
    private List<StoreDto> stores;
}
