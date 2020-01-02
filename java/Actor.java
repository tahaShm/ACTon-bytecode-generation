import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


public class Actor extends DefaultActor {
    private ArrayList<Message> queue;
    private ReentrantLock lock;
    int queueSize;

    public Actor(int queueSize){
        this.queue = new ArrayList<>();
        this.lock = new ReentrantLock();
        this.queueSize = queueSize;
    }

    @Override
    public void run() {
        while(true){
            lock.lock();
            if(!queue.isEmpty())
                queue.remove(0).execute();
            lock.unlock();
        }
    }

    public void send(Message m){
        lock.lock();
        if(queue.size() < queueSize)
            queue.add(m);
        lock.unlock();
    }
}
