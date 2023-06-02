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
import ru.easybot.testTusk.models.entities.HardDisk;
import ru.easybot.testTusk.services.HardDisksService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HardDisksControllerTest {
    MockMvc mockMvc;

    @MockBean
    private HardDisksController hardDisksController;

    @MockBean
    private HardDisksService hardDisksService;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(hardDisksController).build();
    }

    @Test
    void testReturnHardDisk() throws Exception {
        HardDisk hardDisk = getHardDisk();
        BDDMockito.given(hardDisksService.findHardDiskById(hardDisk.getId())).willReturn(hardDisk);
        mockMvc.perform(MockMvcRequestBuilders.get("/hardDisks/{id}", hardDisk.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testReturnAllHardDisks() throws Exception {
        BDDMockito.given(hardDisksService.getAllHardDisks()).willReturn(new ArrayList<>());
        mockMvc.perform(MockMvcRequestBuilders.get("/hardDisks"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateHardDisk() throws Exception {
        HardDisk hardDisk = getHardDisk();
        BDDMockito.given(hardDisksService.createHardDisk(hardDisk)).willReturn(hardDisk);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonProduct = objectMapper.writeValueAsString(hardDisk).replaceAll("\"id\":1,", "");
        mockMvc.perform(MockMvcRequestBuilders.post("/hardDisks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonProduct)
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateHardDisk() throws Exception {
        HardDisk hardDisk = getHardDisk();
        hardDisk.setVendor("Western Digital");
        BDDMockito.given(hardDisksService.updateHardDisk(hardDisk)).willReturn(hardDisk);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonProduct = objectMapper.writeValueAsString(hardDisk).replaceAll("\"id\":1,", "");
        mockMvc.perform(MockMvcRequestBuilders.patch("/hardDisks/{id}", hardDisk.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(jsonProduct)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk());
    }

    private HardDisk getHardDisk() {
        HardDisk hardDisk = new HardDisk();
        hardDisk.setId(1L);
        hardDisk.setSerialNumber("143579050001015400CA");
        hardDisk.setVendor("PLEXTOR");
        hardDisk.setPrice(BigDecimal.valueOf(5600.00).setScale(2, RoundingMode.HALF_DOWN));
        hardDisk.setQuantity(5L);
        hardDisk.setCapacity(512L);
        return hardDisk;
    }
}
