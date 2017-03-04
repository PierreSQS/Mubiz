package com.mubiz.wsclient;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.http.client.ClientProtocolException;

import com.mubiz.httpclient.MubizHttpClient;

public class MubizWSTestClient {

	private static void pause(long millisec) {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		}

	}

	public static void main(String[] args) throws ClientProtocolException, IOException, SQLException {

		MubizHttpClient myMubizHttpClient = new MubizHttpClient();

		System.out.printf("Displaying the number of blocks%n");

		myMubizHttpClient.connectToWS(MubizHttpClient.MUBIZ_BLOCKS_URL);

		System.out.printf("%nDisplaying the hash of block #455704%n");

		myMubizHttpClient.connectToWS(MubizHttpClient.MUBIZ_BLOCK_INDEX_URL+"455704/");
		
		System.out.println("\nWaiting 5s...");
		pause(5000);
		
		System.out.printf("%nDisplaying the data of block #455704");

		myMubizHttpClient.connectToWS(MubizHttpClient.MUBIZ_BLOCK_HASH_URL
				+ "000000000000000000708493b8cdb77e8eeb3c856974b26136e5c0ceac41f898/");

		
		// Block block = MubizJsonParser.deserializeRespFromJSON(responseBody,
		// Block.class);
		// System.out.println("---------------- the
		// Block------------------------");
		// System.out.println("the deserialized Block:"+block);

	}

}
