package rs.ac.fink.izbori.core;

import rs.ac.fink.izbori.grpc.ReplicatedLogServiceGrpc;

public class FollowerGRPCChannel {
	final String zkNode;
	final String connectionString;
	final ReplicatedLogServiceGrpc.ReplicatedLogServiceBlockingStub blockingStub;
	
	public FollowerGRPCChannel(String zkNode, String connectionString, ReplicatedLogServiceGrpc.ReplicatedLogServiceBlockingStub blockingStub){
		this.zkNode = zkNode;
		this.connectionString = connectionString;
		this.blockingStub = blockingStub;
	}

	public String getZkNode() {
		return zkNode;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public ReplicatedLogServiceGrpc.ReplicatedLogServiceBlockingStub getBlockingStub() {
		return blockingStub;
	}
	
	
	
}