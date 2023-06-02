package ru.easybot.testTusk.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.easybot.testTusk.models.entities.Display;
import ru.easybot.testTusk.services.DisplaysService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DisplaysControllerTest {
    MockMvc mockMvc;

    @MockBean
    private DisplaysController displaysController;

    @MockBean
    private DisplaysService displaysService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(displaysController).build();
    }

    @Test
    void testReturnDisplay() throws Exception {
        Display display = getDisplay();
        BDDMockito.given(displaysService.findDisplayById(display.getId())).willReturn(display);
        mockMvc.perform(MockMvcRequestBuilders.get("/displays/{id}", display.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testReturnAllDisplays() throws Exception {
        BDDMockito.given(displaysService.getAllDisplays()).willReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/displays"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateDisplay() throws Exception {
        Display display = getDisplay();
        BDDMockito.given(displaysService.createDisplay(display)).willReturn(display);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonProduct = objectMapper.writeValueAsString(display).replaceAll("\"id\":1,", "");
        mockMvc.perform(MockMvcRequestBuilders.post("/displays")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonProduct)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateDisplay() throws Exception {
        Display display = getDisplay();
        display.setVendor("IBM");
        BDDMockito.given(displaysService.updateDisplay(display)).willReturn(display);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonProduct = objectMapper.writeValueAsString(display).replaceAll("\"id\":1,", "");
        mockMvc.perform(MockMvcRequestBuilders.patch("/displays/{id}", display.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonProduct)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    private Display getDisplay() {
        Display display = new Display();
        display.setId(1L);
        display.setSerialNumber("3696b4fa-016f-11ee-be56-0242ac120002");
        display.setVendor("Apple");
        display.setPrice(BigDecimal.valueOf(164200.00).setScale(2, RoundingMode.HALF_DOWN));
        display.setQuantity(12L);
        display.setDiagonal(27L);
        return display;
    }
}