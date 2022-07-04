package com.example.myvocab.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    private String id;
    private String avatar;
    private String userName;
    private String email;
    private String password;
    private LocalDate birth;
    private LocalDate startDate;


//    @GeneratedValue(generator = "uuid2")
//    @GenericGenerator(name = "uuid2", strategy = "uuid2")
//@Type(type = "uuid-char")

//    @OneToMany(cascade = CascadeType.PERSIST, orphanRemoval = true)
//    @JoinColumn(name = "id_user")
//    private Set<Orders> orders = new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_user")
    private Set<UserRole> userRoles = new HashSet<>();


    @PrePersist
    public void prePersist() {
        startDate=LocalDate.now();
    }
}
