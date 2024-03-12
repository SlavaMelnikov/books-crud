package by.melnikov.books.entity;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Author {
    private int id;
    private String name;
    private List<Book> books;
}
