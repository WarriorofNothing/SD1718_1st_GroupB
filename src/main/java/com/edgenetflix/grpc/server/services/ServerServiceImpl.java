package com.edgenetflix.grpc.server.services;

import com.edgenetflix.grpc.*;
import com.edgenetflix.grpc.Void;
import com.edgenetflix.grpc.server.seeder.Chunk;
import com.edgenetflix.grpc.server.seeder.InitialSeeder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ServerServiceImpl extends DownloadSeviceGrpc.DownloadSeviceImplBase{
    private HashMap<String, InitialSeeder> seeders;

    public ServerServiceImpl(HashMap<String, InitialSeeder> seeders){
        this.seeders = seeders;
    }

    @Override
    public void requestForSeeder(SeederRequest request, StreamObserver<SeederResponse> responseObserver) {
        InitialSeeder seeder = seeders.get(request.getFileName());

        responseObserver.onNext(SeederResponse.newBuilder()
                .setIp("35.184.72.242")
                .build());
        responseObserver.onNext(SeederResponse.newBuilder()
                .setPort(seeder.getPort())
                .build());
        responseObserver.onNext(SeederResponse.newBuilder()
                .setFileLength(seeder.getFile().length())
                .build());
        responseObserver.onNext(SeederResponse.newBuilder()
                .setFileHash(seeder.getFileHash())
                .build());
        responseObserver.onNext(SeederResponse.newBuilder()
                .setChunkLegth(seeder.getChunkSize())
                .build());
        List<String> hashList = new ArrayList<>();
        for(Chunk c : seeder.getChunks()){
            hashList.add(c.getHash());
        }
        responseObserver.onNext(SeederResponse.newBuilder()
                .addAllChunkHash(hashList)
                .build());

        responseObserver.onCompleted();

        try {
            seeder.startSeeding();
        } catch (IOException|InterruptedException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void shutdownSeeder(SeederRequest request, StreamObserver<Void> responseObserver) {
        InitialSeeder seeder = seeders.get(request.getFileName());

        responseObserver.onNext(Void.newBuilder()
                .build());

        responseObserver.onCompleted();

        seeder.stopSeeding();
    }

    @Override
    public void getSeederList(Void request, StreamObserver<SeederList> responseObserver) {
        List<String> seederList = new ArrayList<>();
        for(String file : seeders.keySet()){
            String s = "{ \"fileName\": " + file + "," +
                    "\"ip\": " + "35.184.72.242" + ", " +
                    "\"port\": " + seeders.get(file).getPort() + ", " +
                    "\"seeding\": " + seeders.get(file).isSeeding() + " }";
            seederList.add(s);
        }

        responseObserver.onNext(SeederList.newBuilder()
                .addAllSeeder(seederList)
                .build());

        responseObserver.onCompleted();
    }
}
