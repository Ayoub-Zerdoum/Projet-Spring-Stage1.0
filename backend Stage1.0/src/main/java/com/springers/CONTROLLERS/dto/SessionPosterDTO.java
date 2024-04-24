package com.springers.CONTROLLERS.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SessionPosterDTO {

    Long id;
    int classroom;

    LocalDateTime date;

    private List<String> profNames;

}
