package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterDtoExt;
import mate.academy.rickandmorty.model.Character;
import mate.academy.rickandmorty.repository.CharacterRepository;
import mate.academy.rickandmorty.service.external.CharactersClient;
import mate.academy.rickandmorty.service.internal.CharacterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/characters")
@Tag(name = "Rick and Morty character management",
        description = "Endpoints for managing characters")
public class CharactersController {
    private final CharactersClient charactersClient;
    private final CharacterService characterService;
    private final CharacterRepository characterRepository;

    @GetMapping("/random")
    @Operation(summary = "Get random character",
            description = "Fill DB if empty, get random character")
    public Character getRandomCharacter() {
        fillRepository();
        return characterService.getRandomCharacter();
    }

    @GetMapping("/search")
    @Operation(summary = "Search by name",
            description = "Fill DB if empty, search character by name")
    public List<Character> searchCharacters(@RequestParam String name) {
        fillRepository();
        return characterService.findCharactersByName(name);
    }

    private void fillRepository() {
        if (characterRepository.count() == 0) {
            List<CharacterDtoExt> characters = charactersClient.getCharacters();
            characters.forEach(characterService::save);
        }
    }
}
