package com.springers.CONTROLLERS.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PosterDTO {

    @NotEmpty
    private String title;

    @NotEmpty
    private String pdfPath;

    private Double finalRating;

    private Long stageId;

    private Long sessionPosterId;
}