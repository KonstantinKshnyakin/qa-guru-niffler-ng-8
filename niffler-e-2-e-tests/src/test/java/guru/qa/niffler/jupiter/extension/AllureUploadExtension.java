package guru.qa.niffler.jupiter.extension;

import guru.qa.niffler.api.AllureServerRestClient;
import guru.qa.niffler.api.ApiClients;
import guru.qa.niffler.api.model.allure_server.AllureResult;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.platform.commons.util.StringUtils;
import retrofit2.Response;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class AllureUploadExtension implements SuiteExtension {

    private static final AllureServerRestClient allureServerClient = ApiClients.allureServerClient();
    private static final String PROJECT_ID = "niffler-ng-8";
    private static final String ALLURE_RESULTS_DIRECTORY = "./niffler-e-2-e-tests/build/allure-results";
    private static String EXECUTION_NAME;
    private static String BUILD_URL;
    private static String EXECUTION_TYPE;

    static {
        String localRun = "local_run";
        String defaultValue = localRun + "_" + LocalDateTime.now();
        EXECUTION_NAME = System.getenv("HEAD_COMMIT_MESSAGE");
        if (StringUtils.isBlank(EXECUTION_NAME)) {
            EXECUTION_NAME = defaultValue;
        }
        BUILD_URL = System.getenv("BUILD_URL");
        if (StringUtils.isBlank(BUILD_URL)) {
            BUILD_URL = defaultValue;

        }
        EXECUTION_TYPE = System.getenv("EXECUTION_TYPE");
        if (StringUtils.isBlank(EXECUTION_TYPE)) {
            EXECUTION_TYPE = localRun;
        }
    }

    @SneakyThrows
    @Override
    public void afterSuite() {
        final Base64.Encoder encoder = Base64.getEncoder();
        List<AllureResult> results = Files.walk(Paths.get(ALLURE_RESULTS_DIRECTORY))
            .filter(Files::isRegularFile)
            .map(filePath -> {
                try {
                    byte[] content = Files.readAllBytes(filePath);
                    return new AllureResult(filePath.getFileName().toString(), encoder.encodeToString(content));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            })
            .collect(Collectors.toList());
        Response<Void> sendResultResp = allureServerClient.sendResults(PROJECT_ID, results);
        Assertions.assertTrue(sendResultResp.isSuccessful());

        Response<Void> generateReportREsp = allureServerClient.generateReport(
            PROJECT_ID,
            EXECUTION_NAME,
            BUILD_URL,
            EXECUTION_TYPE
        );
        Assertions.assertTrue(generateReportREsp.isSuccessful());
    }
}
