package by.melnikov.books.entity;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Book {
    private int id;
    private String title;
    private Author author;
    private int price;
    private List<Store> stores;
}
