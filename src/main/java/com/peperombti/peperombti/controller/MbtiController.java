package com.peperombti.peperombti.controller;

import com.peperombti.peperombti.dto.request.ResultRequestDto;
import com.peperombti.peperombti.dto.response.QuestionResponseDto;
import com.peperombti.peperombti.dto.response.ResultResponseDto;
import com.peperombti.peperombti.service.MbtiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class MbtiController {
    private final MbtiService mbtiService;

    @GetMapping("/question")
    public ResponseEntity<? super QuestionResponseDto> getQuestion() {
        ResponseEntity<? super QuestionResponseDto> response = mbtiService.getQuestion();
        return response;
    }

    @PostMapping("/result")
    public CompletableFuture<ResponseEntity<? super ResultResponseDto>> calculateMbtiResult(@RequestBody ResultRequestDto dto) {
        CompletableFuture<ResponseEntity<? super ResultResponseDto>> response = mbtiService.calculateResult(dto);
        return response;
    }
}
