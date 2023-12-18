package com.bobocode.hw22nasapicturesstealer.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Photo {
  private String id;
  private String imgSrc;
  private CameraInfo camera;
}
