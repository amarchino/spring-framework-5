package guru.springframework.recipeapp.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;
	@Lob
	private String directions;
	@Lob
	private Byte[] image;
	@Enumerated(EnumType.STRING)
	private Difficulty difficulty;
	@OneToOne(cascade = CascadeType.ALL)
	private Note note;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
	@Builder.Default
	private Set<Ingredient> ingredients = new HashSet<>();
	@ManyToMany
	@JoinTable(name="recipe_category", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	@Builder.Default
	private Set<Category> categories = new HashSet<>();

	public void setNote(Note note) {
		this.note = note;
		if(note != null) {
			note.setRecipe(this);
		}
	}
	public Recipe addIngredient(Ingredient ingredient) {
		ingredient.setRecipe(this);
		this.ingredients.add(ingredient);
		return this;
	}
	public Recipe removeIngredient(Ingredient ingredient) {
		ingredient.setRecipe(null);
		this.ingredients.remove(ingredient);
		return this;
	}

}
