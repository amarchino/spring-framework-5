package guru.springframework.sfgpetclinic.formatters;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import guru.springframework.sfgpetclinic.model.PetType;
import guru.springframework.sfgpetclinic.services.PetTypeService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PetTypeFormatter implements Formatter<PetType> {
	
	private final PetTypeService petTypeService;

	@Override
	public String print(PetType object, Locale locale) {
		return object.getName();
	}

	@Override
	public PetType parse(String text, Locale locale) throws ParseException {
		return petTypeService.findAll()
			.stream()
			.filter(pt -> pt.getName().equals(text))
			.findFirst()
			.orElseThrow(() -> new ParseException("Type not found: " + text, 0));
	}

}
