package by.melnikov.books.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Author {
    private int id;
    private String name;
    private List<Book> books;
}
