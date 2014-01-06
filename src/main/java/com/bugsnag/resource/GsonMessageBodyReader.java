package com.bugsnag.resource;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import org.apache.commons.io.IOUtils;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class GsonMessageBodyReader implements MessageBodyReader<Object> {

	private Gson gson = new GsonBuilder().create();
	
	@Override
	public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
		return true;
	}

	@Override
	public Object readFrom(
			final Class<Object> type,
			final Type genericType,
			final Annotation[] annotations,
			final MediaType mediaType,
			final MultivaluedMap<String, String> httpHeaders,
			final InputStream entityStream) throws IOException {

		final String json = IOUtils.toString(entityStream, Charsets.UTF_8);
		return gson.fromJson(json, genericType);
	}

}
