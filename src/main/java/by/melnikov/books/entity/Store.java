package by.melnikov.books.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class Store {
    private int id;
    private String city;
    private List<Book> books;
}
