package com.example.myvocab;

import com.example.myvocab.model.Users;
import com.example.myvocab.repo.UsersRepo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTest {
    @Autowired private EntityManager em;
    @Autowired private UsersRepo usersRepo;

    @Test @Transactional
    void save_user() {
        Users user = Users.builder()
                .userName("Quan")
                .email("quan@gmail.com")
                .password("12345678")
                .birth(LocalDate.of(1994, 07, 31))
                .build();
        em.persist(user);
        em.flush();
        List<Users> usersList = usersRepo.findAll();
        usersList.stream().forEach(System.out::println);
        Optional<Users> users1 = usersRepo.findById(user.getId());
        Assertions.assertThat(users1).isPresent();
    }

    @Test
    void save(){

        List<Users> usersList=usersRepo.findAll();
        usersList.stream().forEach(System.out::println);
    }

}
