package com.edgenetflix.grpc.server.seeder;

public class Chunk {
    private byte[] bytes;
    private String hash;
    private int sequence_number;
    private long payload;

    Chunk(byte[] bytes, String hash, int sequence_number){
        this.bytes = bytes;
        this.hash = hash;
        this.sequence_number = sequence_number;
        this.payload = bytes.length;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getHash() {
        return hash;
    }

    public int getChunkSequenceNumber() {
        return sequence_number;
    }

    public long getPayload(){
        return payload;
    }
}
