package mate.academy.rickandmorty.repository;

import mate.academy.rickandmorty.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long>,
        JpaSpecificationExecutor<Character> {
    @Query(value = "SELECT * FROM characters c ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<Character> getRandomCharacter();

    List<Character> findByNameContainingIgnoreCase(String name);
}
