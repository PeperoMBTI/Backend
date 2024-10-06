package com.peperombti.peperombti.repository;

import com.peperombti.peperombti.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}
