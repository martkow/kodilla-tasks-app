package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

/**
 * <ul>
 *     <li>@SpringJUnitWebConfig - The purpose of this annotation is to initialize the Spring context. It simulates a real application setup to conduct the test. It allows for the use of annotations such as @Autowired, which will be needed in our test.</li>
 *     <li>@WebMvcTest(TrelloController.class) – this annotation automatically configures the MVC infrastructure specifically for unit tests in our application. Its purpose is to expose a single controller for testing, which is specified as an argument. With the @WebMvcTest annotation, we can also use MockMvc, which will be discussed below.</li>
 *     <li>MockMvc – this class allows us to perform HTTP requests to our controller. Moreover, it includes assertion capabilities that you will learn about in this submodule. This class is an excellent tool for creating unit tests for controllers. The MockMvc object (bean) is automatically created in @WebMvcTest tests and can be injected into a variable using the @Autowired annotation.</li>
 *     <li>@MockBean – this annotation is very similar to the @Mock annotation from the Mockito library, which you might already be familiar with. However, there is one important difference! The @MockBean annotation provides a mock object that becomes a bean in the Spring context. We use it when working with @SpringJUnitWebConfig. It allows us to add behaviors to a class that is injected into other components, in this case, the Trello controller.</li>
 * <ul/>
 */
@SpringJUnitWebConfig
@WebMvcTest(TrelloController.class)
class TrelloControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrelloFacade trelloFacade;

    /**
     * <ul>
     * <li>mockMvc.perform() – this is the preparation step for sending a request. This method expects the type of request to be specified. The HTTP methods for this function are available within the MockMvcRequestBuilders class in the org.springframework.test.web.servlet.request package.</li>
     * <li>get() – this method tells the perform() method what type of request is to be sent. In this case, the argument provided is the path /v1/trello/boards. We can further extend our request with additional parameters – in this case, using contentType(), which must be of type APPLICATION_JSON.</li>
     * <li>andExpect() – after closing the parentheses of the perform() method, we gain access to the andExpect() method, which allows us to programmatically check the result of the request! This method is used to verify the server's response.</li>
     * </ul>
     */
    @Test
    void shouldFetchEmptyTrelloBoards() throws Exception {
        // Given
        Mockito.when(trelloFacade.fetchTrelloBoards()).thenReturn(List.of());
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/trello/boards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200)) // or isOk()
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchTrelloBoards() throws Exception {
        // Given
        List<TrelloListDto> trelloLists = List.of(new TrelloListDto("1", "Test list", false));
        List<TrelloBoardDto> trelloBoards = List.of(new TrelloBoardDto("Test Task", "1", trelloLists));

        Mockito.when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/trello/boards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
// If you want to iterate over a list in JSON format and know that the $ symbol is the access point to the response, it makes sense to assume the use of an index. Using an index in the form of $[0] will ensure access to the first element of the list. With this operation, you can then retrieve fields from that element using the dot operator, such as $[0].id. After that, you can perform an assertion using the is() method from the Matchers class.
                // Trello board fields
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Test Task")))
                // Trello list fields
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].name", Matchers.is("Test list")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lists[0].closed", Matchers.is(false)));
    }

    @Test
    void shouldCreateTrelloCard() throws Exception {
        // Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card", "Card description", "idList", "position");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "Card", "shortUrl", "idList", "idBoard", null);

        Mockito.when(trelloFacade.createTrelloCard(Mockito.any(TrelloCardDto.class))).thenReturn(createdTrelloCardDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCardDto);
        // When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/trello/cards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent)) // Transfering request body
                .andExpect(MockMvcResultMatchers.status().is(201))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is("1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.is("Card")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.shortUrl", Matchers.is("shortUrl")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idList", Matchers.is("idList")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idBoard", Matchers.is("idBoard")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.badges", Matchers.nullValue()));
    }
}