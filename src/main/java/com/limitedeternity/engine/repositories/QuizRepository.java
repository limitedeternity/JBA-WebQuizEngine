package com.limitedeternity.engine.repositories;

import com.limitedeternity.engine.models.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<QuizQuestion, Long> { }