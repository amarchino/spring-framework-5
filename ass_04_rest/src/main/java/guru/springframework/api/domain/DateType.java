package guru.springframework.api.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DateType {

	private String date;
	@JsonProperty("timezone_type")
	private Integer timezoneType;
	private String timezone;
}
