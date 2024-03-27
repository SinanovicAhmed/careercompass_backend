package com.careercompass.careercompass.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
