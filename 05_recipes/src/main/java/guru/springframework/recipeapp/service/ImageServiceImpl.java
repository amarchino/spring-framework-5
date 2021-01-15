package guru.springframework.recipeapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {@Override

	public void saveImageFile(Long recipeId, MultipartFile file) {
		// TODO
		log.debug("Received a file");
	}
	
}
