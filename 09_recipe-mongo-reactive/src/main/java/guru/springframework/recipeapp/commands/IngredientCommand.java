package guru.springframework.recipeapp.commands;

import java.math.BigDecimal;

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
public class IngredientCommand {
	private String id;
	private String recipeId;
	private String description;
	private BigDecimal amount;
	private UnitOfMeasureCommand uom;
}
