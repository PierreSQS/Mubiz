package com.mubiz.wsclient;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.apache.http.client.ClientProtocolException;

import com.mubiz.dao.Block;
import com.mubiz.dao.Hash;
import com.mubiz.dao.NumberOfBlock;
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
		String blockIndexMax = myMubizHttpClient.connectToWS(MubizHttpClient.MUBIZ_BLOCKS_URL);

		NumberOfBlock blocksCounter = MubizJsonParser.deserializeRespFromJSON(blockIndexMax, NumberOfBlock.class);

		// get hash
		String hashResp = myMubizHttpClient
				.connectToWS(MubizHttpClient.MUBIZ_BLOCK_INDEX_URL + blocksCounter.getBlocks() + "/");

		Hash hash = MubizJsonParser.deserializeRespFromJSON(hashResp, Hash.class);

		// get block data
		String blockResp = myMubizHttpClient
				.connectToWS(MubizHttpClient.MUBIZ_BLOC_HASH_URL + hash.getBlock_hash() + "/");
		
		Block block = MubizJsonParser.deserializeRespFromJSON(blockResp, Block.class);
		
		System.out.println(block);
	}

}
