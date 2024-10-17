package com.peperombti.peperombti.service;

import com.peperombti.peperombti.domain.ParticipantCount;
import com.peperombti.peperombti.domain.Question;
import com.peperombti.peperombti.dto.OptionDto;
import com.peperombti.peperombti.dto.request.ResultRequestDto;
import com.peperombti.peperombti.dto.response.QuestionResponseDto;
import com.peperombti.peperombti.dto.response.ResultResponseDto;
import com.peperombti.peperombti.repository.ParticipantCountRepository;
import com.peperombti.peperombti.repository.QuestionRepository;
import com.peperombti.peperombti.repository.ResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MbtiService {

    private final QuestionRepository questionRepository;
    private final ResultRepository resultRepository;
    private final ParticipantCountRepository participantCountRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String TOPIC = "mbti_results";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ResponseEntity<? super QuestionResponseDto> getQuestion() {
        List<String> questionList = new ArrayList<>();
        List<OptionDto> option1List = new ArrayList<>();
        List<OptionDto> option2List = new ArrayList<>();

        try {
            List<Question> questions = questionRepository.findAll();

            for (Question question : questions) {
                questionList.add(question.getQuestion());
                option1List.add(new OptionDto(question.getOption1(), question.getOption1_type()));
                option2List.add(new OptionDto(question.getOption2(), question.getOption2_type()));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return QuestionResponseDto.databaseError();
        }
        return QuestionResponseDto.success(questionList, option1List, option2List);
    }

    @Async
    public CompletableFuture<ResponseEntity<? super ResultResponseDto>> calculateResult(ResultRequestDto dto) {
        String mbti;
        try {
            System.out.println("Received E: " + dto.getE());
            System.out.println("Received I: " + dto.getI());
            System.out.println("Received S: " + dto.getS());
            System.out.println("Received N: " + dto.getN());
            System.out.println("Received T: " + dto.getT());
            System.out.println("Received F: " + dto.getF());
            System.out.println("Received J: " + dto.getJ());
            System.out.println("Received P: " + dto.getP());
            mbti = calcMbti(dto.getE(), dto.getI(), dto.getS(), dto.getN(), dto.getT(), dto.getF(), dto.getJ(), dto.getP());

            if (mbti.length() != 4)
                return CompletableFuture.completedFuture(ResultResponseDto.invalidInput());

            cacheParticipantInRedis(mbti);

        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(ResultResponseDto.databaseError());
        }
        return CompletableFuture.completedFuture(ResultResponseDto.success(mbti));
    }

    @Async
    public void cacheParticipantInRedis(String mbti) {
        try {
            //참여자 수 저장
            redisTemplate.opsForValue().increment("participant");

            //mbti 결과 저장
            kafkaTemplate.send(TOPIC, mbti);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String calcMbti(int e, int i, int s, int n, int t, int f, int j, int p) {
        String result = "";
        if (e > i)
            result += "E";
        else if (e < i)
            result += "I";

        if (s > n)
            result += "S";
        else if (s < n)
            result += "N";

        if (t > f)
            result += "T";
        else if (t < f)
            result += "F";

        if (j > p)
            result += "J";
        else if (j < p)
            result += "P";

        System.out.println(result);
        return result;
    }

    @Scheduled(cron = "0 */3 * * * *")
    public void syncParticipantCount() {
        String participantCache = redisTemplate.opsForValue().get("participant");
        Long participantCount = Long.parseLong(participantCache);

        Optional<ParticipantCount> optionalParticipantCount = participantCountRepository.findById(1L);
        if (optionalParticipantCount.isEmpty()) {
            ParticipantCount dbParticipantCount = new ParticipantCount();
            dbParticipantCount.setId(1L);
            dbParticipantCount.setParticipant(participantCount);
            dbParticipantCount.setCreatedAt(LocalDateTime.now());
            dbParticipantCount.setUpdatedAt(LocalDateTime.now());
            participantCountRepository.save(dbParticipantCount);
        } else {
            ParticipantCount dbParticipantCount = optionalParticipantCount.get();
            dbParticipantCount.setParticipant(participantCount);
            dbParticipantCount.setUpdatedAt(LocalDateTime.now());
            participantCountRepository.save(dbParticipantCount);
        }
    }
}
