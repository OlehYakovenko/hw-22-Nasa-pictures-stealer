package com.bobocode.hw22nasapicturesstealer.service;

import com.bobocode.hw22nasapicturesstealer.model.Camera;
import com.bobocode.hw22nasapicturesstealer.model.CameraInfo;
import com.bobocode.hw22nasapicturesstealer.model.NasaApiResponse;
import com.bobocode.hw22nasapicturesstealer.model.NasaRequest;
import com.bobocode.hw22nasapicturesstealer.model.Photo;
import com.bobocode.hw22nasapicturesstealer.model.Picture;
import com.bobocode.hw22nasapicturesstealer.repository.CameraRepository;
import com.bobocode.hw22nasapicturesstealer.repository.PictureRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
@Log4j
public class StealerService {

  private final RestTemplate restTemplate;
  private final PictureRepository pictureRepository;
  private final CameraRepository cameraRepository;
  @Value("${nasa.base.url}")
  private String nasaBaseUrl;
  @Value("${nasa.api.key}")
  private String nasaApiKey;

  public List<Picture> retrievePictures(NasaRequest request) {
    var url = UriComponentsBuilder.fromHttpUrl(nasaBaseUrl)
        .queryParam("sol", request.sol())
        .queryParam("api_key", nasaApiKey)
        .toUriString();
    NasaApiResponse response = restTemplate.getForObject(url, NasaApiResponse.class);
    return processNasaResponse(response);
  }

  private List<Picture> processNasaResponse(NasaApiResponse response) {
    List<Picture> savedPictures = new ArrayList<>();

    if (response != null && response.getPhotos() != null) {
      for (Photo photo : response.getPhotos()) {
        Camera camera = findOrCreateCamera(photo.getCamera());

        Picture picture = new Picture();
        try {
          picture.setNasaId(Long.parseLong(photo.getId()));
        } catch (NumberFormatException e) {
          continue;
        }
        picture.setImgSrc(photo.getImgSrc());
        picture.setCamera(camera);

        savedPictures.add(pictureRepository.save(picture));
      }
    }

    return savedPictures;
  }

  private Camera findOrCreateCamera(CameraInfo cameraInfo) {
    Camera camera = cameraRepository.findById(cameraInfo.getId())
        .orElse(new Camera());
    if (camera.getId() == null) {
      camera.setNasaId(cameraInfo.getId());
      camera.setName(cameraInfo.getName());
      camera = cameraRepository.save(camera);
    }
    return camera;
  }
}
