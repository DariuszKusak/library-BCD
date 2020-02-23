package com.library.bcd.librarybcd.repository;

import com.library.bcd.librarybcd.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

        Optional<Authority> findByLogin(String login);
}
