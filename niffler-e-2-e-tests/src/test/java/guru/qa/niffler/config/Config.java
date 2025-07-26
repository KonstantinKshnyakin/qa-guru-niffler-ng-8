package guru.qa.niffler.config;

import javax.annotation.Nonnull;

public interface Config {

  static @Nonnull Config getInstance() {
    return "docker".equals(System.getProperty("test.env"))
        ? DockerConfig.INSTANCE
        : LocalConfig.INSTANCE;
  }

  @Nonnull
  String frontUrl();

  @Nonnull
  String spendUrl();

  @Nonnull
  String spendJdbcUrl();

  default @Nonnull String ghUrl() {
    return "https://api.github.com/";
  }

  @Nonnull
  String authUrl();

  @Nonnull
  String authJdbcUrl();

  @Nonnull
  String gatewayUrl();

  @Nonnull
  String userdataUrl();

  @Nonnull
  String userdataJdbcUrl();

  @Nonnull
  String currencyJdbcUrl();

  @Nonnull
  String currencyGrpcAddress();

  @Nonnull
  default Integer currencyGrpcPort() {
    return 8092;
  }
}
