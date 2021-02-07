package guru.springframework.recipeapp.commands;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import guru.springframework.recipeapp.domain.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeCommand {
	private String id;
	
	@NotBlank
	@Size(min = 3, max=255)
	private String description;
	@Min(1)
	@Max(999)
	private Integer prepTime;
	@Min(1)
	@Max(999)
	private Integer cookTime;
	@Min(1)
	@Max(100)
	private Integer servings;
	private String source;
	@URL
	private String url;
	@NotBlank
	private String directions;
	@Builder.Default
	private List<IngredientCommand> ingredients = new ArrayList<>();
	private Difficulty difficulty;
	private NotesCommand notes;
	@Builder.Default
	private List<CategoryCommand> categories = new ArrayList<>();
	private Byte[] image;
}
