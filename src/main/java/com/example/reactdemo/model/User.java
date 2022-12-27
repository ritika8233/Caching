package com.example.reactdemo.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("users")
public class User {
    @Id
    private int id;

    @NotNull(message = "Name cannot be empty")
    private String username;

//    @Min(value = 10, message = "Required min age is 10")
//    @Max(value = 50, message = "Required max age is 50")
//    private int age;

    @NotNull(message = "Email cannot be empty")
    private String email;
}
