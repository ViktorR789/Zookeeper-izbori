package rs.ac.fink.izbori.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.56.0)",
    comments = "Source: izbori.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ReplicatedLogServiceGrpc {

  private ReplicatedLogServiceGrpc() {}

  public static final String SERVICE_NAME = "izbori.ReplicatedLogService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<rs.ac.fink.izbori.grpc.LogEntry,
      rs.ac.fink.izbori.grpc.LogResponse> getAppendLogMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AppendLog",
      requestType = rs.ac.fink.izbori.grpc.LogEntry.class,
      responseType = rs.ac.fink.izbori.grpc.LogResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.ac.fink.izbori.grpc.LogEntry,
      rs.ac.fink.izbori.grpc.LogResponse> getAppendLogMethod() {
    io.grpc.MethodDescriptor<rs.ac.fink.izbori.grpc.LogEntry, rs.ac.fink.izbori.grpc.LogResponse> getAppendLogMethod;
    if ((getAppendLogMethod = ReplicatedLogServiceGrpc.getAppendLogMethod) == null) {
      synchronized (ReplicatedLogServiceGrpc.class) {
        if ((getAppendLogMethod = ReplicatedLogServiceGrpc.getAppendLogMethod) == null) {
          ReplicatedLogServiceGrpc.getAppendLogMethod = getAppendLogMethod =
              io.grpc.MethodDescriptor.<rs.ac.fink.izbori.grpc.LogEntry, rs.ac.fink.izbori.grpc.LogResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "AppendLog"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.ac.fink.izbori.grpc.LogEntry.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.ac.fink.izbori.grpc.LogResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ReplicatedLogServiceMethodDescriptorSupplier("AppendLog"))
              .build();
        }
      }
    }
    return getAppendLogMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.ac.fink.izbori.grpc.LeaderRequest,
      rs.ac.fink.izbori.grpc.LeaderInfo> getGetLeaderInfoMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetLeaderInfo",
      requestType = rs.ac.fink.izbori.grpc.LeaderRequest.class,
      responseType = rs.ac.fink.izbori.grpc.LeaderInfo.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.ac.fink.izbori.grpc.LeaderRequest,
      rs.ac.fink.izbori.grpc.LeaderInfo> getGetLeaderInfoMethod() {
    io.grpc.MethodDescriptor<rs.ac.fink.izbori.grpc.LeaderRequest, rs.ac.fink.izbori.grpc.LeaderInfo> getGetLeaderInfoMethod;
    if ((getGetLeaderInfoMethod = ReplicatedLogServiceGrpc.getGetLeaderInfoMethod) == null) {
      synchronized (ReplicatedLogServiceGrpc.class) {
        if ((getGetLeaderInfoMethod = ReplicatedLogServiceGrpc.getGetLeaderInfoMethod) == null) {
          ReplicatedLogServiceGrpc.getGetLeaderInfoMethod = getGetLeaderInfoMethod =
              io.grpc.MethodDescriptor.<rs.ac.fink.izbori.grpc.LeaderRequest, rs.ac.fink.izbori.grpc.LeaderInfo>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetLeaderInfo"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.ac.fink.izbori.grpc.LeaderRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.ac.fink.izbori.grpc.LeaderInfo.getDefaultInstance()))
              .setSchemaDescriptor(new ReplicatedLogServiceMethodDescriptorSupplier("GetLeaderInfo"))
              .build();
        }
      }
    }
    return getGetLeaderInfoMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ReplicatedLogServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ReplicatedLogServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ReplicatedLogServiceStub>() {
        @java.lang.Override
        public ReplicatedLogServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ReplicatedLogServiceStub(channel, callOptions);
        }
      };
    return ReplicatedLogServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ReplicatedLogServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ReplicatedLogServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ReplicatedLogServiceBlockingStub>() {
        @java.lang.Override
        public ReplicatedLogServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ReplicatedLogServiceBlockingStub(channel, callOptions);
        }
      };
    return ReplicatedLogServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ReplicatedLogServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ReplicatedLogServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ReplicatedLogServiceFutureStub>() {
        @java.lang.Override
        public ReplicatedLogServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ReplicatedLogServiceFutureStub(channel, callOptions);
        }
      };
    return ReplicatedLogServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void appendLog(rs.ac.fink.izbori.grpc.LogEntry request,
        io.grpc.stub.StreamObserver<rs.ac.fink.izbori.grpc.LogResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getAppendLogMethod(), responseObserver);
    }

    /**
     */
    default void getLeaderInfo(rs.ac.fink.izbori.grpc.LeaderRequest request,
        io.grpc.stub.StreamObserver<rs.ac.fink.izbori.grpc.LeaderInfo> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetLeaderInfoMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ReplicatedLogService.
   */
  public static abstract class ReplicatedLogServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ReplicatedLogServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ReplicatedLogService.
   */
  public static final class ReplicatedLogServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ReplicatedLogServiceStub> {
    private ReplicatedLogServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReplicatedLogServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ReplicatedLogServiceStub(channel, callOptions);
    }

    /**
     */
    public void appendLog(rs.ac.fink.izbori.grpc.LogEntry request,
        io.grpc.stub.StreamObserver<rs.ac.fink.izbori.grpc.LogResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getAppendLogMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getLeaderInfo(rs.ac.fink.izbori.grpc.LeaderRequest request,
        io.grpc.stub.StreamObserver<rs.ac.fink.izbori.grpc.LeaderInfo> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetLeaderInfoMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ReplicatedLogService.
   */
  public static final class ReplicatedLogServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ReplicatedLogServiceBlockingStub> {
    private ReplicatedLogServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReplicatedLogServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ReplicatedLogServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public rs.ac.fink.izbori.grpc.LogResponse appendLog(rs.ac.fink.izbori.grpc.LogEntry request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getAppendLogMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.ac.fink.izbori.grpc.LeaderInfo getLeaderInfo(rs.ac.fink.izbori.grpc.LeaderRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetLeaderInfoMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ReplicatedLogService.
   */
  public static final class ReplicatedLogServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ReplicatedLogServiceFutureStub> {
    private ReplicatedLogServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReplicatedLogServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ReplicatedLogServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.ac.fink.izbori.grpc.LogResponse> appendLog(
        rs.ac.fink.izbori.grpc.LogEntry request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getAppendLogMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.ac.fink.izbori.grpc.LeaderInfo> getLeaderInfo(
        rs.ac.fink.izbori.grpc.LeaderRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetLeaderInfoMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_APPEND_LOG = 0;
  private static final int METHODID_GET_LEADER_INFO = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_APPEND_LOG:
          serviceImpl.appendLog((rs.ac.fink.izbori.grpc.LogEntry) request,
              (io.grpc.stub.StreamObserver<rs.ac.fink.izbori.grpc.LogResponse>) responseObserver);
          break;
        case METHODID_GET_LEADER_INFO:
          serviceImpl.getLeaderInfo((rs.ac.fink.izbori.grpc.LeaderRequest) request,
              (io.grpc.stub.StreamObserver<rs.ac.fink.izbori.grpc.LeaderInfo>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getAppendLogMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              rs.ac.fink.izbori.grpc.LogEntry,
              rs.ac.fink.izbori.grpc.LogResponse>(
                service, METHODID_APPEND_LOG)))
        .addMethod(
          getGetLeaderInfoMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              rs.ac.fink.izbori.grpc.LeaderRequest,
              rs.ac.fink.izbori.grpc.LeaderInfo>(
                service, METHODID_GET_LEADER_INFO)))
        .build();
  }

  private static abstract class ReplicatedLogServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ReplicatedLogServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return rs.ac.fink.izbori.grpc.Izbori.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ReplicatedLogService");
    }
  }

  private static final class ReplicatedLogServiceFileDescriptorSupplier
      extends ReplicatedLogServiceBaseDescriptorSupplier {
    ReplicatedLogServiceFileDescriptorSupplier() {}
  }

  private static final class ReplicatedLogServiceMethodDescriptorSupplier
      extends ReplicatedLogServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ReplicatedLogServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ReplicatedLogServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ReplicatedLogServiceFileDescriptorSupplier())
              .addMethod(getAppendLogMethod())
              .addMethod(getGetLeaderInfoMethod())
              .build();
        }
      }
    }
    return result;
  }
}
