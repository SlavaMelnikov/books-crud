package by.melnikov.books.servlet;

import by.melnikov.books.dto.AuthorDto;
import by.melnikov.books.dto.BookDto;
import by.melnikov.books.exception.ControllerException;
import by.melnikov.books.service.AuthorService;
import by.melnikov.books.service.impl.AuthorServiceImpl;
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

@WebServlet("/author/*")
public class AuthorServlet extends HttpServlet {
    private final AuthorService authorService;
    private final Gson gson;

    public AuthorServlet() {
        authorService = new AuthorServiceImpl();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(RESPONSE_TYPE);
        AuthorDto authorDto = getAuthorDtoFromRequestBody(request);
        boolean result = authorService.addNewAuthor(authorDto);
        try {
            if (result) {
                response.getWriter().write("{\"response\": \"new author was successfully added\"}");
            } else {
                response.getWriter().write("{\"response\": \"this author already exist\"}");
            }
        } catch (IOException e) {
            throw new ControllerException(String.format("Error while sending a response. %s", e));
        }
    }

    private AuthorDto getAuthorDtoFromRequestBody(HttpServletRequest request) {
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
        return gson.fromJson(jsonString.toString(), AuthorDto.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        if (request.getPathInfo() != null && request.getPathInfo().equals("/books") ) {
            String authorName = request.getParameter(NAME_REQUEST_PARAMETER).replace("_", " ");
            AuthorDto authorDto = AuthorDto.builder()
                    .name(authorName)
                    .books(new ArrayList<>())
                    .build();
            List<BookDto> allAuthorBooks = authorService.findAllAuthorBooks(authorDto);
            String jsonString = gson.toJson(allAuthorBooks);
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
        String authorId = request.getParameter(ID_REQUEST_PARAMETER);
        String authorName = request.getParameter(NAME_REQUEST_PARAMETER);
        AuthorDto authorDto;
        try {
            if (authorId != null) {
                int id = Integer.parseInt(authorId);
                authorDto = isGet ? authorService.findAuthorById(id)
                                  : authorService.removeAuthorById(id);
            } else {
                authorDto = isGet ? authorService.findAuthorByName(authorName.replace("_", " "))
                                  : authorService.removeAuthorByName(authorName.replace("_", " "));
            }
            String jsonString = gson.toJson(authorDto);
            response.getWriter().write(jsonString);
        } catch (NumberFormatException e) {
            throw new ControllerException(String.format("Your  request parameter is incorrect. %s", e));
        } catch (IOException e) {
            throw new ControllerException(String.format("Error while sending a response. %s", e));
        }
    }
}
