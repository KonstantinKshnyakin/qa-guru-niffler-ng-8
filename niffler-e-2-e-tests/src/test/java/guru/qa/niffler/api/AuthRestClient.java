package guru.qa.niffler.api;

import guru.qa.niffler.api.core.TestResponse;
import guru.qa.niffler.api.model.OAuthTokenResponse;
import retrofit2.http.*;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public interface AuthRestClient {


    @GET("login")
    TestResponse<Void, Void> loginGet();

    @FormUrlEncoded
    @POST("login")
    TestResponse<Void, Void> loginPost(@Field("_csrf") String csrf,
                                       @Field("username") String username,
                                       @Field("password") String password);

    @GET("register")
    TestResponse<Void, Void> registerGet();

    @FormUrlEncoded
    @POST("register")
    TestResponse<Void, Void> registerPost(@Field("_csrf") String csrf,
                                          @Field("username") String username,
                                          @Field("password") String password,
                                          @Field("passwordSubmit") String passwordSubmit);

    @GET("oauth2/authorize")
    TestResponse<Void, Void> authorizeGet(@Query("response_type") String responseType,
                                          @Query("client_id") String clientId,
                                          @Query("scope") String scope,
                                          @Query(value = "redirect_uri") String redirectUri,
                                          @Query("code_challenge") String codeChallenge,
                                          @Query("code_challenge_method") String codeChallengeMethod
    );

    @FormUrlEncoded
    @POST("oauth2/token")
    TestResponse<OAuthTokenResponse, Void> tokenPost(@Field("code") String code,
                                                     @Field("redirect_uri") String redirectUri,
                                                     @Field("code_verifier") String codeVerifier,
                                                     @Field("grant_type") String grantType,
                                                     @Field(value = "client_id") String client);
}
