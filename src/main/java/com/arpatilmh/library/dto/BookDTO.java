package com.arpatilmh.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    UUID id;

    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    String title;

    @NotBlank(message = "Author cannot be blank")
    @Size(max = 100, message = "Author name cannot exceed 100 characters")
    String author;

    @NotBlank(message = "ISBN cannot be blank")
    @Size(min = 10, max = 13, message = "ISBN must be between 10 and 13 characters")
    String isbn;

    @NotNull(message = "Publication year cannot be null")
    @Positive(message = "Publication year must be positive")
    Integer year;
}
