package mate.academy.rickandmorty.service.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import mate.academy.rickandmorty.dto.external.CharacterDtoExt;
import mate.academy.rickandmorty.dto.external.RickAndMortyResponseDto;
import org.springframework.stereotype.Component;

@Component
public class CharactersClient {
    private static final String BASE_URL = "https://rickandmortyapi.com/api/character";
    private final ObjectMapper objectMapper;

    public CharactersClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<CharacterDtoExt> getCharacters() {
        HttpClient httpClient = HttpClient.newHttpClient();
        List<CharacterDtoExt> characterDtoExtList = new ArrayList<>();
        RickAndMortyResponseDto dataDto;
        String additionUrl = "";
        String nextPageUrl;
        HttpResponse<String> response;
        do {
            String url = BASE_URL + additionUrl;
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();
            try {
                response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                dataDto = objectMapper.readValue(response.body(), RickAndMortyResponseDto.class);
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
            characterDtoExtList.addAll(dataDto.getCharacterDtoExts());
            nextPageUrl = dataDto.getInfo().getNext();
            if (nextPageUrl != null) {
                additionUrl = nextPageUrl.substring(nextPageUrl.indexOf('?'));
            }
        } while (nextPageUrl != null);
        return characterDtoExtList;
    }
}
