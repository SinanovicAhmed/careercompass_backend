package com.careercompass.careercompass.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "company_details")
public class CompanyDetails {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String slogan;

    private String about;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "number_of_employees")
    private Integer numberOfEmployees;

    @ManyToMany
    @JoinTable(
            name = "company_city",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "city_id")
    )
    private List<City> cities;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
