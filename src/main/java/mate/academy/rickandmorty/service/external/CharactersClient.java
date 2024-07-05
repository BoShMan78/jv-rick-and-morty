package mate.academy.rickandmorty.service.external;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import mate.academy.rickandmorty.dto.external.CharacterExternalDto;
import mate.academy.rickandmorty.dto.external.RickAndMortyResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharactersClient {
    private final ObjectMapper objectMapper;

    @Value("${rick-and-morty.url}")
    private String baseUrl;

    public List<CharacterExternalDto> getCharacters() {
        HttpClient httpClient = HttpClient.newHttpClient();
        List<CharacterExternalDto> characterExternalDtoList = new ArrayList<>();
        RickAndMortyResponseDto dataDto;
        String additionUrl = "";
        String nextPageUrl;
        HttpResponse<String> response;
        do {
            String url = baseUrl + additionUrl;
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
            characterExternalDtoList.addAll(dataDto.getCharacterExternalDtos());
            nextPageUrl = dataDto.getInfo().getNext();
            if (nextPageUrl != null) {
                additionUrl = nextPageUrl.substring(nextPageUrl.indexOf('?'));
            }
        } while (nextPageUrl != null);
        return characterExternalDtoList;
    }
}
