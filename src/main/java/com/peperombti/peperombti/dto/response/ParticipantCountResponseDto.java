package com.peperombti.peperombti.dto.response;

import com.peperombti.peperombti.dto.OptionDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ParticipantCountResponseDto extends ResponseDto{
    private Long participants;

    private ParticipantCountResponseDto(Long participants) {
        super();
        this.participants = participants;
    }

    public static ResponseEntity<ParticipantCountResponseDto> success(Long participants) {
        ParticipantCountResponseDto responseBody = new ParticipantCountResponseDto(participants);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}
