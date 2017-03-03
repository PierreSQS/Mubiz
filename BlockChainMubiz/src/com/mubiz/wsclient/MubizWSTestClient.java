package com.mubiz.wsclient;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.http.client.ClientProtocolException;

import com.mubiz.dao.Block;
import com.mubiz.httpclient.MubizHttpClient;
import com.mubiz.jsonparser.MubizJsonParser;

public class MubizWSTestClient {

	public static void main(String[] args) throws ClientProtocolException, IOException, SQLException {

		MubizHttpClient myMubizHttpClient = new MubizHttpClient();

		String responseBody = myMubizHttpClient.connectToWS(MubizHttpClient.MUBIZ_BLOC_HASH_URL
				+ "000000000000000000207a44417907a44175fc9152dc05b901d180a68fe42070/");

		MubizJsonParser.deserializeRespFromJSON(responseBody, Block.class);

		// Block block = MubizJsonParser.deserializeRespFromJSON(responseBody,
		// Block.class);
		// System.out.println("---------------- the Block
		// ------------------------");
		// System.out.println("the deserialized Block:"+block);

	}

}
