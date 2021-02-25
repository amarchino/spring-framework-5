package guru.springframework.restdocs.web.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.springframework.restdocs.domain.Beer;
import guru.springframework.restdocs.repositories.BeerRepository;
import guru.springframework.restdocs.web.controllers.BeerController;
import guru.springframework.restdocs.web.model.BeerDto;
import guru.springframework.restdocs.web.model.BeerStyleEnum;

@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.springframework.guru", uriPort = 80)
@ExtendWith(RestDocumentationExtension.class)
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "guru.springframework.restdocs.web.mappers")
public class BeerControllerTest {

	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	@MockBean private BeerRepository beerRepository;
	
	@Test
	public void getBeerById() throws Exception {
        BDDMockito.given(beerRepository.findById(Mockito.any(UUID.class))).willReturn(Optional.of(Beer.builder().id(UUID.randomUUID()).beerName("Nice Ale").beerStyle("ALE").price(new BigDecimal("9.99")).upc(123456789L).build()));

        mockMvc.perform(
    		get(BeerController.BASE_URL + "/{beerId}", UUID.randomUUID().toString())
    		.param("iscold", "yes")
    		.accept(MediaType.APPLICATION_JSON)
    	)
        .andExpect(status().isOk())
        .andDo(document("v1/beer-get",
        	RequestDocumentation.pathParameters(
        		RequestDocumentation.parameterWithName("beerId").description("UUID of the selected beer to get")
        	),
        	RequestDocumentation.requestParameters(
        		RequestDocumentation.parameterWithName("iscold").description("Is beer cold?")
        	),
        	PayloadDocumentation.responseFields(
        		PayloadDocumentation.fieldWithPath("id").description("Id of the beer"),
        		PayloadDocumentation.fieldWithPath("version").description("Version number"),
        		PayloadDocumentation.fieldWithPath("createdDate").description("Date created"),
        		PayloadDocumentation.fieldWithPath("lastModifiedDate").description("Date updated"),
        		PayloadDocumentation.fieldWithPath("beerName").description("Beer name"),
        		PayloadDocumentation.fieldWithPath("beerStyle").description("beer style"),
        		PayloadDocumentation.fieldWithPath("upc").description("UPC of beer"),
        		PayloadDocumentation.fieldWithPath("price").description("Price"),
        		PayloadDocumentation.fieldWithPath("quantityOnHand").description("Quantity on hand")
        	)
        ));
    }

    @Test
    public void saveNewBeer() throws Exception {
        BeerDto beerDto =  getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(
        	post(BeerController.BASE_URL)
        	.contentType(MediaType.APPLICATION_JSON)
        	.content(beerDtoJson)
        )
        .andExpect(status().isCreated())
        .andDo(document("v1/beer-new",
        	PayloadDocumentation.requestFields(
        		fields.withPath("id").ignored(),
        		fields.withPath("version").ignored(),
        		fields.withPath("createdDate").ignored(),
        		fields.withPath("lastModifiedDate").ignored(),
        		fields.withPath("beerName").description("Beer name"),
        		fields.withPath("beerStyle").description("beer style"),
        		fields.withPath("upc").description("UPC of beer").attributes(),
        		fields.withPath("price").description("Price"),
        		fields.withPath("quantityOnHand").ignored()
        	)
        ));
    }

    @Test
    public void updateBeerById() throws Exception {
        BeerDto beerDto =  getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
        
        BDDMockito.given(beerRepository.findById(Mockito.any(UUID.class))).willReturn(Optional.of(Beer.builder().build()));

        mockMvc.perform(put(BeerController.BASE_URL + "/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    private BeerDto getValidBeerDto(){
        return BeerDto.builder()
                .beerName("Nice Ale")
                .beerStyle(BeerStyleEnum.ALE)
                .price(new BigDecimal("9.99"))
                .upc(123123123123L)
                .build();

    }
    
    private static class ConstrainedFields {
    	private final ConstraintDescriptions constraintDescriptions;
    	public ConstrainedFields(Class<?> input) {
    		this.constraintDescriptions = new ConstraintDescriptions(input);
		}
    	private FieldDescriptor withPath(String path) {
    		return PayloadDocumentation.fieldWithPath(path)
				.attributes(
					Attributes.key("constraints")
					.value(StringUtils.collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ". "))
				);
    	}
    }
}
