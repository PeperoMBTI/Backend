package com.peperombti.peperombti.service;

import com.peperombti.peperombti.domain.Result;
import com.peperombti.peperombti.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {
    private List<String> mbtiResults = new ArrayList<>();
    private final ResultRepository resultRepository;

    @KafkaListener(topics = "mbti_results", groupId = "mbti_group")
    public void consume(String mbti) {

        mbtiResults.add(mbti);

        if (mbtiResults.size() >= 10) {
            saveToDatabase(mbtiResults);
            mbtiResults.clear();
        }
    }

    private void saveToDatabase(List<String> mbtiResults) {
        List<Result> entities = new ArrayList<>();
        for (String result : mbtiResults) {
            Result entity = new Result();
            entity.setMbti(result);
            entities.add(entity);
        }

        resultRepository.saveAll(entities);
    }
}
