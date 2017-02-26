package com.mubiz.wsclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.mubiz.httpclient.MubizHttpClient;
import com.mubiz.jsonparser.MubizJsonParser;
import com.mubiz.dao.Block;

public class MubizWSClient {
	public static void main(String[] args) throws ClientProtocolException, IOException {

		String responseBody = new MubizHttpClient(MubizHttpClient.MUBIZ_BLOC_HASH_URL
				+ "000000000000000000207a44417907a44175fc9152dc05b901d180a68fe42070/").connectToWS();
		

		MubizJsonParser.deserializeRespFromJSON(responseBody, Block.class);
		
		
		//Block block = MubizJsonParser.deserializeRespFromJSON(responseBody, Block.class);
		// System.out.println("---------------- the Block
		// ------------------------");
		// System.out.println("the deserialized Block:"+block);

	}
}
