package guru.springframework.sfgpetclinic.services.springdatajpa;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import guru.springframework.sfgpetclinic.services.CrudService;

public abstract class BaseServiceSDJpa<T, ID, R extends CrudRepository<T, ID>> implements CrudService<T, ID> {
	
	protected final R repository;
	
	protected BaseServiceSDJpa(R repository) {
		this.repository = repository;
	}
	
	@Override
	public Set<T> findAll() {
		Set<T> res = new HashSet<>();
		repository.findAll().forEach(res::add);
		return res;
	}
	@Override
	public T findById(ID id) {
		return repository.findById(id).orElse(null);
	}
	@Override
	public T save(T object) {
		return repository.save(object);
	}
	@Override
	public void delete(T object) {
		repository.delete(object);
	}
	@Override
	public void deleteById(ID id) {
		repository.deleteById(id);
	}
}
