package com.romeiro.picklejar.repository;

import com.romeiro.picklejar.model.Log;
import com.romeiro.picklejar.model.LogType;
import com.romeiro.picklejar.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Integer> {

    @Query("SELECT l FROM Log l WHERE l.type = :logType AND l.user.id = :userId")
    List<Log> findByType(@Param("userId") Integer userId, LogType logType);

    @Query("SELECT l FROM Log l WHERE l.password.id = :passwordId AND l.type = :logType AND l.user.id = :userId")
    List<Log> findByTypeAndPasswordId(@Param("userId") Integer userId, @Param("logType") LogType logType, @Param("passwordId") Integer passwordId);

    List<Log> findByUserId(Integer userId);
}
