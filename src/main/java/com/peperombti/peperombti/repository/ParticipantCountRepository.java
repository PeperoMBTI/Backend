package com.peperombti.peperombti.repository;

import com.peperombti.peperombti.domain.ParticipantCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantCountRepository extends JpaRepository<ParticipantCount, Long> {
}
