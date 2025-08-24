package guru.qa.niffler.config;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

enum DockerConfig implements Config {
    INSTANCE;

    @Override
    public @Nonnull String frontUrl() {
        return "http://frontend.niffler.dc/";
    }

    @Override
    public @Nonnull String spendUrl() {
        return "http://spend.niffler.dc:8093/";
    }

    @Override
    public @Nonnull String spendJdbcUrl() {
        return "jdbc:postgresql://niffler-all-db:5432/niffler-spend";
    }

    @Override
    public @Nonnull String authUrl() {
        return "http://auth.niffler.dc:9000/";
    }

    @Override
    public @Nonnull String authJdbcUrl() {
        return "jdbc:postgresql://niffler-all-db:5432/niffler-auth";
    }

    @Override
    public @Nonnull String gatewayUrl() {
        return "http://gateway.niffler.dc:8090/";
    }

    @Override
    public @Nonnull String userdataUrl() {
        return "http://userdata.niffler.dc:8089/";
    }

    @Override
    public @Nonnull String userdataJdbcUrl() {
        return "jdbc:postgresql://niffler-all-db:5432/niffler-userdata";
    }

    @Override
    public @Nonnull String currencyJdbcUrl() {
        return "jdbc:postgresql://niffler-all-db:5432/niffler-currency";
    }

    @NotNull
    @Override
    public String currencyGrpcAddress() {
        return "currency.niffler.dc";
    }
}
