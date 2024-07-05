package mate.academy.rickandmorty.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.internal.CharacterInternalDto;
import mate.academy.rickandmorty.service.internal.CharacterService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/characters")
@Tag(name = "Rick and Morty character management",
        description = "Endpoints for managing characters")
public class CharactersController {
    private final CharacterService characterService;

    @GetMapping("/random")
    @Operation(summary = "Get random character",
            description = "Fill DB if empty, get random character")
    public CharacterInternalDto getRandomCharacter() {
        return characterService.getRandomCharacter();
    }

    @GetMapping("/search")
    @Operation(summary = "Search by name",
            description = "Fill DB if empty, search character by name")
    public List<CharacterInternalDto> searchCharacters(
            @RequestParam String name,
            @ParameterObject @PageableDefault Pageable pageable
    ) {
        return characterService.findCharactersByName(name);
    }
}
