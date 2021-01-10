package guru.springframework.recipeapp.commands;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
	private Long id;
	private String description;
	private BigDecimal quantity;
	private UnitOfMeasureCommand unit;
}
