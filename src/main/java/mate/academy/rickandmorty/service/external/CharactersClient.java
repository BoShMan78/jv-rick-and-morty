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
    private static final String BASE_URL = "https://rickandmortyapi.com/api/character/";
    private final ObjectMapper objectMapper;

    public CharactersClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<CharacterDtoExt> getCharacters() {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(BASE_URL))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());
            //------
            System.out.println(response.body());
            RickAndMortyResponseDto dataDto = objectMapper.readValue(response.body(),
                    RickAndMortyResponseDto.class);

            List<CharacterDtoExt> characterDtoExtList = new ArrayList<>();

            characterDtoExtList.addAll(dataDto.getCharacterDtoExts());

            String nextPageUrl = dataDto.getInfo().getNext();

            do {
                httpRequest = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create(nextPageUrl))
                        .build();
                response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                dataDto = objectMapper.readValue(response.body(), RickAndMortyResponseDto.class);
                characterDtoExtList.addAll(dataDto.getCharacterDtoExts());

                nextPageUrl = dataDto.getInfo().getNext();

            } while (nextPageUrl != null);

            return characterDtoExtList;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public CharacterDtoExt getCharacterById(Long id) {
        HttpClient httpClient = HttpClient.newHttpClient();
        String url = BASE_URL + id.toString();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();
        try {
            HttpResponse<String> response = httpClient.send(httpRequest,
                    HttpResponse.BodyHandlers.ofString());
            //------
            System.out.println(response.body());
            CharacterDtoExt characterDtoExt = objectMapper.readValue(response.body(),
                    CharacterDtoExt.class);
            return characterDtoExt;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
