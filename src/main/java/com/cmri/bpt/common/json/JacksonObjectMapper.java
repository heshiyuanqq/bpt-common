package com.cmri.bpt.common.json;

import java.util.Date;


import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component("customObjectMapper")
public class JacksonObjectMapper extends ObjectMapper {

	@SuppressWarnings("deprecation")
	public JacksonObjectMapper() {

		SimpleModule module = new SimpleModule("ykxDate", new Version(1, 0, 0, "ykx"));
		// yyyy-MM-dd HH:mm:ss
		module.addSerializer(Date.class, new JsonTimeStampSerializer());
		module.addDeserializer(Date.class, new JsonTimeStampDeserializer());
		// yyyy-MM-dd HH:mm
		module.addSerializer(Date.class, new JsonDateTimeSerializer());
		module.addDeserializer(Date.class, new JsonDateTimeDeserializer());
		// yyyy-MM-dd
		module.addSerializer(Date.class, new JsonDateSerializer());
		module.addDeserializer(Date.class, new JsonDateDeserializer());

		this.registerModule(module);

		this.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		
	}
}