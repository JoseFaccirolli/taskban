package com.taskban.taskban_api.repository;

import com.taskban.taskban_api.entity.Card;
import com.taskban.taskban_api.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByStatus(Status status);
    List<Card> findByTitleContainingIgnoreCase(String title);
}
