package dev.pollito.springbootstartertemplate.errordecoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.pollito.springbootstartertemplate.exception.JikanException;
import feign.Response;
import feign.codec.ErrorDecoder;
import moe.jikan.models.Error;

import java.io.IOException;
import java.io.InputStream;

public class JikanErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        try (InputStream body = response.body().asInputStream()) {
            return new JikanException(new ObjectMapper().readValue(body, Error.class));
        } catch (IOException e) {
            return new Default().decode(s, response);
        }
    }
}
