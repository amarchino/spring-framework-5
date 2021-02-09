package guru.springframework.recipeapp.commands;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
	@NotBlank
	private String description;
	@NotNull
	@Min(1)
	private BigDecimal amount;
	@NotNull
	private UnitOfMeasureCommand uom;
}
