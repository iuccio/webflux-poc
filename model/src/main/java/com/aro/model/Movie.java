package com.aro.model;

import lombok.*;

@ToString
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
  private String id;
  private String title;
}