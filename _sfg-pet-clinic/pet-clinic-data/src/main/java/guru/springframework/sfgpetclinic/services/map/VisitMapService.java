package guru.springframework.sfgpetclinic.services.map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.services.VisitService;

@Service
@Profile({ "default", "map" })
public class VisitMapService extends AbstractMapService<Visit> implements VisitService {
	@Override
	public Visit save(Visit visit) {
		if(visit == null
				|| visit.getPet() == null || visit.getPet().getId() == null
				|| visit.getPet().getOwner() == null || visit.getPet().getOwner().getId() == null) {
			throw new RuntimeException("Invalid visit");
		}
		return super.save(visit);
	}
}
