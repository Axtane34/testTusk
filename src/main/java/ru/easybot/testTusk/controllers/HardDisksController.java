package ru.easybot.testTusk.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.easybot.testTusk.models.entities.HardDisk;
import ru.easybot.testTusk.models.responseEntities.ApiResponse;
import ru.easybot.testTusk.services.HardDisksService;
import ru.easybot.testTusk.util.exceptions.hardDisk.HardDiskNotCreatedException;
import ru.easybot.testTusk.util.exceptions.hardDisk.HardDiskNotUpdatedException;
import ru.easybot.testTusk.util.utils.ExceptionFiller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/hardDisks")
@Validated
public class HardDisksController {
    private final HardDisksService hardDisksService;
    private static final Logger logger = LoggerFactory.getLogger(HardDisksController.class);

    @Autowired
    public HardDisksController(HardDisksService hardDisksService) {
        this.hardDisksService = hardDisksService;
    }

    /**
     * Метод возвращает все существующие в БД жесткие диски
     */

    @GetMapping
    public ResponseEntity<ApiResponse> getAllHardDisks(){
        List<HardDisk> resultList = hardDisksService.getAllHardDisks();
        ApiResponse apiResponse = ApiResponse.getDefaultOKWithResult(resultList);
        if (resultList.isEmpty())
            apiResponse.setMessage("Operation complete, but result list of hard disks is empty");
        logger.info(apiResponse.toString());
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Метод возвращает жесткий диск по id, передаваемому в пути запроса
     */

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getHardDiskById(@PathVariable Long id){
        ApiResponse apiResponse = ApiResponse.getDefaultOKWithResult(hardDisksService.findHardDiskById(id));
        logger.info(apiResponse.toString());
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Метод сохраняет жесткий диск в БД и возвращает его в ответе.
     * json для проверки: {
     *     "serialNumber": "143579050001015400CA",
     *     "vendor": "PLEXTOR",
     *     "price": 5600.00,
     *     "quantity": 5,
     *     "capacity": 512
     * }
     */

    @PostMapping
    public ResponseEntity<ApiResponse> saveHardDisk(@RequestBody @Valid HardDisk hardDisk, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new HardDiskNotCreatedException(ExceptionFiller.enrichErrorMessage(bindingResult));
        }
        ApiResponse apiResponse = ApiResponse.getDefaultCreatedWithResult(hardDisksService.createHardDisk(hardDisk));
        logger.info(apiResponse.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /**
     * Метод редактирует существующий жесткий диск по id переданному в пути запроса
     * json для проверки: {
     *     "serialNumber": "WD-WCAY01763080",
     *     "vendor": "Western Digital",
     *     "price": 2600.00,
     *     "quantity": 2,
     *     "capacity": 256
     * }
     */

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateHardDiskById(@PathVariable @Positive(message = "id should be greater then 0") Long id,
                                                        @RequestBody @Valid HardDisk hardDisk,
                                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new HardDiskNotUpdatedException(ExceptionFiller.enrichErrorMessage(bindingResult));
        }
        hardDisk.setId(id);
        ApiResponse apiResponse = ApiResponse.getDefaultOKWithResult(hardDisksService.updateHardDisk(hardDisk));
        logger.info(apiResponse.toString());
        return ResponseEntity.ok(apiResponse);
    }
}
