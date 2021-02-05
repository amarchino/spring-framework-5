package guru.springframework.recipeapp.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.recipeapp.domain.Recipe;
import guru.springframework.recipeapp.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {
	
	private final RecipeRepository recipeRepository;
	
	@Override
	public void saveImageFile(String recipeId, MultipartFile file) {
		try {
			Recipe recipe = recipeRepository.findById(recipeId).get();
			Byte[] byteObject = new Byte[file.getBytes().length];
			int i = 0;
			for(byte b : file.getBytes()) {
				byteObject[i++] = b;
			}
			recipe.setImage(byteObject);
			recipeRepository.save(recipe);
		} catch(Exception e) {
			// TODO handle better
			log.error("Error occurred", e);
		}
	}
	
}
