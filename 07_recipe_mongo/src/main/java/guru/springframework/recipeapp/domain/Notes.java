package guru.springframework.recipeapp.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notes {
	
	private String id;
	private String recipeNotes;
	@EqualsAndHashCode.Exclude
	private Recipe recipe;

}
