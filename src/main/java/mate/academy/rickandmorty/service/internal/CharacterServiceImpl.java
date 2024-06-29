package mate.academy.rickandmorty.service.internal;

import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterDtoExt;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.repository.spec.CharacterSearchSpecification;
import mate.academy.rickandmorty.service.external.CharactersClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;
    private final CharacterSearchSpecification characterSearchSpecification;
    private final CharactersClient charactersClient;
    private final CharacterServiceImpl characterService;

    public CharacterServiceImpl(CharacterRepository characterRepository,
                                CharacterMapper characterMapper,
                                CharacterSearchSpecification characterSearchSpecification,
                                CharactersClient charactersClient,
                                CharacterServiceImpl characterService) {
        this.characterRepository = characterRepository;
        this.characterMapper = characterMapper;
        this.characterSearchSpecification = characterSearchSpecification;
        this.charactersClient = charactersClient;
        this.characterService = characterService;
    }

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
    public void fillRepository() {
        if (characterRepository.count() != 0) {
            //-----------
            System.out.println("Repository has already Rick and Morty's Characters");
        } else {
            List<CharacterDtoExt> characters = charactersClient.getCharacters();
            characters.forEach(characterService::save);
            //---------
            System.out.println("Repository has just been filled");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Character getRandomCharacter() {
        return characterRepository.getRandomCharacter();
    }
}
