package com.robinrrr10.wiremock;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

/**
 * Unit test for simple App.
 */
public class AppTest {
	
	//create object for  wiremockserver
	WireMockServer wiremockserver = new WireMockServer();
	
	//Below method is example for apache http client
	@Test(enabled=false)
	public void httpClientTest() throws ClientProtocolException, IOException {
		String url = "https://reqres.in/api/users/2";
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = client.execute(httpGet);
		System.out.println("Response body is:"+EntityUtils.toString(response.getEntity()));
		response.close();
		client.close();
	}
	
	//Below example is for wiremock
	@Test
	public void testWiremock() throws ClientProtocolException, IOException {
		
		//start wiremock
		wiremockserver.start();
		
		//create and run the api in wiremock
		wiremockserver.stubFor(WireMock.get(WireMock.urlEqualTo("/api/sample")).willReturn(WireMock.aResponse().withHeader("Content-Type", "application/json").withBody("{\"name\":\"wiremock\"}")));
		
		//Below all lins are for hitting the api using apache http client and printing the response
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("http://localhost:8080/api/sample");
		CloseableHttpResponse response = client.execute(httpGet);
		System.out.println("Response body is========================>:"+EntityUtils.toString(response.getEntity()));
		response.close();
		client.close();
		wiremockserver.stop();
	}
   
}
