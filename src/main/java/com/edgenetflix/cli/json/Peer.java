package com.edgenetflix.cli.json;

import java.util.HashSet;
import java.util.Set;

public class Peer {
    private String ip;
    private int port;
    private Set<Integer> availableChunks;


    Peer(String ip, int port){
        this.ip = ip;
        this.port = port;

        availableChunks = new HashSet<>();
    }

    public Set<Integer> getAvailableChunks() {
        return availableChunks;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }
}
