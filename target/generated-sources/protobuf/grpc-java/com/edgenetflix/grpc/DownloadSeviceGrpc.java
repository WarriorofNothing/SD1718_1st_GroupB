package com.edgenetflix.grpc;

import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.7.0)",
    comments = "Source: edgenetflix.proto")
public final class DownloadSeviceGrpc {

  private DownloadSeviceGrpc() {}

  public static final String SERVICE_NAME = "com.edgenetflix.grpc.DownloadSevice";

  // Static method descriptors that strictly reflect the proto.
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.edgenetflix.grpc.ChunkRequest,
      com.edgenetflix.grpc.ChunkResponse> METHOD_GET_CHUNK =
      io.grpc.MethodDescriptor.<com.edgenetflix.grpc.ChunkRequest, com.edgenetflix.grpc.ChunkResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "com.edgenetflix.grpc.DownloadSevice", "getChunk"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.edgenetflix.grpc.ChunkRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.edgenetflix.grpc.ChunkResponse.getDefaultInstance()))
          .setSchemaDescriptor(new DownloadSeviceMethodDescriptorSupplier("getChunk"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.edgenetflix.grpc.SeederRequest,
      com.edgenetflix.grpc.SeederResponse> METHOD_REQUEST_FOR_SEEDER =
      io.grpc.MethodDescriptor.<com.edgenetflix.grpc.SeederRequest, com.edgenetflix.grpc.SeederResponse>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "com.edgenetflix.grpc.DownloadSevice", "requestForSeeder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.edgenetflix.grpc.SeederRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.edgenetflix.grpc.SeederResponse.getDefaultInstance()))
          .setSchemaDescriptor(new DownloadSeviceMethodDescriptorSupplier("requestForSeeder"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.edgenetflix.grpc.SeederRequest,
      com.edgenetflix.grpc.Void> METHOD_SHUTDOWN_SEEDER =
      io.grpc.MethodDescriptor.<com.edgenetflix.grpc.SeederRequest, com.edgenetflix.grpc.Void>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
          .setFullMethodName(generateFullMethodName(
              "com.edgenetflix.grpc.DownloadSevice", "shutdownSeeder"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.edgenetflix.grpc.SeederRequest.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.edgenetflix.grpc.Void.getDefaultInstance()))
          .setSchemaDescriptor(new DownloadSeviceMethodDescriptorSupplier("shutdownSeeder"))
          .build();
  @io.grpc.ExperimentalApi("https://github.com/grpc/grpc-java/issues/1901")
  public static final io.grpc.MethodDescriptor<com.edgenetflix.grpc.Void,
      com.edgenetflix.grpc.SeederList> METHOD_GET_SEEDER_LIST =
      io.grpc.MethodDescriptor.<com.edgenetflix.grpc.Void, com.edgenetflix.grpc.SeederList>newBuilder()
          .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
          .setFullMethodName(generateFullMethodName(
              "com.edgenetflix.grpc.DownloadSevice", "getSeederList"))
          .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.edgenetflix.grpc.Void.getDefaultInstance()))
          .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
              com.edgenetflix.grpc.SeederList.getDefaultInstance()))
          .setSchemaDescriptor(new DownloadSeviceMethodDescriptorSupplier("getSeederList"))
          .build();

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static DownloadSeviceStub newStub(io.grpc.Channel channel) {
    return new DownloadSeviceStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static DownloadSeviceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new DownloadSeviceBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static DownloadSeviceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new DownloadSeviceFutureStub(channel);
  }

  /**
   */
  public static abstract class DownloadSeviceImplBase implements io.grpc.BindableService {

    /**
     */
    public void getChunk(com.edgenetflix.grpc.ChunkRequest request,
        io.grpc.stub.StreamObserver<com.edgenetflix.grpc.ChunkResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_CHUNK, responseObserver);
    }

    /**
     */
    public void requestForSeeder(com.edgenetflix.grpc.SeederRequest request,
        io.grpc.stub.StreamObserver<com.edgenetflix.grpc.SeederResponse> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_REQUEST_FOR_SEEDER, responseObserver);
    }

    /**
     */
    public void shutdownSeeder(com.edgenetflix.grpc.SeederRequest request,
        io.grpc.stub.StreamObserver<com.edgenetflix.grpc.Void> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_SHUTDOWN_SEEDER, responseObserver);
    }

    /**
     */
    public void getSeederList(com.edgenetflix.grpc.Void request,
        io.grpc.stub.StreamObserver<com.edgenetflix.grpc.SeederList> responseObserver) {
      asyncUnimplementedUnaryCall(METHOD_GET_SEEDER_LIST, responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            METHOD_GET_CHUNK,
            asyncServerStreamingCall(
              new MethodHandlers<
                com.edgenetflix.grpc.ChunkRequest,
                com.edgenetflix.grpc.ChunkResponse>(
                  this, METHODID_GET_CHUNK)))
          .addMethod(
            METHOD_REQUEST_FOR_SEEDER,
            asyncServerStreamingCall(
              new MethodHandlers<
                com.edgenetflix.grpc.SeederRequest,
                com.edgenetflix.grpc.SeederResponse>(
                  this, METHODID_REQUEST_FOR_SEEDER)))
          .addMethod(
            METHOD_SHUTDOWN_SEEDER,
            asyncUnaryCall(
              new MethodHandlers<
                com.edgenetflix.grpc.SeederRequest,
                com.edgenetflix.grpc.Void>(
                  this, METHODID_SHUTDOWN_SEEDER)))
          .addMethod(
            METHOD_GET_SEEDER_LIST,
            asyncServerStreamingCall(
              new MethodHandlers<
                com.edgenetflix.grpc.Void,
                com.edgenetflix.grpc.SeederList>(
                  this, METHODID_GET_SEEDER_LIST)))
          .build();
    }
  }

  /**
   */
  public static final class DownloadSeviceStub extends io.grpc.stub.AbstractStub<DownloadSeviceStub> {
    private DownloadSeviceStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DownloadSeviceStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DownloadSeviceStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DownloadSeviceStub(channel, callOptions);
    }

    /**
     */
    public void getChunk(com.edgenetflix.grpc.ChunkRequest request,
        io.grpc.stub.StreamObserver<com.edgenetflix.grpc.ChunkResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_GET_CHUNK, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void requestForSeeder(com.edgenetflix.grpc.SeederRequest request,
        io.grpc.stub.StreamObserver<com.edgenetflix.grpc.SeederResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_REQUEST_FOR_SEEDER, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void shutdownSeeder(com.edgenetflix.grpc.SeederRequest request,
        io.grpc.stub.StreamObserver<com.edgenetflix.grpc.Void> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(METHOD_SHUTDOWN_SEEDER, getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getSeederList(com.edgenetflix.grpc.Void request,
        io.grpc.stub.StreamObserver<com.edgenetflix.grpc.SeederList> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(METHOD_GET_SEEDER_LIST, getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class DownloadSeviceBlockingStub extends io.grpc.stub.AbstractStub<DownloadSeviceBlockingStub> {
    private DownloadSeviceBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DownloadSeviceBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DownloadSeviceBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DownloadSeviceBlockingStub(channel, callOptions);
    }

    /**
     */
    public java.util.Iterator<com.edgenetflix.grpc.ChunkResponse> getChunk(
        com.edgenetflix.grpc.ChunkRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_GET_CHUNK, getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.edgenetflix.grpc.SeederResponse> requestForSeeder(
        com.edgenetflix.grpc.SeederRequest request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_REQUEST_FOR_SEEDER, getCallOptions(), request);
    }

    /**
     */
    public com.edgenetflix.grpc.Void shutdownSeeder(com.edgenetflix.grpc.SeederRequest request) {
      return blockingUnaryCall(
          getChannel(), METHOD_SHUTDOWN_SEEDER, getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<com.edgenetflix.grpc.SeederList> getSeederList(
        com.edgenetflix.grpc.Void request) {
      return blockingServerStreamingCall(
          getChannel(), METHOD_GET_SEEDER_LIST, getCallOptions(), request);
    }
  }

  /**
   */
  public static final class DownloadSeviceFutureStub extends io.grpc.stub.AbstractStub<DownloadSeviceFutureStub> {
    private DownloadSeviceFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private DownloadSeviceFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected DownloadSeviceFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new DownloadSeviceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.edgenetflix.grpc.Void> shutdownSeeder(
        com.edgenetflix.grpc.SeederRequest request) {
      return futureUnaryCall(
          getChannel().newCall(METHOD_SHUTDOWN_SEEDER, getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_CHUNK = 0;
  private static final int METHODID_REQUEST_FOR_SEEDER = 1;
  private static final int METHODID_SHUTDOWN_SEEDER = 2;
  private static final int METHODID_GET_SEEDER_LIST = 3;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final DownloadSeviceImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(DownloadSeviceImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_CHUNK:
          serviceImpl.getChunk((com.edgenetflix.grpc.ChunkRequest) request,
              (io.grpc.stub.StreamObserver<com.edgenetflix.grpc.ChunkResponse>) responseObserver);
          break;
        case METHODID_REQUEST_FOR_SEEDER:
          serviceImpl.requestForSeeder((com.edgenetflix.grpc.SeederRequest) request,
              (io.grpc.stub.StreamObserver<com.edgenetflix.grpc.SeederResponse>) responseObserver);
          break;
        case METHODID_SHUTDOWN_SEEDER:
          serviceImpl.shutdownSeeder((com.edgenetflix.grpc.SeederRequest) request,
              (io.grpc.stub.StreamObserver<com.edgenetflix.grpc.Void>) responseObserver);
          break;
        case METHODID_GET_SEEDER_LIST:
          serviceImpl.getSeederList((com.edgenetflix.grpc.Void) request,
              (io.grpc.stub.StreamObserver<com.edgenetflix.grpc.SeederList>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class DownloadSeviceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    DownloadSeviceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.edgenetflix.grpc.Edgenetflix.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("DownloadSevice");
    }
  }

  private static final class DownloadSeviceFileDescriptorSupplier
      extends DownloadSeviceBaseDescriptorSupplier {
    DownloadSeviceFileDescriptorSupplier() {}
  }

  private static final class DownloadSeviceMethodDescriptorSupplier
      extends DownloadSeviceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    DownloadSeviceMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (DownloadSeviceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new DownloadSeviceFileDescriptorSupplier())
              .addMethod(METHOD_GET_CHUNK)
              .addMethod(METHOD_REQUEST_FOR_SEEDER)
              .addMethod(METHOD_SHUTDOWN_SEEDER)
              .addMethod(METHOD_GET_SEEDER_LIST)
              .build();
        }
      }
    }
    return result;
  }
}
