package guru.qa.niffler.test.grpc;

import com.google.protobuf.Empty;
import guru.qa.niffler.grpc.CalculateRequest;
import guru.qa.niffler.grpc.Currency;
import guru.qa.niffler.grpc.CurrencyResponse;
import guru.qa.niffler.grpc.CurrencyValues;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static guru.qa.niffler.grpc.CurrencyValues.*;

public class CurrencyGrpcTest extends BaseGrpcTest {

    @Test
    public void allCurrenciesShouldBeReturned() {
        CurrencyResponse response = currencyGrpcClient.getAllCurrencies(Empty.getDefaultInstance());
        List<Currency> allCurrenciesList = response.getAllCurrenciesList();
        Assertions.assertEquals(4, allCurrenciesList.size());
    }

    @MethodSource
    @ParameterizedTest(name = "calculateRate должен корректно сконвертировать {2} {0} в {1} (ожидается {3})")
    void calculateRateShouldBeConverted(CurrencyValues spendCurrency,
                                        CurrencyValues desiredCurrency,
                                        double amount,
                                        double expRate) {
        CalculateRequest request = buildRequest(spendCurrency, desiredCurrency, amount);
        double actRate = currencyGrpcClient.calculateRate(request)
            .getCalculatedAmount();

        Assertions.assertEquals(expRate, actRate);
    }

    static Stream<Arguments> calculateRateShouldBeConverted() {
        return Stream.of(
            Arguments.of(RUB, RUB, 675.96, 675.96),
            Arguments.of(RUB, USD, 261.34, 3.92),
            Arguments.of(RUB, EUR, 376.74, 5.23),
            Arguments.of(RUB, KZT, 616.19, 4401.36),
            Arguments.of(KZT, USD, 489.5, 1.03),
            Arguments.of(EUR, USD, 0.88, 0.95),
            Arguments.of(USD, KZT, -4.55, -2166.67), //<Баг
            Arguments.of(EUR, USD, 0.0, 0.0)
        );
    }

    private CalculateRequest buildRequest(CurrencyValues spendCurrency,
                                          CurrencyValues desiredCurrency,
                                          double amount) {
        return CalculateRequest.newBuilder()
            .setAmount(amount)
            .setSpendCurrency(spendCurrency)
            .setDesiredCurrency(desiredCurrency)
            .build();
    }
}
