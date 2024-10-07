package com.peperombti.peperombti.controller;

import com.peperombti.peperombti.dto.response.QuestionResponseDto;
import com.peperombti.peperombti.service.MbtiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MbtiController {
    private final MbtiService mbtiService;

    @GetMapping("/question")
    public ResponseEntity<? super QuestionResponseDto> getQuestion() {
        ResponseEntity<? super QuestionResponseDto> response = mbtiService.getQuestion();
        return response;
    }
}
