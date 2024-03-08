package by.melnikov.books.servlet;

import by.melnikov.books.dto.BookDto;
import by.melnikov.books.dto.StoreDto;
import by.melnikov.books.exception.ControllerException;
import by.melnikov.books.service.StoreService;
import by.melnikov.books.service.impl.StoreServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.melnikov.books.util.RequestParameters.CITY_REQUEST_PARAMETER;
import static by.melnikov.books.util.RequestParameters.RESPONSE_TYPE;


@WebServlet("/store/*")
public class StoreServlet extends HttpServlet {
    private final StoreService storeService;
    private final Gson gson;

    public StoreServlet() {
        storeService = new StoreServiceImpl();
        gson = new Gson();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(RESPONSE_TYPE);
        StoreDto storeDto = getStoreDtoFromRequestBody(request);
        storeService.addNewStore(storeDto);
        try {
            response.getWriter().write("{\"response\": \"store was successfully added\"}");
        } catch (IOException e) {
            throw new ControllerException(String.format("Error while sending a response. %s", e));
        }
    }

    private StoreDto getStoreDtoFromRequestBody(HttpServletRequest request) {
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
        return gson.fromJson(jsonString.toString(), StoreDto.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(RESPONSE_TYPE);
        String city = request.getParameter(CITY_REQUEST_PARAMETER);
        StoreDto storeDto = StoreDto.builder()
                .city(city)
                .books(new ArrayList<>())
                .build();
        try {
            if (request.getPathInfo() != null && request.getPathInfo().equals("/books")) {
                List<BookDto> allBooksInStore = storeService.findAllBooksInStore(storeDto);
                String jsonString = gson.toJson(allBooksInStore);
                response.getWriter().write(jsonString);
            } else if (request.getPathInfo() != null && request.getPathInfo().equals("/all-stores")) {
                List<StoreDto> allStores = storeService.findAllStores();
                String jsonString = gson.toJson(allStores);
                response.getWriter().write(jsonString);
            }
        } catch (IOException e) {
                throw new ControllerException(String.format("Error while sending a response. %s", e));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType(RESPONSE_TYPE);
        String city = request.getParameter(CITY_REQUEST_PARAMETER);
        StoreDto storeDto = StoreDto.builder()
                .city(city)
                .books(new ArrayList<>())
                .build();
        storeService.removeStore(storeDto);
        try {
            response.getWriter().write("{\"response\": \"store was successfully removed\"}");
        } catch (IOException e) {
            throw new ControllerException(String.format("Error while sending a response. %s", e));
        }
    }
}
