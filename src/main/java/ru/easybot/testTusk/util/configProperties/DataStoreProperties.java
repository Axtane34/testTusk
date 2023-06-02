package ru.easybot.testTusk.util.configProperties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Переменная среда из application.yaml файла с префиксом db, содержит данные для подключения к БД
 */
@Data
@Component
@ConfigurationProperties(prefix = "db")
public class DataStoreProperties {
    private String driver;
    private String url;
    private String username;
    private String password;
}
