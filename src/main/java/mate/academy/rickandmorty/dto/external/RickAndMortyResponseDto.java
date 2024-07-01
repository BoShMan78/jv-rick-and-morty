package mate.academy.rickandmorty.dto.external;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RickAndMortyResponseDto {
    private Info info;
    @JsonProperty("results")
    private List<CharacterDtoExt> characterDtoExts;
}
