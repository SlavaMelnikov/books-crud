package by.melnikov.books.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Book {
    private int id;
    private String title;
    private Author author;
    private int price;
    private List<Store> stores;
}
