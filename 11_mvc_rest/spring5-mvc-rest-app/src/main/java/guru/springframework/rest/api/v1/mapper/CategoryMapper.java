package guru.springframework.rest.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import guru.springframework.rest.api.v1.model.CategoryDTO;
import guru.springframework.rest.domain.Category;

@Mapper(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface CategoryMapper {
	
	CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

	CategoryDTO categoryToCategoryDTO(Category category);
	Category categoryDTOToCategory(CategoryDTO categoryDto);
}
