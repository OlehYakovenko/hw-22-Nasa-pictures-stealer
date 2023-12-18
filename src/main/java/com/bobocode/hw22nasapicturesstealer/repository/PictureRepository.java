package com.bobocode.hw22nasapicturesstealer.repository;

import com.bobocode.hw22nasapicturesstealer.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {

}
