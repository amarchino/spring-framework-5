package guru.springframework.sfgpetclinic.services.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import guru.springframework.sfgpetclinic.model.BaseEntity;

public abstract class AbstractMapService<T extends BaseEntity, ID extends Long> {
	
	protected Map<Long, T> map = new HashMap<>();
	
	public Set<T> findAll() {
		return new HashSet<>(map.values());
	}
	
	public T findById(Long id) {
		return map.get(id);
	}
	
	public T save(T object) {
		if(object == null) {
			throw new RuntimeException("Object cannot be null");
		}
		
		if(object.getId() == null) {
			object.setId(nextId());
		}
		map.put(object.getId(), object);
		return object;
	}

	public void deleteById(Long id) {
		map.remove(id);
	}
	
	public void delete(T object) {
		map.entrySet().removeIf(entry -> entry.getValue().equals(object));
	}
	
	private Long nextId() {
		return map.isEmpty() ? 1L : Collections.max(map.keySet()) + 1;
	}
}
