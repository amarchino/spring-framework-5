package guru.springframework.sfgpetclinic.services.map;

import org.springframework.stereotype.Service;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.services.SpecialityService;

@Service
public class SpecialityMapService extends AbstractMapService<Speciality> implements SpecialityService {
}
