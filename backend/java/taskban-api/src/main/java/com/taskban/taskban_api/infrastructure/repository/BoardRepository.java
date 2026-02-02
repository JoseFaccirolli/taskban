package com.taskban.taskban_api.infrastructure.repository;

import com.taskban.taskban_api.infrastructure.entities.Board;
import com.taskban.taskban_api.infrastructure.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    @Query("SELECT board FROM Board board JOIN FETCH board.user user WHERE board.id = :id")
    Optional<Board> findByIdWithUser(@Param("id") Integer id);

    @Query("""
    SELECT b 
    FROM Board b 
    JOIN FETCH b.user u 
    WHERE u.id = :userId
    """)
    List<Board> findAllByUserWithUser(@Param("userId") Integer userId);

}
