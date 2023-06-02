package ru.easybot.testTusk.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@BatchSize(size = 10)
public class PC extends Product {
    @Pattern(regexp = "^(desktop|nettop|monoblock)$", message = "form factor can contain value only in this range (desktop, nettop, monoblock)")
    private String formFactor;
}
