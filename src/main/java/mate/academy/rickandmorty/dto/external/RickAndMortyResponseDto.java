package mate.academy.rickandmorty.dto.external;

import java.util.List;
import lombok.Data;

@Data
public class RickAndMortyResponseDto {
    private Info info;
    private List<CharacterDtoExt> characterDtoExts;
}
