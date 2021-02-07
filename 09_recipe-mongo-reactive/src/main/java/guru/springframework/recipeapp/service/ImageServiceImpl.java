package guru.springframework.recipeapp.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import guru.springframework.recipeapp.repositories.reactive.RecipeReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageServiceImpl implements ImageService {
	
	private final RecipeReactiveRepository recipeReactiveRepository;
	
	@Override
	public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {
		Byte[] byteObject;
		try {
			byteObject = new Byte[file.getBytes().length];
			int i = 0;
			for(byte b : file.getBytes()) {
				byteObject[i++] = b;
			}
		} catch (IOException e) {
			log.error("Error occurred", e);
			return Mono.empty();
		}
		
		return recipeReactiveRepository
			.findById(recipeId)
			.doOnNext(r -> r.setImage(byteObject))
			.flatMap(r -> recipeReactiveRepository.save(r))
			.flatMap(r -> Mono.empty());
	}
	
}
