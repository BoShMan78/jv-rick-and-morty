package mate.academy.rickandmorty.controller;

import java.util.List;
import mate.academy.rickandmorty.dto.external.CharacterDtoExt;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.service.external.CharactersClient;
import mate.academy.rickandmorty.service.internal.CharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/characters")
public class CharactersController {
    private final CharactersClient charactersClient;
    private final CharacterService characterService;

    public CharactersController(CharactersClient charactersClient,
                                CharacterService charactersService) {
        this.charactersClient = charactersClient;
        this.characterService = charactersService;
    }

    @GetMapping
    public List<CharacterDtoExt> getCharacters() {
        List<CharacterDtoExt> characterDtoExts = charactersClient.getCharacters();
        characterDtoExts.forEach(System.out::println);
        return characterDtoExts;
    }

    @GetMapping("/{id}")
    public CharacterDtoExt getCharacterById(@PathVariable Long id) {
        CharacterDtoExt characterDtoExt = charactersClient.getCharacterById(id);
        return characterDtoExt;
    }

    @GetMapping("/random")
    public Character getRandomCharacter() {
        characterService.fillRepository();
        return characterService.getRandomCharacter();
    }

    @GetMapping("/search")
    public List<Character> searchCharacters(@PathVariable String name) {
        return characterService.findCharactersByName(name);
    }
}
