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
import ru.easybot.testTusk.models.entities.Laptop;
import ru.easybot.testTusk.services.LaptopsService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LaptopsControllerTest {
    MockMvc mockMvc;

    @MockBean
    private LaptopsController laptopsController;

    @MockBean
    private LaptopsService laptopsService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(laptopsController).build();
    }

    @Test
    void testReturnLaptop() throws Exception {
        Laptop laptop = getLaptop();
        BDDMockito.given(laptopsService.findLaptopById(laptop.getId())).willReturn(laptop);
        mockMvc.perform(MockMvcRequestBuilders.get("/laptops/{id}", laptop.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testReturnAllLaptops() throws Exception {
        BDDMockito.given(laptopsService.getAllLaptops()).willReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/laptops"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateLaptop() throws Exception {
        Laptop laptop = getLaptop();
        BDDMockito.given(laptopsService.createLaptop(laptop)).willReturn(laptop);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonProduct = objectMapper.writeValueAsString(laptop).replaceAll("\"id\":1,", "");
        mockMvc.perform(MockMvcRequestBuilders.post("/laptops")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonProduct)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateLaptop() throws Exception {
        Laptop laptop = getLaptop();
        laptop.setVendor("HP");
        BDDMockito.given(laptopsService.updateLaptop(laptop)).willReturn(laptop);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonProduct = objectMapper.writeValueAsString(laptop).replaceAll("\"id\":1,", "");
        mockMvc.perform(MockMvcRequestBuilders.patch("/laptops/{id}", laptop.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonProduct)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    private Laptop getLaptop() {
        Laptop laptop = new Laptop();
        laptop.setId(1L);
        laptop.setSerialNumber("eac292c4-016e-11ee-be56-0242ac120002");
        laptop.setVendor("ASUS");
        laptop.setPrice(BigDecimal.valueOf(24200.00).setScale(2, RoundingMode.HALF_DOWN));
        laptop.setQuantity(2L);
        laptop.setLaptopSize("17in");
        return laptop;
    }
}
