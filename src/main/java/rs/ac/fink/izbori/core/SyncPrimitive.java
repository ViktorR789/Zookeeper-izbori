package rs.ac.fink.izbori.core;

import org.apache.zookeeper.*;
import java.io.IOException;

public class SyncPrimitive implements Watcher{

	protected ZooKeeper zk = null;
    protected Integer mutex;

    protected String root;

    public SyncPrimitive(String address, Integer zkNotifier) {
    	this(address);
    	this.mutex = zkNotifier;
    	
    }
    protected SyncPrimitive(String address) {
        if(zk == null){
            try {
                System.out.println("Starting ZK:");
                zk = new ZooKeeper(address, 3000, this);
                mutex = Integer.valueOf(-1);
                System.out.println("Finished starting ZK: " + zk);
            } catch (IOException e) {
                System.out.println(e.toString());
                zk = null;
            }
        }
        //else mutex = Integer.valueOf(-1);
    }
    
    synchronized public void process(WatchedEvent event) {
        synchronized (mutex) {
            //System.out.println("Process: " + event.getType());
        	mutex.notify();
        }
    }
    public ZooKeeper getZk() {
    	return zk;
    }
}