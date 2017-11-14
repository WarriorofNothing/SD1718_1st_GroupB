package com.edgenetflix.cli.rest;

import com.edgenetflix.cli.json.Response;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionService {
    private final String SERVER_URL;

    public ConnectionService(String server_url) {
        SERVER_URL = server_url;
    }

    /**
     * @param file  Dile name
     * @param client_id Client unique id
     * @param ip Client-peer ip
     * @param port Client-peer port
     * @return  Returns the file information using class Response
     * @throws Exception
     *
     * This method is used to get basic information (metadata about the file requested to download.
     * Client sends a GET request to the Web Server, passing the required information about the client in the query
     * Web Server responds with a message containing the file information in a json string format.
     */
    public Response getMetadata(String file, String client_id, String ip, int port) throws Exception {
        String resource = "/download/metainfo";
        String query = "?file=" + file + "&id=" + client_id + "&ip=" + ip + "&port=" + port;

        URL url = new URL(SERVER_URL + resource + query);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        InputStreamReader isr = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(isr);

        String line;
        StringBuilder json_response = new StringBuilder();

        //Read the server response line by line and for each line append to the string buffer to return the response
        while ((line = br.readLine()) != null) {
            json_response.append(line);
        }

        br.close();

        //System.out.println(json_response.toString());

        Response response = new Response();
        response.parseMetadata(json_response.toString());

        return response;
    }

    /**
     * @param file File name
     * @param nseq Chunk index (sequence number)
     * @return Returns the seeder and the peers information using class Response
     * @throws Exception
     *
     * Makes a get request to obtain the seeder and the peers of the specific chunk
     */
    public Response getChunkInfo(String file, String id, int nseq) throws Exception {
        String resource = "/download/chunk";
        String query = "?file=" + file + "&id=" + id + "&index=" + nseq;

        URL url = new URL(SERVER_URL + resource + query);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        InputStreamReader isr = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(isr);

        String line;
        StringBuilder json_response = new StringBuilder();

        while ((line = br.readLine()) != null) {
            json_response.append(line);
        }

        br.close();

        //System.out.println(json_response.toString());

        Response response = new Response();
        response.parseChunkInfo(json_response.toString());

        return response;
    }

    /**
     *
     * @param file  File name
     * @param id    Client id
     * @param nseq  Chunk index (sequence number)
     * @param completed Inform that this chunk was the last one. Client has all the chunks, therefore the download is complete.
     */
    public void sendAck(String file, String id, int nseq, boolean completed) throws Exception{
        String resource = "/download/ack";
        String query = "?file=" + file + "&id=" + id + "&index=" + nseq + "&completed=" + completed;

        URL url = new URL(SERVER_URL + resource + query);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        InputStreamReader isr = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(isr);

        String line;
        StringBuilder response = new StringBuilder();

        while ((line = br.readLine()) != null) {
            response.append(line);
        }

        br.close();
    }

    public void getList() throws Exception{
        String resource = "/list";
        URL url = new URL(SERVER_URL + resource);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        InputStreamReader isr = new InputStreamReader(conn.getInputStream());
        BufferedReader br = new BufferedReader(isr);

        String line;
        StringBuilder json_response = new StringBuilder();

        while ((line = br.readLine()) != null) {
            json_response.append(line);
        }

        br.close();

        //System.out.println(json_response.toString());

        Response response = new Response();
        response.parseSeederList(json_response.toString());
    }
}