// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: edgenetflix.proto

package com.edgenetflix.grpc;

public final class Edgenetflix {
  private Edgenetflix() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_edgenetflix_grpc_ChunkRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_edgenetflix_grpc_ChunkRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_edgenetflix_grpc_ChunkResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_edgenetflix_grpc_ChunkResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_edgenetflix_grpc_SeederRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_edgenetflix_grpc_SeederRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_edgenetflix_grpc_SeederResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_edgenetflix_grpc_SeederResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_edgenetflix_grpc_Void_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_edgenetflix_grpc_Void_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_com_edgenetflix_grpc_SeederList_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_com_edgenetflix_grpc_SeederList_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\021edgenetflix.proto\022\024com.edgenetflix.grp" +
      "c\"\"\n\014ChunkRequest\022\022\n\nchunkIndex\030\001 \001(\005\"B\n" +
      "\rChunkResponse\022\r\n\005chunk\030\001 \001(\014\022\021\n\tseqNumb" +
      "er\030\002 \001(\005\022\017\n\007payload\030\003 \001(\003\"!\n\rSeederReque" +
      "st\022\020\n\010fileName\030\001 \001(\t\"w\n\016SeederResponse\022\n" +
      "\n\002ip\030\001 \001(\t\022\014\n\004port\030\002 \001(\003\022\022\n\nfileLength\030\003" +
      " \001(\003\022\020\n\010fileHash\030\004 \001(\t\022\022\n\nchunkLegth\030\005 \001" +
      "(\003\022\021\n\tchunkHash\030\006 \003(\t\"\006\n\004Void\"\034\n\nSeederL" +
      "ist\022\016\n\006seeder\030\001 \003(\t2\354\002\n\016DownloadSevice\022U" +
      "\n\010getChunk\022\".com.edgenetflix.grpc.ChunkR",
      "equest\032#.com.edgenetflix.grpc.ChunkRespo" +
      "nse0\001\022_\n\020requestForSeeder\022#.com.edgenetf" +
      "lix.grpc.SeederRequest\032$.com.edgenetflix" +
      ".grpc.SeederResponse0\001\022Q\n\016shutdownSeeder" +
      "\022#.com.edgenetflix.grpc.SeederRequest\032\032." +
      "com.edgenetflix.grpc.Void\022O\n\rgetSeederLi" +
      "st\022\032.com.edgenetflix.grpc.Void\032 .com.edg" +
      "enetflix.grpc.SeederList0\001B\002P\001b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_com_edgenetflix_grpc_ChunkRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_com_edgenetflix_grpc_ChunkRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_edgenetflix_grpc_ChunkRequest_descriptor,
        new java.lang.String[] { "ChunkIndex", });
    internal_static_com_edgenetflix_grpc_ChunkResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_com_edgenetflix_grpc_ChunkResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_edgenetflix_grpc_ChunkResponse_descriptor,
        new java.lang.String[] { "Chunk", "SeqNumber", "Payload", });
    internal_static_com_edgenetflix_grpc_SeederRequest_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_com_edgenetflix_grpc_SeederRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_edgenetflix_grpc_SeederRequest_descriptor,
        new java.lang.String[] { "FileName", });
    internal_static_com_edgenetflix_grpc_SeederResponse_descriptor =
      getDescriptor().getMessageTypes().get(3);
    internal_static_com_edgenetflix_grpc_SeederResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_edgenetflix_grpc_SeederResponse_descriptor,
        new java.lang.String[] { "Ip", "Port", "FileLength", "FileHash", "ChunkLegth", "ChunkHash", });
    internal_static_com_edgenetflix_grpc_Void_descriptor =
      getDescriptor().getMessageTypes().get(4);
    internal_static_com_edgenetflix_grpc_Void_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_edgenetflix_grpc_Void_descriptor,
        new java.lang.String[] { });
    internal_static_com_edgenetflix_grpc_SeederList_descriptor =
      getDescriptor().getMessageTypes().get(5);
    internal_static_com_edgenetflix_grpc_SeederList_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_com_edgenetflix_grpc_SeederList_descriptor,
        new java.lang.String[] { "Seeder", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
