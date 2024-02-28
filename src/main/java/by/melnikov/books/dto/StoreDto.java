package by.melnikov.books.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class StoreDto {
    private int id;
    private String address;
    private List<BookDto> books;
}
