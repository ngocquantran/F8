package com.example.myvocab.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "package")
public class Package {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int duration;
    private int price;
    private String description;


}
