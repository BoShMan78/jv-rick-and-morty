package mate.academy.rickandmorty.repository.spec;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import mate.academy.rickandmorty.model.Character;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class CharacterSearchSpecification {
    public Specification<Character> searchByName(String name) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
