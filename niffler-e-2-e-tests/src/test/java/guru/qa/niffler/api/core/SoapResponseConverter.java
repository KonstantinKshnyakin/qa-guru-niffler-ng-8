package guru.qa.niffler.api.core;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.MimeHeaders;
import jakarta.xml.soap.SOAPException;
import jakarta.xml.soap.SOAPMessage;
import okhttp3.ResponseBody;
import org.w3c.dom.Document;
import retrofit2.Converter;

import java.io.IOException;
import java.io.InputStream;

public final class SoapResponseConverter<T> implements Converter<ResponseBody, T> {

    private final JAXBContext context;
    private final Class<T> type;

    SoapResponseConverter(JAXBContext context, Class<T> type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try (value; InputStream is = value.byteStream()) {
            MimeHeaders headers = new MimeHeaders();
            if (value.contentType() != null) {
                headers.addHeader("Content-type", value.contentType().toString());
            }
            try {
                SOAPMessage message = MessageFactory.newInstance().createMessage(headers, is);
                Document document = message.getSOAPBody().extractContentAsDocument();
                return context.createUnmarshaller().unmarshal(document, type).getValue();
            } catch (SOAPException | JAXBException e) {
                throw new RuntimeException(e);
            }
        }
    }
}