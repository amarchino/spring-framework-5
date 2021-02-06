package guru.springframework.recipeapp.domain;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Category {

	@Id
	private String id;
	private String description;
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private Set<Recipe> recipes = new HashSet<>();

}
