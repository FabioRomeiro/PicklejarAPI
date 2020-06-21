package com.romeiro.picklejar.repository;

import com.romeiro.picklejar.model.Password;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PasswordRepository extends JpaRepository<Password, Integer> {

    @Query("SELECT p FROM Password p WHERE CONCAT(p.name, p.username, p.link, p.status) LIKE '%:text%' AND p.user.id = :userId")
    Page<Password> findByAnyText(@Param("userId") Integer userId, @Param("text") String text, Pageable pageable);

    @Query("SELECT p FROM Password p WHERE p.favorite = :favorite AND p.user.id = :userId")
    Page<Password> findByFavorite(@Param("userId") Integer userId, @Param("favorite") boolean favorite, Pageable pageable);

    Page<Password> findByUserId(Integer userId, Pageable pageable);
}
