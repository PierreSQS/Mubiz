package com.mubiz.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class MubizHttpClient {
	//private String url;

	public final static String MUBIZ_ROOT_URL = "http://bitcoin.mubiz.com/";
	public final static String MUBIZ_BLOCKS_URL = MUBIZ_ROOT_URL + "blocks/";
	public final static String MUBIZ_BLOCK_INDEX_URL = MUBIZ_ROOT_URL + "block_index/";
	public final static String MUBIZ_BLOCK_HASH_URL = MUBIZ_ROOT_URL + "block_hash/";
	
	public MubizHttpClient(){
		
	}

//	public MubizHttpClient(String url) {
//		super();
//		this.url = url;
//		System.out.println("\nthe called URL: " + url);
//	}
//
	public String connectToWS(String url) throws ClientProtocolException, IOException {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);

			System.out.println("\nExecuting request " + httpget.getRequestLine());

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				@Override
				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}

			};
			String responseBody = httpclient.execute(httpget, responseHandler);
			System.out.println("---------------- the Resp. Body ------------------------");
			System.out.println(responseBody);

			return responseBody;
			
		} finally {
			httpclient.close();
		}

	}

}