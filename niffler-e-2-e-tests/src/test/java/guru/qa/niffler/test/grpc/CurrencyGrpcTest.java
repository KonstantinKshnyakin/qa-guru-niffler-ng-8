package guru.qa.niffler.test.grpc;

import com.google.protobuf.Empty;
import guru.qa.niffler.grpc.Currency;
import guru.qa.niffler.grpc.CurrencyResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CurrencyGrpcTest extends BaseGrpcTest {

    @Test
    public void allCurrenciesShouldBeReturned() {
        CurrencyResponse response = blockingStub.getAllCurrencies(Empty.getDefaultInstance());
        List<Currency> allCurrenciesList = response.getAllCurrenciesList();
        Assertions.assertEquals(4, allCurrenciesList.size());
    }
}
