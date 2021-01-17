package guru.springframework.sfgpetclinic.controllers;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guru.springframework.sfgpetclinic.model.Pet;
import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.PetService;
import guru.springframework.sfgpetclinic.services.VisitService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}/visits")
@RequiredArgsConstructor
public class VisitController {
	private static final String VIEWS_VISIT_CREATE_OR_UPDATE_FORM = "pets/createOrUpdateVisitForm";
	
	private final PetService petService;
	private final VisitService visitService;

	@ModelAttribute("visit")
	public Visit loadPetWithVisitPet(@PathVariable("petId") Long petId, Model model) {
		Pet pet = petService.findById(petId);
		Visit visit = new Visit();
		pet.addVisit(visit);
		model.addAttribute("pet", pet);
		return visit;
	}
	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}
	
	@GetMapping("/new")
	public String initNewVisitForm() {
		return VIEWS_VISIT_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping("/new")
	public String processNewVisitForm(@Valid Visit visit, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_VISIT_CREATE_OR_UPDATE_FORM;
		}
		visitService.save(visit);
		return "redirect:/owners/" + visit.getPet().getOwner().getId();
	}
}
