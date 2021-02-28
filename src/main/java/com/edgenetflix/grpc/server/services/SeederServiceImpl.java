package com.edgenetflix.grpc.server.services;

import com.edgenetflix.grpc.ChunkRequest;
import com.edgenetflix.grpc.ChunkResponse;
import com.edgenetflix.grpc.DownloadSeviceGrpc;
import com.edgenetflix.grpc.server.seeder.Chunk;
import com.edgenetflix.grpc.server.seeder.InitialSeeder;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

import java.util.List;

public class SeederServiceImpl extends DownloadSeviceGrpc.DownloadSeviceImplBase{
    private InitialSeeder seeder;

    public SeederServiceImpl(InitialSeeder seeder){
        this.seeder = seeder;
    }

    @Override
    public void getChunk(ChunkRequest request, StreamObserver<ChunkResponse> responseObserver) {
        int index = request.getChunkIndex();
        List<Chunk> chunks = seeder.getChunks();
        Chunk requested_chunk = chunks.get(index-1);

        responseObserver.onNext(ChunkResponse.newBuilder()
                .setChunk(ByteString.copyFrom(requested_chunk.getBytes()))
                .build());
        responseObserver.onNext(ChunkResponse.newBuilder()
                .setSeqNumber(requested_chunk.getChunkSequenceNumber())
                .build());
        responseObserver.onNext(ChunkResponse.newBuilder()
                .setPayload(requested_chunk.getPayload())
                .build());

        responseObserver.onCompleted();
    }
}
