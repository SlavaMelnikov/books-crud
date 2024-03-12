package by.melnikov.books.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class StoreDto {
    private int id;
    private String city;
    private List<BookDto> books;
}
