package guru.springframework.sfgpetclinic.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/owners")
@RequiredArgsConstructor
public class OwnerController {
	
	private static final String VIEWS_FIND_OWNERS = "owners/findOwners";
	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";
	private static final String VIEWS_OWNERS_LIST = "owners/ownersList";
	private static final String VIEWS_OWNER_DETAIL = "owners/ownerDetails";

	private final OwnerService ownerService;

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@RequestMapping("find")
	public String findOwners(Model model) {
		model.addAttribute("owner", Owner.builder().build());
		return VIEWS_FIND_OWNERS;
	}
	
	@GetMapping({"", "/"})
	public String processFindForm(Owner owner, BindingResult result, Model model) {
		if(owner.getLastName() == null) {
			owner.setLastName("");
		}
		List<Owner> results = ownerService.findAllByLastNameLike(owner.getLastName());
		if(results.isEmpty()) {
			result.rejectValue("lastName", "notFound", "not found");
			return VIEWS_FIND_OWNERS;
		}
		if(results.size() == 1) {
			owner = results.get(0);
			return "redirect:/owners/" + owner.getId();
		}
		model.addAttribute("selections", results);
		return VIEWS_OWNERS_LIST;
	}
	
	@GetMapping("/{ownerId}")
	public ModelAndView showOwner(@PathVariable("ownerId") Long ownerId) {
		ModelAndView mav = new ModelAndView(VIEWS_OWNER_DETAIL);
		mav.addObject(ownerService.findById(ownerId));
		return mav;
	}
	
	@GetMapping("/new")
	public String initCreationForm(Model model) {
		model.addAttribute("owner", new Owner());
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping("/new")
	public String processCreationForm(@Valid Owner owner, BindingResult result) {
		if(result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		Owner savedOwner = ownerService.save(owner);
		return "redirect:/owners/" + savedOwner.getId();
	}
	
	@GetMapping("/{ownerId}/edit")
	public String initUpdateOwnerForm(@PathVariable Long ownerId, Model model) {
		model.addAttribute("owner", ownerService.findById(ownerId));
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}
	
	@PostMapping("/{ownerId}/edit")
	public String processUpdateOwnerForm(@Valid Owner owner, BindingResult result, @PathVariable Long ownerId) {
		if(result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		owner.setId(ownerId);
		Owner savedOwner = ownerService.save(owner);
		return "redirect:/owners/" + savedOwner.getId();
	}
}
