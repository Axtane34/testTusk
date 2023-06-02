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
public class Laptop extends Product{
    @Pattern(regexp = "^(1[3-5]|17)(in)$", message = "laptop size can contain value only in this range (13in, 14in, 15in, 17in")
    private String laptopSize;
}
