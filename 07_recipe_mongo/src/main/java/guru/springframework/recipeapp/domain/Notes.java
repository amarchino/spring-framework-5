package guru.springframework.recipeapp.domain;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notes {
	
	@Id
	private String id;
	private String recipeNotes;

}
