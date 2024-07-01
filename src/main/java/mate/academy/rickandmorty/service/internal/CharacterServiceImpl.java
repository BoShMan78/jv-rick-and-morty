package mate.academy.rickandmorty.service.internal;

import java.util.List;
import lombok.AllArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterDtoExt;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.repository.spec.CharacterSearchSpecification;
import mate.academy.rickandmorty.service.external.CharactersClient;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;
    private final CharacterSearchSpecification characterSearchSpecification;
    private final CharactersClient charactersClient;

    @Override
    public Character save(CharacterDtoExt characterDto) {
        Character character = characterMapper.toModel(characterDto);
        return characterRepository.save(character);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Character> findCharactersByName(String name) {
        Specification<Character> spec = characterSearchSpecification.searchByName(name);
        return characterRepository.findAll(spec)
                .stream()
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Character getRandomCharacter() {
        return characterRepository.getRandomCharacter();
    }
}
