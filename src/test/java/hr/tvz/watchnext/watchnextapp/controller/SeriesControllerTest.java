package hr.tvz.watchnext.watchnextapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import hr.tvz.watchnext.watchnextapp.command.SeriesCommand;
import hr.tvz.watchnext.watchnextapp.enumeration.SeriesStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf; // DODANO

@SpringBootTest
@AutoConfigureMockMvc
class SeriesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testPostSeries() throws Exception {
        SeriesCommand command = new SeriesCommand(
                "Test Series",
                "Drama",
                3,
                SeriesStatus.WATCHING,
                8.5,
                "tt9999999"
        );

        mockMvc.perform(post("/api/series")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command))
                        .with(csrf()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDeleteSeries() throws Exception {
        mockMvc.perform(delete("/api/series/id/{id}", 1L)
                        .with(csrf()));
    }
}