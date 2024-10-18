package com.peperombti.peperombti.dto.response;

import com.peperombti.peperombti.common.ResponseCode;
import com.peperombti.peperombti.common.ResponseMessage;
import com.peperombti.peperombti.dto.OptionDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class ResultResponseDto extends ResponseDto{
    private String mbti;

    private ResultResponseDto(String mbti) {
        super();
        this.mbti = mbti;
    }

    public static ResponseEntity<ResultResponseDto> success(String mbti) {
        ResultResponseDto responseBody = new ResultResponseDto(mbti);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    public static ResponseEntity<ResponseDto> invalidInput() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.INVALID_INPUT, ResponseMessage.INVALID_INPUT);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
