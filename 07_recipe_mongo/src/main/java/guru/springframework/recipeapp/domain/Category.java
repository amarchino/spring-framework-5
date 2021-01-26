package guru.springframework.recipeapp.domain;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {

	private String id;
	private String description;
	@EqualsAndHashCode.Exclude
	@Builder.Default
	private Set<Recipe> recipes = new HashSet<>();

}
