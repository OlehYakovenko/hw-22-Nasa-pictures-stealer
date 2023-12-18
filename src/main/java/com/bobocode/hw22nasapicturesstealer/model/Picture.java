package com.bobocode.hw22nasapicturesstealer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "pictures")
@Getter
@Setter
public class Picture {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long nasaId;

  private String imgSrc;

  @CreationTimestamp
  private LocalDate createdAt;

  @ManyToOne
  @JoinColumn(name = "camera_id")
  private Camera camera;

}
