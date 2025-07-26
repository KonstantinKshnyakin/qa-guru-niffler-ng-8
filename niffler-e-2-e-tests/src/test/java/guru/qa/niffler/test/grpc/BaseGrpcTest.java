package guru.qa.niffler.test.grpc;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.grpc.NifflerCurrencyServiceGrpc;
import guru.qa.niffler.jupiter.annotation.GrpcTest;
import guru.qa.niffler.jupiter.extension.ApiLoginExtension;
import guru.qa.niffler.util.GrpcConsoleInterceptor;
import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import org.junit.jupiter.api.extension.RegisterExtension;

@GrpcTest
public class BaseGrpcTest {

    protected static final Config CFG = Config.getInstance();
    @RegisterExtension
    protected static ApiLoginExtension apiLoginExtension = ApiLoginExtension.restApiLOGinExtension();

    protected static Channel channel = ManagedChannelBuilder
        .forAddress(CFG.currencyGrpcAddress(), CFG.currencyGrpcPort())
        .intercept(new GrpcConsoleInterceptor())
        .usePlaintext()
        .build();


    protected static final NifflerCurrencyServiceGrpc.NifflerCurrencyServiceBlockingStub blockingStub =
        NifflerCurrencyServiceGrpc.newBlockingStub(channel);
}
