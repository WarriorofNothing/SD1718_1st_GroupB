package com.edgenetflix.rest.db;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Leecher {
    private String id;
    private String ip;
    private int port;
    private Set<Integer> chunksIndexSet;

    public Leecher(String id, String ip, int port){
        this.id = id;

        this.ip = ip;
        this.port = port;

        chunksIndexSet = new HashSet<Integer>();
    }

    String getId() {
        return id;
    }

    String getIp() {
        return ip;
    }

    int getPort() {
        return port;
    }

    Set<Integer> getChunksSet() {
        return chunksIndexSet;
    }

    @Override
    public String toString() {
        StringBuilder chunksArrayJson = new StringBuilder("[ ");
        for(int index : chunksIndexSet){
            chunksArrayJson.append(index);
            chunksArrayJson.append(", ");
        }

        if(!chunksIndexSet.isEmpty())
            chunksArrayJson.deleteCharAt(chunksArrayJson.length() - 2);     //Remove the last comma

        chunksArrayJson.append("]");

        return "{ \"ip\": " + ip + ", " +
                "\"port\": " + port + ", " +
                "\"chunks\": " + chunksArrayJson.toString() + " }";
    }
}
