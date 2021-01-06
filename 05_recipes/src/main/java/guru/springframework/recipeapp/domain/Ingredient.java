package guru.springframework.recipeapp.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
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
	
	public Ingredient(String description, BigDecimal quantity, UnitOfMeasure unit) {
		this.description = description;
		this.quantity = quantity;
		this.unit = unit;
	}

}
