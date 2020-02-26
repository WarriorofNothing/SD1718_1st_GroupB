package com.edgenetflix.cli.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class Response {

    //Response to metadata get
    private long file_length;
    private String file_hash;
    private long chunk_length;
    private int number_chunks;
    private List<String> hashList;

    //Response to chunk info get
    private List<Peer> peers;
    private Peer seeder;

    public long getFileLength() {
        return file_length;
    }

    public String getFileHash() {
        return file_hash;
    }

    public long getChunkLength() {
        return chunk_length;
    }

    public int getNumberOfhunks() {
        return number_chunks;
    }

    public List<String> getHashList() {
        return hashList;
    }

    public List<Peer> getPeers() {
        return peers;
    }

    public Peer getSeeder() {
        return seeder;
    }

    public void parseMetadata(String json_response){

        JsonObject metadataJson = JsonParser.parseString(json_response).getAsJsonObject();
        file_length = metadataJson.get("fileLength").getAsLong();
        file_hash = metadataJson.get("fileHash").getAsString();
        chunk_length = metadataJson.get("chunkLength").getAsLong();
        number_chunks = metadataJson.get("numberOfChunks").getAsInt();
        JsonArray hashJsonArray = metadataJson.get("hashList").getAsJsonArray();

        hashList = new ArrayList<>();
        for(int i=0; i < hashJsonArray.size(); i++){
            JsonObject hashJson = hashJsonArray.get(i).getAsJsonObject();
            hashList.add(hashJson.get(String.valueOf(i+1)).getAsString());
        }

    }
    /**
     * @param json_response Response in json format
     */
    public void parseChunkInfo(String json_response){
        JsonObject chunkinfoJson = JsonParser.parseString(json_response).getAsJsonObject();
        JsonObject seederJson = chunkinfoJson.get("seeder").getAsJsonObject();
        JsonArray leechersJsonArray = chunkinfoJson.get("leechers").getAsJsonArray();

        seeder = new Peer(seederJson.get("ip").getAsString(),seederJson.get("port").getAsInt());
        peers = new ArrayList<>();

        for(int i=0; i < leechersJsonArray.size(); i++){
            JsonObject leecher = leechersJsonArray.get(i).getAsJsonObject();
            Peer peer = new Peer(leecher.get("ip").getAsString(), leecher.get("port").getAsInt());
            JsonArray chunksJsonArray = leecher.get("chunks").getAsJsonArray();
            for(int j=0; j < chunksJsonArray.size();j++)
                peer.getAvailableChunks().add(chunksJsonArray.get(j).getAsInt());

            peers.add(peer);
        }

    }

    public void parseSeederList(String json_response){
        JsonObject listJson = JsonParser.parseString(json_response).getAsJsonObject();
        JsonArray seedersJsonArray = listJson.get("seederList").getAsJsonArray();

        for(int i=0; i < seedersJsonArray.size(); i++){
            JsonObject seeder = seedersJsonArray.get(i).getAsJsonObject();
            String s = "Seeder["+ seeder.get("ip").getAsString() +":"+seeder.get("port").getAsInt() +"]" + " - file: " + seeder.get("fileName").getAsString();
            StringBuilder sb = new StringBuilder(s);

            if(seeder.get("seeding").getAsBoolean())
                sb.append(" (Seeding)");
            else{
                sb.append(" (Not Seeding)");
            }

            System.out.println(sb.toString());
        }

    }

    public void printMetainfo(){
        System.out.println("Metainfo: ");
        System.out.println("    File hash: " + file_hash);
        System.out.println("    File length: " + file_length);
        System.out.println("    Chunk length: " + chunk_length);
        System.out.println("    Number of chunks: " + number_chunks);
        for (String h : hashList){
            System.out.println("    Hash: " + h);
        }

        System.out.println();
    }

    public void printChunkInfo(){
        System.out.println("Chunk Info: ");
        System.out.println("Seeder: " + seeder.getIp()+":"+seeder.getPort());
        for(Peer p : peers) {
            System.out.print("Leecher: " + p.getIp()+":"+p.getPort()+" - chunks: ");
            for (int c : p.getAvailableChunks()){
                System.out.print(c + " ");
            }

            System.out.println();
        }

        System.out.println();
    }
}
