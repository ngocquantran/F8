package com.example.myvocab.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Roles {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;


}
