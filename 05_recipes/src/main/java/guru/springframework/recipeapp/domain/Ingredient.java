package guru.springframework.recipeapp.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	private BigDecimal quantity;
	@ManyToOne
	private Recipe recipe;
	@OneToOne(fetch = FetchType.EAGER)
	private UnitOfMeasure unit;
	
	public Ingredient() {}

	public Ingredient(String description, BigDecimal quantity, UnitOfMeasure unit, Recipe recipe) {
		this.description = description;
		this.quantity = quantity;
		this.unit = unit;
		this.recipe = recipe;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	public UnitOfMeasure getUnit() {
		return unit;
	}
	public void setUnit(UnitOfMeasure unit) {
		this.unit = unit;
	}

}
