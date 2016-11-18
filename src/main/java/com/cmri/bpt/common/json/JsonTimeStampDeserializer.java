package com.cmri.bpt.common.json;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;



public class JsonTimeStampDeserializer extends JsonDeserializer<Date> {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat ldf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
		if (jp.getText() == null) {
			return null;
		}
		try {
			return ldf.parse(jp.getText());
		} catch (ParseException e) {
			try {
				return sdf.parse(jp.getText());
			} catch (ParseException e2) {
				return null;
			}
		}
	}
}
