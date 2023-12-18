package com.bobocode.hw22nasapicturesstealer.controller;

import com.bobocode.hw22nasapicturesstealer.model.NasaRequest;
import com.bobocode.hw22nasapicturesstealer.model.Picture;
import com.bobocode.hw22nasapicturesstealer.service.StealerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pictures/")
public class StealerController {

  private final StealerService stealerService;

  @PostMapping("/steal")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<?> stealPictures(@RequestBody NasaRequest request) {
    try {
      List<Picture> pictures = stealerService.retrievePictures(request);
      return ResponseEntity.ok(pictures);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving pictures");
    }
  }

}
