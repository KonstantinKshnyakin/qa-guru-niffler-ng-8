package guru.qa.niffler.api;

import guru.qa.niffler.api.model.allure_server.AllureResult;
import guru.qa.niffler.api.model.allure_server.AllureResults;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

public interface AllureServerRestClient {

    @POST("allure-docker-service/send-results")
    Response<Void> sendResults(@Query("project_id") String projectId,
                               @Query("force_project_creation") boolean forceProjectCreation,
                               @Body AllureResults allureResults);

    default Response<Void> sendResults(String projectId, List<AllureResult> results) {
        return sendResults(projectId, true, new AllureResults(results));
    }

    @GET("allure-docker-service/generate-report")
    Response<Void> generateReport(@Query("project_id") String projectId,
                                  @Query("execution_name") String executionName,
                                  @Query("execution_from") String executionFrom,
                                  @Query("execution_type") String executionType);
}
