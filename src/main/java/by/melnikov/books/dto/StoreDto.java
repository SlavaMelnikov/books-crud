package by.melnikov.books.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class StoreDto {
    private int id;
    private String city;
    private List<BookDto> books;
}
