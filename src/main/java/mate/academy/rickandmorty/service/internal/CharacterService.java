package mate.academy.rickandmorty.service.internal;

import java.util.List;
import mate.academy.rickandmorty.dto.external.CharacterExternalDto;
import mate.academy.rickandmorty.dto.internal.CharacterInternalDto;
import mate.academy.rickandmorty.model.Character;

public interface CharacterService {
    Character save(CharacterExternalDto characterDto);

    List<CharacterInternalDto> findCharactersByName(String name);

    CharacterInternalDto getRandomCharacter();
}
