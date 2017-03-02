package com.mubiz.wsclient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.http.client.ClientProtocolException;

import com.mubiz.dao.Block;
import com.mubiz.dao.Hash;
import com.mubiz.dao.NumberOfBlocks;
import com.mubiz.database.BlockTable;
import com.mubiz.database.MySQLUtilities;
import com.mubiz.httpclient.MubizHttpClient;
import com.mubiz.jsonparser.MubizJsonParser;

public class MubizWSTestClient {

	public static void main(String[] args) throws ClientProtocolException, IOException, SQLException {

		// Initializing the Http Client
		MubizHttpClient myMubizHttpClient = new MubizHttpClient();

		// Initializing Database Connection

		MySQLUtilities myMySQLUtilities = new MySQLUtilities("mysql-sample-properties.xml");
		Connection myConnection = myMySQLUtilities.getConnection();

		BlockTable myBlockTable = new BlockTable(myConnection, myMySQLUtilities.dbName, myMySQLUtilities.dbms);

		// get total number of blocks or the highest index
		String blocksResp = myMubizHttpClient.connectToWS(MubizHttpClient.MUBIZ_BLOCKS_URL);

		NumberOfBlocks blocksCounter = MubizJsonParser.deserializeRespFromJSON(blocksResp, NumberOfBlocks.class);

		
		int totalBlocks = blocksCounter.getBlocks();
		
		System.out.println("the number of blocks actually: "+totalBlocks);
		
		

//		for (int i = 1; i <= totalBlocks; i++) {
		
		for (int i = 1; i <= 2000; i++) {

//		for (int i = 1; i <= totalBlocks; i++) {
			// get JSON Hash from WS
			String hashResp = myMubizHttpClient.connectToWS(MubizHttpClient.MUBIZ_BLOCK_INDEX_URL + i + "/");
			Hash hash = MubizJsonParser.deserializeRespFromJSON(hashResp, Hash.class);
			System.out.println("the data of the Block (block#" + i + ")");
			// get block data
			String blockResp = myMubizHttpClient
					.connectToWS(MubizHttpClient.MUBIZ_BLOC_HASH_URL + hash.getBlock_hash() + "/");
			Block block = MubizJsonParser.deserializeRespFromJSON(blockResp, Block.class);
			System.out.println("inserting block : block#" + i);
			myBlockTable.insertRow(block);
		}
	}

}
