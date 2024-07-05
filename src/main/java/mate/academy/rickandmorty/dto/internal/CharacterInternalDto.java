package mate.academy.rickandmorty.dto.internal;

public record CharacterInternalDto(
        Long id,
        Long externalId,
        String name,
        String status,
        String gender
) {
}
