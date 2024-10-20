package com.peperombti.peperombti.dto.response;

import com.peperombti.peperombti.common.ResponseCode;
import com.peperombti.peperombti.common.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDto {
    private String responseCode;
    private String responseMessage;

    public ResponseDto() {
        this.responseCode = ResponseCode.SUCCESS;
        this.responseMessage = ResponseMessage.SUCCESS;
    }

    public static ResponseEntity<ResponseDto> databaseError() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
}
