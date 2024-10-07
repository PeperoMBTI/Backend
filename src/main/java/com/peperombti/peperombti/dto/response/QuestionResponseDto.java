package com.peperombti.peperombti.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class QuestionResponseDto extends ResponseDto{

    private List<String> question;
    private List<OptionDto> option1;
    private List<OptionDto> option2;

    private QuestionResponseDto(List<String> question, List<OptionDto> option1, List<OptionDto> option2) {
        super();
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
    }

    public static ResponseEntity<QuestionResponseDto> success(List<String> question, List<OptionDto> option1, List<OptionDto> option2) {
        QuestionResponseDto responseBody = new QuestionResponseDto(question, option1, option2);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
