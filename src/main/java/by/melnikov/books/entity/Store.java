package by.melnikov.books.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Store {
    private int id;
    private String city;
    private List<Book> books;
}
