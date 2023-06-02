package ru.easybot.testTusk.models.responseEntities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * Это универсальный объект ответа, содержать в себе поля String status, String message, Object result.
 * Где status это HttpStatus код, message сообщение о результате операции, а result это Object параметр
 * который может хранить практически что угодно, от stackTrace объекта или ресурса до простого дополнительного String описания
 * пример стандартных ответов на одном маршруте:
 * {
 *     "status": "200 OK",
 *     "message": "Operation complete, but no counters attach to this account",
 *     "result": []
 * }
 * {
 *     "status": "200 OK",
 *     "message": "Operation complete",
 *     "result": {
 *         "surname": "Афанасьев",
 *         "name": "Сергей",
 *         "secondName": "Николаевич",
 *         "contragents": [],
 *         "house": {
 *             "n_house": 85490,
 *             "address": "г.МИХАЙЛОВСК ул.ЛЕНИНА д. 191/1"
 *         },
 *         "nlic": 300000
 *     }
 * }
 * {
 *     "status": "404 NOT_FOUND",
 *     "message": "account with this lic number does not exist",
 *     "result": {
 *         "exception": "LicNotFoundException",
 *         "line": "LicsService: lambda$findByNLic$0: 29"
 *     }
 * }
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String status;
    private String message;
    private Object result;

    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String convertToJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper.writeValueAsString(this);
    }

    @Override
    public String toString() {
        try {
            return "\nresponse: "
                    + convertToJson()
                    .replaceAll(":\\[", ":[\n").replaceAll(",\"", ",\n\"")
                    .replaceAll("]", "\n]").replaceAll("\\{", "{\n")
                    .replaceAll("}", "\n}").replaceAll(",\\{", ",\n{");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static ApiResponse getDefaultOK(){
        return new ApiResponse(HttpStatus.OK.toString(), "Operation complete");
    }

    public static ApiResponse getDefaultOKWithResult(Object result){
        return new ApiResponse(HttpStatus.OK.toString(), "Operation complete", result);
    }

    public static ApiResponse getDefaultCreated() {
        return new ApiResponse(HttpStatus.CREATED.toString(), "Operation complete");
    }

    public static ApiResponse getDefaultCreatedWithResult(Object result){
        return new ApiResponse(HttpStatus.CREATED.toString(), "Operation complete", result);
    }
}