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
import ru.easybot.testTusk.models.entities.PC;
import ru.easybot.testTusk.services.PCsService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PCsControllerTest {
    MockMvc mockMvc;

    @MockBean
    private PCsController personalComputersController;

    @MockBean
    private PCsService personalComputersService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(personalComputersController).build();
    }

    @Test
    void testReturnPC() throws Exception {
        PC personalComputer = getPC();
        BDDMockito.given(personalComputersService.findPCById(personalComputer.getId())).willReturn(personalComputer);
        mockMvc.perform(MockMvcRequestBuilders.get("/PCs/{id}", personalComputer.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testReturnAllPCs() throws Exception {
        BDDMockito.given(personalComputersService.getAllPCs()).willReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/PCs"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreatePC() throws Exception {
        PC personalComputer = getPC();
        BDDMockito.given(personalComputersService.createPC(personalComputer)).willReturn(personalComputer);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonProduct = objectMapper.writeValueAsString(personalComputer).replaceAll("\"id\":1,", "");
        mockMvc.perform(MockMvcRequestBuilders.post("/PCs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonProduct)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdatePC() throws Exception {
        PC personalComputer = getPC();
        personalComputer.setVendor("IBM");
        BDDMockito.given(personalComputersService.updatePC(personalComputer)).willReturn(personalComputer);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonProduct = objectMapper.writeValueAsString(personalComputer).replaceAll("\"id\":1,", "");
        mockMvc.perform(MockMvcRequestBuilders.patch("/PCs/{id}", personalComputer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonProduct)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    private PC getPC() {
        PC personalComputer = new PC();
        personalComputer.setId(1L);
        personalComputer.setSerialNumber("a1fa2fc2-0194-11ee-be56-0242ac120002");
        personalComputer.setVendor("Apple");
        personalComputer.setPrice(BigDecimal.valueOf(264200.00).setScale(2, RoundingMode.HALF_DOWN));
        personalComputer.setQuantity(12L);
        personalComputer.setFormFactor("desktop");
        return personalComputer;
    }
}