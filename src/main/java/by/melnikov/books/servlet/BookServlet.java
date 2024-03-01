package by.melnikov.books.servlet;

import by.melnikov.books.dto.AuthorDto;
import by.melnikov.books.dto.BookDto;
import by.melnikov.books.exception.ControllerException;
import by.melnikov.books.service.AuthorService;
import by.melnikov.books.service.BookService;
import by.melnikov.books.service.impl.AuthorServiceImpl;
import by.melnikov.books.service.impl.BookServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/book")
public class BookServlet extends HttpServlet {
    private final BookService bookService;
    private final AuthorService authorService;
    private final Gson gson;

    public BookServlet() {
        bookService = new BookServiceImpl();
        authorService = new AuthorServiceImpl();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String requestParameter = request.getParameter("id");
        try {
            int id = Integer.parseInt(requestParameter);
            BookDto bookDto = bookService.findBookById(id);
            if (bookDto == null) {
                response.getWriter().write("\"response\": \"\"book with such id not found\"}");
            }
            String jsonString = gson.toJson(bookDto);
            response.getWriter().write(jsonString);
        } catch (NumberFormatException e) {
            throw new ControllerException(String.format("Your id (%s) are not a number.", requestParameter));
        } catch (IOException e) {
            throw new ControllerException("Error while sending a response");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String requestBody = getJsonStringFromRequest(request);
        BookDto bookDto = gson.fromJson(requestBody, BookDto.class);
        bookService.addNewBook(bookDto);
        response.getWriter().write("{\"response\": \"book was successfully added\"}");
    }

    private String getJsonStringFromRequest(HttpServletRequest request) {
        StringBuilder jsonString = null;
        try (BufferedReader reader = request.getReader()) {
            jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
        } catch (IOException e) {
            throw new ControllerException("Error during parsing body of post method");
        }
        return jsonString.toString();
    }
}
