package ru.easybot.testTusk.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.easybot.testTusk.models.entities.Laptop;
import ru.easybot.testTusk.models.responseEntities.ApiResponse;
import ru.easybot.testTusk.services.LaptopsService;
import ru.easybot.testTusk.util.exceptions.laptop.LaptopNotCreatedException;
import ru.easybot.testTusk.util.exceptions.laptop.LaptopNotUpdatedException;
import ru.easybot.testTusk.util.utils.ExceptionFiller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/laptops")
@Validated
public class LaptopsController {
    private final LaptopsService laptopsService;
    private static final Logger logger = LoggerFactory.getLogger(LaptopsController.class);

    @Autowired
    public LaptopsController(LaptopsService laptopsService) {
        this.laptopsService = laptopsService;
    }

    /**
     * Метод возвращает все существующие в БД ноутбуки
     */

    @GetMapping
    public ResponseEntity<ApiResponse> getAllLaptops() {
        List<Laptop> resultList = laptopsService.getAllLaptops();
        ApiResponse apiResponse = ApiResponse.getDefaultOKWithResult(resultList);
        if (resultList.isEmpty())
            apiResponse.setMessage("Operation complete, but result list of laptops is empty");
        logger.info(apiResponse.toString());
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Метод возвращает ноутбук по id, передаваемому в пути запроса
     */

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getLaptopById(@PathVariable Long id) {
        ApiResponse apiResponse = ApiResponse.getDefaultOKWithResult(laptopsService.findLaptopById(id));
        logger.info(apiResponse.toString());
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Метод сохраняет ноутбук в БД и возвращает его в ответе.
     * json для проверки: {
     *      "serialNumber": "eac292c4-016e-11ee-be56-0242ac120002",
     *      "vendor": "Intel",
     *      "price": 82600.00,
     *      "quantity": 2,
     *      "formFactor": "desktop"
     * }
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse> saveLaptop(@RequestBody @Valid Laptop laptop, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new LaptopNotCreatedException(ExceptionFiller.enrichErrorMessage(bindingResult));
        }
        ApiResponse apiResponse = ApiResponse.getDefaultCreatedWithResult(laptopsService.createLaptop(laptop));
        logger.info(apiResponse.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /**
     * Метод редактирует существующий ноутбук по id переданному в пути запроса
     * json для проверки: {
     *      "serialNumber": "3696b4fa-016f-11ee-be56-0242ac120002",
     *      "vendor": "Apple",
     *      "price": 164200.00,
     *      "quantity": 12,
     *      "formFactor": "monoblock"
      }
     */

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateLaptopById(@PathVariable @Positive(message = "id should be greater then 0") Long id,
                                                        @RequestBody @Valid Laptop laptop,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new LaptopNotUpdatedException(ExceptionFiller.enrichErrorMessage(bindingResult));
        }
        laptop.setId(id);
        ApiResponse apiResponse = ApiResponse.getDefaultOKWithResult(laptopsService.updateLaptop(laptop));
        logger.info(apiResponse.toString());
        return ResponseEntity.ok(apiResponse);
    }
}
