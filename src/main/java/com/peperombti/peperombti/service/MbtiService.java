package com.peperombti.peperombti.service;

import com.peperombti.peperombti.domain.Question;
import com.peperombti.peperombti.dto.response.OptionDto;
import com.peperombti.peperombti.dto.response.QuestionResponseDto;
import com.peperombti.peperombti.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MbtiService {

    private final QuestionRepository questionRepository;

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
}
