package guru.qa.niffler.api.core;

import org.jetbrains.annotations.Nullable;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ResponseCallAdapterFactory extends CallAdapter.Factory {

    public static ResponseCallAdapterFactory create() {
        return new ResponseCallAdapterFactory();
    }

    @Nullable
    @Override
    public CallAdapter<?, ?> get(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(type) != Response.class) {
            return null;
        }

        if (!(type instanceof ParameterizedType)) {
            throw new IllegalStateException("Response must be parameterized as Response<Foo>");
        }

        Type innerType = getParameterUpperBound(0, (ParameterizedType) type);

        return new CallAdapter<Object, Response<?>>() {

            @Override
            public Type responseType() {
                return innerType;
            }

            @Override
            public Response<?> adapt(Call<Object> call) {
                try {
                    return call.execute();
                } catch (Exception e) {
                    throw new RuntimeException("HTTP call failed", e);
                }
            }
        };
    }
}
