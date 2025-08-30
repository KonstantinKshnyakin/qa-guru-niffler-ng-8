package guru.qa.niffler.api.model.allure_server;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record AllureResult(
    @JsonProperty("file_name")
    String filename,
    @JsonProperty("content_base64")
    String contentBase64
) {
}