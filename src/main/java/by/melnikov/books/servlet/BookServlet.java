package by.melnikov.books.servlet;

import by.melnikov.books.dto.BookDto;
import by.melnikov.books.exception.ControllerException;
import by.melnikov.books.service.BookService;
import by.melnikov.books.service.impl.BookServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/book")
public class BookServlet extends HttpServlet {
    private static final String RESPONSE_TYPE = "application/json";
    private static final String ID_REQUEST_PARAMETER = "id";
    private static final String TITLE_REQUEST_PARAMETER = "title";
    private final BookService bookService;
    private final Gson gson;

    public BookServlet() {
        bookService = new BookServiceImpl();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(RESPONSE_TYPE);
        BookDto bookDto = getBookDtoFromRequestBody(request);
        bookService.addNewBook(bookDto);
        try {
            response.getWriter().write("{\"response\": \"book was successfully added\"}");
        } catch (IOException e) {
            throw new ControllerException("Error while sending a response.");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(RESPONSE_TYPE);
        BookDto bookDto = getBookDtoFromRequestBody(request);
        boolean result = bookService.updatePrice(bookDto);
        try {
            if (result) {
                response.getWriter().write("{\"response\": \"price was successfully update\"}");
            } else {
                response.getWriter().write("{\"response\": \"price wasn't updated\"}");
            }
        } catch (IOException e) {
            throw new ControllerException("Error while sending a response");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response, true);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        processRequest(request, response, false);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response, boolean isGet) {
        response.setContentType(RESPONSE_TYPE);
        String bookId = request.getParameter(ID_REQUEST_PARAMETER);
        String bookTitle = request.getParameter(TITLE_REQUEST_PARAMETER);
        BookDto bookDto;
        try {
            if (bookId != null) {
                int id = Integer.parseInt(bookId);
                bookDto = isGet ? bookService.findBookById(id)
                                : bookService.removeBookById(id);
            } else {
                bookDto = isGet ? bookService.findBookByTitle(bookTitle.replaceAll("_", " "))
                                : bookService.removeBookByTitle(bookTitle.replaceAll("_", " "));
            }
            String jsonString = gson.toJson(bookDto);
            response.getWriter().write(jsonString);
        } catch (NumberFormatException e) {
            throw new ControllerException("Your request parameter is incorrect.");
        } catch (IOException e) {
            throw new ControllerException("Error while sending a response");
        }
    }

    private BookDto getBookDtoFromRequestBody(HttpServletRequest request) {
        StringBuilder jsonString;
        try (BufferedReader reader = request.getReader()) {
            jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
        } catch (IOException e) {
            throw new ControllerException("Error during parsing body request body.");
        }
        return gson.fromJson(jsonString.toString(), BookDto.class);
    }
}
