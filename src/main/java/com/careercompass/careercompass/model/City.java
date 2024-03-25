package com.careercompass.careercompass.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @ManyToMany(
            mappedBy = "cities"
    )
    private List<CompanyDetails> companies;
}
