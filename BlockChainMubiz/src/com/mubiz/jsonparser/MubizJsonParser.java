package com.mubiz.jsonparser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MubizJsonParser {

	public static <T> T deserializeRespFromJSON(String responseBody, Class<T> typeOfT) {
		Gson gson = new GsonBuilder().create();
		T deserialized = gson.fromJson(responseBody,  typeOfT);
		System.out.println("\n---------------- the Object ------------------------");
		System.out.println("the deserialized Object:"+deserialized);
		return deserialized;
	}

}
