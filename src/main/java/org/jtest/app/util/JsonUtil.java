package org.jtest.app.util;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * 
 * @author pengchen
 *
 */
public class JsonUtil {
     public static final Gson gson = new GsonBuilder().disableHtmlEscaping().registerTypeAdapter(Double.class, new JsonSerializer<Double>() {

 		public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
 			// TODO Auto-generated method stub
 			 if (src == src.longValue())
 	                return new JsonPrimitive(src.longValue());
 	         return new JsonPrimitive(src);
 		}      
     }).create();
}
