package mate.academy.rickandmorty.service.internal;

import jakarta.annotation.PostConstruct;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterExternalDto;
import mate.academy.rickandmorty.dto.internal.CharacterInternalDto;
import mate.academy.rickandmorty.mapper.CharacterMapper;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.service.external.CharactersClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {
    private final CharacterRepository characterRepository;
    private final CharacterMapper characterMapper;
    private final CharactersClient charactersClient;

    @Override
    public Character save(CharacterExternalDto characterDto) {
        Character character = characterMapper.toModel(characterDto);
        return characterRepository.save(character);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CharacterInternalDto> findCharactersByName(String name) {
        List<CharacterInternalDto> list =
                characterRepository.findByNameContainingIgnoreCase(name).stream()
                .map(characterMapper::toInternalDto)
                .toList();
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public CharacterInternalDto getRandomCharacter() {
        return characterMapper
                .toInternalDto(characterRepository.getRandomCharacter().orElseThrow(null));
    }

    @PostConstruct
    public void init() {
        if (characterRepository.count() == 0) {
            var characterList = charactersClient.getCharacters().stream()
                    .map(characterMapper::toModel)
                    .toList();
            characterRepository.saveAll(characterList);
        }
    }
}
