package com.edgenetflix.rest.db;

import java.util.List;

/**
 *Class Seeder keeps the information about the seeder that is seeding the file (seed), as well as the
 * file (seed) information such as the length, hash and the number of chunks (and its hashes) that it was divided
 */
public class Seeder {
    private String ip;
    private long port;
    private long seed_length;
    private String seed_hash;
    private long chunk_length;
    private List<String> chunkHashList;

    Seeder(String ip, long port, long seed_length, String seed_hash, long chunk_length, List<String> chunkHashList){
        this.ip = ip;
        this.port = port;
        this.seed_length = seed_length;
        this.seed_hash = seed_hash;
        this.chunk_length = chunk_length;
        this.chunkHashList = chunkHashList;
    }

    public String getIp() {
        return ip;
    }

    long getPort() {
        return port;
    }

    long getSeedLength() {
        return seed_length;
    }

    String getSeedHash() {
        return seed_hash;
    }

     long getChunkLength() {
        return chunk_length;
    }

    List<String> getChunkHashList() {
        return chunkHashList;
    }

    @Override
    public String toString() {
        return "{ \"ip\": " + ip + ", \"port\": " + port + " }";
    }
}
