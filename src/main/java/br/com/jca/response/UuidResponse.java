package br.com.jca.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@NoArgsConstructor
@Getter
@Setter
public class UuidResponse {

    private String uuid;
    private ZonedDateTime timestamp;
    private LocalDateTime dataTimeNow;
    private Timestamp timestampUtc;
    private LocalDateTime dateTimeUtc;



}
