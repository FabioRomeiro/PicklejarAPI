package com.romeiro.picklejar.repository;

import com.romeiro.picklejar.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PasswordRepository extends JpaRepository<Password, Integer> {

    List<Password> findByUserId(Integer id);

    @Query("SELECT p FROM Password p WHERE CONCAT(p.name, p.username, p.link, p.status) LIKE '%:text%'")
    List<Password> findByAnyText(@Param("text") String text);
}
