package ru.easybot.testTusk.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.easybot.testTusk.models.entities.Display;
import ru.easybot.testTusk.models.responseEntities.ApiResponse;
import ru.easybot.testTusk.services.DisplaysService;
import ru.easybot.testTusk.util.exceptions.display.DisplayNotCreatedException;
import ru.easybot.testTusk.util.exceptions.display.DisplayNotUpdatedException;
import ru.easybot.testTusk.util.utils.ExceptionFiller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/displays")
@Validated
public class DisplaysController {
    private final DisplaysService displaysService;
    private static final Logger logger = LoggerFactory.getLogger(DisplaysController.class);

    @Autowired
    public DisplaysController(DisplaysService displaysService) {
        this.displaysService = displaysService;
    }

    /**
     * Метод возвращает все существующие в БД мониторы
     */

    @GetMapping
    public ResponseEntity<ApiResponse> getAllDisplays(){
        List<Display> resultList = displaysService.getAllDisplays();
        ApiResponse apiResponse = ApiResponse.getDefaultOKWithResult(resultList);
        if (resultList.isEmpty())
            apiResponse.setMessage("Operation complete, but result list of displays is empty");
        logger.info(apiResponse.toString());
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Метод возвращает монитор по id передаваемому в пути запроса
     */

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getDisplayById(@PathVariable Long id){
        ApiResponse apiResponse = ApiResponse.getDefaultOKWithResult(displaysService.findDisplayById(id));
        logger.info(apiResponse.toString());
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Метод сохраняет монитор в БД и возвращает его в ответе.
     * json для проверки: {
     *     "serialNumber": "4d36e96e-e325-11ce-bfc1-08002be103180007",
     *     "vendor": "Philips",
     *     "price": 25600.00,
     *     "quantity": 25,
     *     "diagonal": 32
     * }
     */

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse> saveDisplay(@RequestBody @Valid Display display, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DisplayNotCreatedException(ExceptionFiller.enrichErrorMessage(bindingResult));
        }
        ApiResponse apiResponse = ApiResponse.getDefaultCreatedWithResult(displaysService.createDisplay(display));
        logger.info(apiResponse.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /**
     * Метод редактирует существующий монитор по id переданному в пути запроса
     * json для проверки: {
     *     "serialNumber": "4d36e96e-e325-11ce-bfc1-08002be103180007",
     *     "vendor": "LG",
     *     "price": 15600.00,
     *     "quantity": 12,
     *     "diagonal": 27
     * }
     */

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateDisplayById(@PathVariable @Positive(message = "id should be greater then 0") Long id,
                                                        @RequestBody @Valid Display display,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new DisplayNotUpdatedException(ExceptionFiller.enrichErrorMessage(bindingResult));
        }
        display.setId(id);
        ApiResponse apiResponse = ApiResponse.getDefaultOKWithResult(displaysService.updateDisplay(display));
        logger.info(apiResponse.toString());
        return ResponseEntity.ok(apiResponse);
    }
}
