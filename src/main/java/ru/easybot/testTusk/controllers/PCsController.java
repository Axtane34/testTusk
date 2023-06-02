package ru.easybot.testTusk.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.easybot.testTusk.models.entities.PC;
import ru.easybot.testTusk.models.responseEntities.ApiResponse;
import ru.easybot.testTusk.services.PCsService;
import ru.easybot.testTusk.util.exceptions.PC.PCNotCreatedException;
import ru.easybot.testTusk.util.exceptions.PC.PCNotUpdatedException;
import ru.easybot.testTusk.util.utils.ExceptionFiller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/PCs")
@Validated
public class PCsController {
    private final PCsService personalComputersService;
    private static final Logger logger = LoggerFactory.getLogger(PCsController.class);

    @Autowired
    public PCsController(PCsService personalComputersService) {
        this.personalComputersService = personalComputersService;
    }

    /**
     * Метод возвращает все существующие в БД ПК
     */

    @GetMapping
    public ResponseEntity<ApiResponse> getAllPCs(){
        List<PC> resultList = personalComputersService.getAllPCs();
        ApiResponse apiResponse = ApiResponse.getDefaultOKWithResult(resultList);
        if (resultList.isEmpty())
            apiResponse.setMessage("Operation complete, but result list of PCs is empty");
        logger.info(apiResponse.toString());
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Метод возвращает ПК по id, передаваемому в пути запроса
     */

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getPCById(@PathVariable Long id){
        ApiResponse apiResponse = ApiResponse.getDefaultOKWithResult(personalComputersService.findPCById(id));
        logger.info(apiResponse.toString());
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Метод сохраняет ПК в БД и возвращает его в ответе.
     * json для проверки: {
     *     "serialNumber": "a1fa2fc2-0194-11ee-be56-0242ac120002",
     *     "vendor": "Intel",
     *     "price": 250800.00,
     *     "quantity": 2,
     *     "formFactor": "display"
     * }
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse> savePC(@RequestBody @Valid PC pc, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new PCNotCreatedException(ExceptionFiller.enrichErrorMessage(bindingResult));
        }
        ApiResponse apiResponse = ApiResponse.getDefaultCreatedWithResult(personalComputersService.createPC(pc));
        logger.info(apiResponse.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /**
     * Метод редактирует существующий ПК по id переданному в пути запроса
     * json для проверки: {
     *     "serialNumber": "a1fa2fc2-0194-11ee-be56-0242ac120002",
     *     "vendor": "Gigabyte",
     *     "price": 82600.00,
     *     "quantity": 12,
     *     "formFactor": "monoblock"
     * }
     */

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updatePCById(@PathVariable @Positive(message = "id should be greater then 0") Long id,
                                                        @RequestBody @Valid PC pc,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new PCNotUpdatedException(ExceptionFiller.enrichErrorMessage(bindingResult));
        }
        pc.setId(id);
        ApiResponse apiResponse = ApiResponse.getDefaultOKWithResult(personalComputersService.updatePC(pc));
        logger.info(apiResponse.toString());
        return ResponseEntity.ok(apiResponse);
    }
}
