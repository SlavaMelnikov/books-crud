package by.melnikov.books.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
