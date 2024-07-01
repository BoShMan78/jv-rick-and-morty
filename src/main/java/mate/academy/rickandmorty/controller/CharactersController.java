package mate.academy.rickandmorty.controller;

import java.util.List;
import mate.academy.rickandmorty.dto.external.CharacterDtoExt;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
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
    private final CharacterRepository characterRepository;

    public CharactersController(CharactersClient charactersClient,
                                CharacterService charactersService,
                                CharacterRepository characterRepository) {
        this.charactersClient = charactersClient;
        this.characterService = charactersService;
        this.characterRepository = characterRepository;
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
        fillRepository();
        return characterService.getRandomCharacter();
    }

    @GetMapping("/search")
    public List<Character> searchCharacters(@PathVariable String name) {
        fillRepository();
        return characterService.findCharactersByName(name);
    }

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
}
