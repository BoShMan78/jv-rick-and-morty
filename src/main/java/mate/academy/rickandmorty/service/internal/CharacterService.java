package mate.academy.rickandmorty.service.internal;

import java.util.List;
import mate.academy.rickandmorty.dto.external.CharacterDtoExt;
import mate.academy.rickandmorty.model.Character;

public interface CharacterService {
    Character save(CharacterDtoExt characterDto);

    List<Character> findCharactersByName(String name);

    void fillRepository();

    Character getRandomCharacter();
}
