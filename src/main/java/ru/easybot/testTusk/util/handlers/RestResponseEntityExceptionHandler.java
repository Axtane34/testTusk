package ru.easybot.testTusk.util.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.easybot.testTusk.models.responseEntities.ApiResponse;
import ru.easybot.testTusk.util.exceptions.PC.PCNotCreatedException;
import ru.easybot.testTusk.util.exceptions.PC.PCNotFoundException;
import ru.easybot.testTusk.util.exceptions.PC.PCNotUpdatedException;
import ru.easybot.testTusk.util.exceptions.display.DisplayNotCreatedException;
import ru.easybot.testTusk.util.exceptions.display.DisplayNotFoundException;
import ru.easybot.testTusk.util.exceptions.display.DisplayNotUpdatedException;
import ru.easybot.testTusk.util.exceptions.hardDisk.HardDiskNotCreatedException;
import ru.easybot.testTusk.util.exceptions.hardDisk.HardDiskNotFoundException;
import ru.easybot.testTusk.util.exceptions.hardDisk.HardDiskNotUpdatedException;
import ru.easybot.testTusk.util.exceptions.laptop.LaptopNotCreatedException;
import ru.easybot.testTusk.util.exceptions.laptop.LaptopNotFoundException;
import ru.easybot.testTusk.util.exceptions.laptop.LaptopNotUpdatedException;
import ru.easybot.testTusk.util.utils.ExceptionFiller;

import javax.validation.ConstraintViolationException;


/**
 * Этот класс является глобальным обработчиком кастомных исключений. В своей структуре использует объект ApiResponse,
 * и методы util интерфейса ExceptionFiller, тут же логируются ответы при исключениях
 */
@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler
    private ResponseEntity<ApiResponse> handleException(DisplayNotFoundException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.NOT_FOUND.toString(),
                e.getMessage(),
                ExceptionFiller.setMapBasedOnException(e)
        );
        logger.error(response.toString());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //404
    }

    @ExceptionHandler
    private ResponseEntity<ApiResponse> handleException(HardDiskNotFoundException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.NOT_FOUND.toString(),
                e.getMessage(),
                ExceptionFiller.setMapBasedOnException(e)
        );
        logger.error(response.toString());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //404
    }

    @ExceptionHandler
    private ResponseEntity<ApiResponse> handleException(LaptopNotFoundException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.NOT_FOUND.toString(),
                e.getMessage(),
                ExceptionFiller.setMapBasedOnException(e)
        );
        logger.error(response.toString());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //404
    }

    @ExceptionHandler
    private ResponseEntity<ApiResponse> handleException(PCNotFoundException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.NOT_FOUND.toString(),
                e.getMessage(),
                ExceptionFiller.setMapBasedOnException(e)
        );
        logger.error(response.toString());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); //404
    }

    @ExceptionHandler
    private ResponseEntity<ApiResponse> handleException(DisplayNotCreatedException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.BAD_REQUEST.toString(),
                e.getMessage(),
                ExceptionFiller.setMapBasedOnException(e)
        );
        logger.error(response.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //400
    }

    @ExceptionHandler
    private ResponseEntity<ApiResponse> handleException(HardDiskNotCreatedException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.BAD_REQUEST.toString(),
                e.getMessage(),
                ExceptionFiller.setMapBasedOnException(e)
        );
        logger.error(response.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //400
    }

    @ExceptionHandler
    private ResponseEntity<ApiResponse> handleException(LaptopNotCreatedException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.BAD_REQUEST.toString(),
                e.getMessage(),
                ExceptionFiller.setMapBasedOnException(e)
        );
        logger.error(response.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //400
    }

    @ExceptionHandler
    private ResponseEntity<ApiResponse> handleException(PCNotCreatedException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.BAD_REQUEST.toString(),
                e.getMessage(),
                ExceptionFiller.setMapBasedOnException(e)
        );
        logger.error(response.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //400
    }

    @ExceptionHandler
    private ResponseEntity<ApiResponse> handleException(DisplayNotUpdatedException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.BAD_REQUEST.toString(),
                e.getMessage(),
                ExceptionFiller.setMapBasedOnException(e)
        );
        logger.error(response.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //400
    }

    @ExceptionHandler
    private ResponseEntity<ApiResponse> handleException(HardDiskNotUpdatedException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.BAD_REQUEST.toString(),
                e.getMessage(),
                ExceptionFiller.setMapBasedOnException(e)
        );
        logger.error(response.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //400
    }

    @ExceptionHandler
    private ResponseEntity<ApiResponse> handleException(LaptopNotUpdatedException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.BAD_REQUEST.toString(),
                e.getMessage(),
                ExceptionFiller.setMapBasedOnException(e)
        );
        logger.error(response.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //400
    }

    @ExceptionHandler
    private ResponseEntity<ApiResponse> handleException(PCNotUpdatedException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.BAD_REQUEST.toString(),
                e.getMessage(),
                ExceptionFiller.setMapBasedOnException(e)
        );
        logger.error(response.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //400
    }

    @ExceptionHandler
    public ResponseEntity<ApiResponse> handleException(ConstraintViolationException e) {
        ApiResponse response = new ApiResponse(
                HttpStatus.BAD_REQUEST.toString(),
                e.getMessage(),
                ExceptionFiller.setMapBasedOnException(e)
        );
        logger.error(response.toString());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); //400
    }
}
