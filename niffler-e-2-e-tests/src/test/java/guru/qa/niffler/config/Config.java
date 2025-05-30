package guru.qa.niffler.config;

public interface Config {

  static Config getInstance() {
    return LocalConfig.instance;
  }

  String frontUrl();

  String spendUrl();

  String spendJdbcUrl();

  String ghUrl();

  String authUrl();

  String authJdbcUrl();

  String gatewayUrl();

  String userdataUrl();

  String userdataJdbcUrl();

  String currencyJdbcUrl();

}
