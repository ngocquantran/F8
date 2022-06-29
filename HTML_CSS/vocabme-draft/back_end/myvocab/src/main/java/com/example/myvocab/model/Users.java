package com.example.myvocab.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class Users {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String avatar;
    private String userName;
    private String email;
    private String password;
    private Date birth;
    private Date startDate;


}
