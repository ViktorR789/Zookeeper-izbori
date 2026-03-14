package rs.ac.fink.izbori.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.56.0)",
    comments = "Source: izbori.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ElectionServiceGrpc {

  private ElectionServiceGrpc() {}

  public static final String SERVICE_NAME = "izbori.ElectionService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<rs.ac.fink.izbori.grpc.VoteRequest,
      rs.ac.fink.izbori.grpc.VoteResponse> getSubmitVotesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SubmitVotes",
      requestType = rs.ac.fink.izbori.grpc.VoteRequest.class,
      responseType = rs.ac.fink.izbori.grpc.VoteResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.ac.fink.izbori.grpc.VoteRequest,
      rs.ac.fink.izbori.grpc.VoteResponse> getSubmitVotesMethod() {
    io.grpc.MethodDescriptor<rs.ac.fink.izbori.grpc.VoteRequest, rs.ac.fink.izbori.grpc.VoteResponse> getSubmitVotesMethod;
    if ((getSubmitVotesMethod = ElectionServiceGrpc.getSubmitVotesMethod) == null) {
      synchronized (ElectionServiceGrpc.class) {
        if ((getSubmitVotesMethod = ElectionServiceGrpc.getSubmitVotesMethod) == null) {
          ElectionServiceGrpc.getSubmitVotesMethod = getSubmitVotesMethod =
              io.grpc.MethodDescriptor.<rs.ac.fink.izbori.grpc.VoteRequest, rs.ac.fink.izbori.grpc.VoteResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SubmitVotes"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.ac.fink.izbori.grpc.VoteRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.ac.fink.izbori.grpc.VoteResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ElectionServiceMethodDescriptorSupplier("SubmitVotes"))
              .build();
        }
      }
    }
    return getSubmitVotesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<rs.ac.fink.izbori.grpc.StatisticsRequest,
      rs.ac.fink.izbori.grpc.StatisticsResponse> getGetStatisticsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetStatistics",
      requestType = rs.ac.fink.izbori.grpc.StatisticsRequest.class,
      responseType = rs.ac.fink.izbori.grpc.StatisticsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<rs.ac.fink.izbori.grpc.StatisticsRequest,
      rs.ac.fink.izbori.grpc.StatisticsResponse> getGetStatisticsMethod() {
    io.grpc.MethodDescriptor<rs.ac.fink.izbori.grpc.StatisticsRequest, rs.ac.fink.izbori.grpc.StatisticsResponse> getGetStatisticsMethod;
    if ((getGetStatisticsMethod = ElectionServiceGrpc.getGetStatisticsMethod) == null) {
      synchronized (ElectionServiceGrpc.class) {
        if ((getGetStatisticsMethod = ElectionServiceGrpc.getGetStatisticsMethod) == null) {
          ElectionServiceGrpc.getGetStatisticsMethod = getGetStatisticsMethod =
              io.grpc.MethodDescriptor.<rs.ac.fink.izbori.grpc.StatisticsRequest, rs.ac.fink.izbori.grpc.StatisticsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetStatistics"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.ac.fink.izbori.grpc.StatisticsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  rs.ac.fink.izbori.grpc.StatisticsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ElectionServiceMethodDescriptorSupplier("GetStatistics"))
              .build();
        }
      }
    }
    return getGetStatisticsMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ElectionServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ElectionServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ElectionServiceStub>() {
        @java.lang.Override
        public ElectionServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ElectionServiceStub(channel, callOptions);
        }
      };
    return ElectionServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ElectionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ElectionServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ElectionServiceBlockingStub>() {
        @java.lang.Override
        public ElectionServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ElectionServiceBlockingStub(channel, callOptions);
        }
      };
    return ElectionServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ElectionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ElectionServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ElectionServiceFutureStub>() {
        @java.lang.Override
        public ElectionServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ElectionServiceFutureStub(channel, callOptions);
        }
      };
    return ElectionServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void submitVotes(rs.ac.fink.izbori.grpc.VoteRequest request,
        io.grpc.stub.StreamObserver<rs.ac.fink.izbori.grpc.VoteResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSubmitVotesMethod(), responseObserver);
    }

    /**
     */
    default void getStatistics(rs.ac.fink.izbori.grpc.StatisticsRequest request,
        io.grpc.stub.StreamObserver<rs.ac.fink.izbori.grpc.StatisticsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetStatisticsMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ElectionService.
   */
  public static abstract class ElectionServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ElectionServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ElectionService.
   */
  public static final class ElectionServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ElectionServiceStub> {
    private ElectionServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ElectionServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ElectionServiceStub(channel, callOptions);
    }

    /**
     */
    public void submitVotes(rs.ac.fink.izbori.grpc.VoteRequest request,
        io.grpc.stub.StreamObserver<rs.ac.fink.izbori.grpc.VoteResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSubmitVotesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getStatistics(rs.ac.fink.izbori.grpc.StatisticsRequest request,
        io.grpc.stub.StreamObserver<rs.ac.fink.izbori.grpc.StatisticsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetStatisticsMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ElectionService.
   */
  public static final class ElectionServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ElectionServiceBlockingStub> {
    private ElectionServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ElectionServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ElectionServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public rs.ac.fink.izbori.grpc.VoteResponse submitVotes(rs.ac.fink.izbori.grpc.VoteRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSubmitVotesMethod(), getCallOptions(), request);
    }

    /**
     */
    public rs.ac.fink.izbori.grpc.StatisticsResponse getStatistics(rs.ac.fink.izbori.grpc.StatisticsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetStatisticsMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ElectionService.
   */
  public static final class ElectionServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ElectionServiceFutureStub> {
    private ElectionServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ElectionServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ElectionServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.ac.fink.izbori.grpc.VoteResponse> submitVotes(
        rs.ac.fink.izbori.grpc.VoteRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSubmitVotesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<rs.ac.fink.izbori.grpc.StatisticsResponse> getStatistics(
        rs.ac.fink.izbori.grpc.StatisticsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetStatisticsMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_SUBMIT_VOTES = 0;
  private static final int METHODID_GET_STATISTICS = 1;

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
        case METHODID_SUBMIT_VOTES:
          serviceImpl.submitVotes((rs.ac.fink.izbori.grpc.VoteRequest) request,
              (io.grpc.stub.StreamObserver<rs.ac.fink.izbori.grpc.VoteResponse>) responseObserver);
          break;
        case METHODID_GET_STATISTICS:
          serviceImpl.getStatistics((rs.ac.fink.izbori.grpc.StatisticsRequest) request,
              (io.grpc.stub.StreamObserver<rs.ac.fink.izbori.grpc.StatisticsResponse>) responseObserver);
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
          getSubmitVotesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              rs.ac.fink.izbori.grpc.VoteRequest,
              rs.ac.fink.izbori.grpc.VoteResponse>(
                service, METHODID_SUBMIT_VOTES)))
        .addMethod(
          getGetStatisticsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              rs.ac.fink.izbori.grpc.StatisticsRequest,
              rs.ac.fink.izbori.grpc.StatisticsResponse>(
                service, METHODID_GET_STATISTICS)))
        .build();
  }

  private static abstract class ElectionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ElectionServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return rs.ac.fink.izbori.grpc.Izbori.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ElectionService");
    }
  }

  private static final class ElectionServiceFileDescriptorSupplier
      extends ElectionServiceBaseDescriptorSupplier {
    ElectionServiceFileDescriptorSupplier() {}
  }

  private static final class ElectionServiceMethodDescriptorSupplier
      extends ElectionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ElectionServiceMethodDescriptorSupplier(String methodName) {
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
      synchronized (ElectionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ElectionServiceFileDescriptorSupplier())
              .addMethod(getSubmitVotesMethod())
              .addMethod(getGetStatisticsMethod())
              .build();
        }
      }
    }
    return result;
  }
}
