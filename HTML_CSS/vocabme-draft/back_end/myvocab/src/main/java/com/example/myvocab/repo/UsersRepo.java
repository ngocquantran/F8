package com.example.myvocab.repo;

import com.example.myvocab.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsersRepo extends JpaRepository<Users, String> {

}