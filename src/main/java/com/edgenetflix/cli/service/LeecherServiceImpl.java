package com.edgenetflix.cli.service;

import com.edgenetflix.grpc.ChunkRequest;
import com.edgenetflix.grpc.ChunkResponse;
import com.edgenetflix.grpc.DownloadSeviceGrpc;
import com.google.protobuf.ByteString;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;

public class LeecherServiceImpl extends DownloadSeviceGrpc.DownloadSeviceImplBase {
    private HashMap<Integer,byte[]> chunks_available;

    public LeecherServiceImpl(HashMap<Integer,byte[]> chunks_available){ this.chunks_available = chunks_available; }

    @Override
    public void getChunk(ChunkRequest request, StreamObserver<ChunkResponse> responseObserver) {

        if(chunks_available.containsKey(request.getChunkIndex())){
            responseObserver.onNext(ChunkResponse.newBuilder()
                    .setChunk(ByteString.copyFrom(chunks_available.get(request.getChunkIndex())))
                    .build());
            responseObserver.onNext(ChunkResponse.newBuilder()
                    .setSeqNumber(request.getChunkIndex())
                    .build());
            responseObserver.onNext(ChunkResponse.newBuilder()
                    .setPayload(chunks_available.get(request.getChunkIndex()).length)
                    .build());
        }
        else {
            responseObserver.onNext(ChunkResponse.newBuilder()
                    .setChunk(ByteString.copyFrom(new byte[0]))
                    .build());
            responseObserver.onNext(ChunkResponse.newBuilder()
                    .setSeqNumber(-1)
                    .build());
            responseObserver.onNext(ChunkResponse.newBuilder()
                    .setPayload(0)
                    .build());
        }

        responseObserver.onCompleted();
    }
}
