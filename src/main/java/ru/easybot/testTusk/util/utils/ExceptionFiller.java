package ru.easybot.testTusk.util.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;

/**
 * Служебный util интерфейс наполняющий ошибки контекстом
 */
public interface ExceptionFiller {

    /**
     * Внутренний метод получающий строку из массива StackTraceElement[] в которой произошла ошибка уровня приложения
     * @param stackTrace StackTraceElement[] полученный из исключения
     * @return возвращает строку ошибки в коде в таком виде: "line": "LaptopsService: lambda$findByID$0: 29"
     */
    static String getLineFromStackTrace(StackTraceElement[] stackTrace) {
        StackTraceElement element = stackTrace[0];
        String [] name = element.getClassName().split("\\.");
        return name[name.length-1] + ": " + element.getMethodName() + ": " + element.getLineNumber();
    }

    /**
     * Метод добавляет в объект result название класса исключения
     * @param ex переданное исключение
     * @return возвращает название исключение в объекте map
     */
    static Map<String, String> setMapBasedOnException(Throwable ex) {
        return Map.of("exception", ex.getClass().getSimpleName(),
                "line", getLineFromStackTrace(ex.getStackTrace()));
    }

    /**
     * Метод для наполнения errorMessage из полей объекта bindingResult содержащих ошибки валидации
     * @param bindingResult объект bindingResult содержащий поля с ошибками валидации
     * @return возвращает собранные ошибки строкой
     */
    static String enrichErrorMessage(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            errorMessage.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage())
                    .append(";");
        }
        return errorMessage.toString();
    }
}
