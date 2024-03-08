package by.melnikov.books.servlet;

import by.melnikov.books.dto.BookDto;
import by.melnikov.books.dto.StoreDto;
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
import java.util.ArrayList;
import java.util.List;

import static by.melnikov.books.util.RequestParameters.*;

@WebServlet("/book/*")
public class BookServlet extends HttpServlet {
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
            throw new ControllerException(String.format("Error while sending a response. %s", e));
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
            throw new ControllerException(String.format("Error while sending a response. %s", e));
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
            throw new ControllerException(String.format("Error during parsing request body. %s", e));
        }
        return gson.fromJson(jsonString.toString(), BookDto.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        if (request.getPathInfo() != null && request.getPathInfo().equals("/stores") ) {
            String bookTitle = request.getParameter(TITLE_REQUEST_PARAMETER).replace("_", " ");
            BookDto bookDto = BookDto.builder()
                    .title(bookTitle)
                    .stores(new ArrayList<>())
                    .build();
            List<StoreDto> allStoresWithBook = bookService.findAllStoresWithBook(bookDto);
            String jsonString = gson.toJson(allStoresWithBook);
            try {
                response.getWriter().write(jsonString);
            } catch (IOException e) {
                throw new ControllerException(String.format("Error while sending a response. %s", e));
            }
        } else {
            processRequest(request, response, true);
        }
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
                bookDto = isGet ? bookService.findBookByTitle(bookTitle.replace("_", " "))
                                : bookService.removeBookByTitle(bookTitle.replace("_", " "));
            }
            String jsonString = gson.toJson(bookDto);
            response.getWriter().write(jsonString);
        } catch (NumberFormatException e) {
            throw new ControllerException(String.format("Your request parameter is incorrect. %s", e));
        } catch (IOException e) {
            throw new ControllerException(String.format("Error while sending a response. %s", e));
        }
    }
}
