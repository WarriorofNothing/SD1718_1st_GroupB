// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edgenetflix.proto

package com.edgenetflix.grpc;

public interface ChunkResponseOrBuilder extends
    // @@protoc_insertion_point(interface_extends:com.edgenetflix.grpc.ChunkResponse)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bytes chunk = 1;</code>
   */
  com.google.protobuf.ByteString getChunk();

  /**
   * <code>int32 seqNumber = 2;</code>
   */
  int getSeqNumber();

  /**
   * <code>int64 payload = 3;</code>
   */
  long getPayload();
}
