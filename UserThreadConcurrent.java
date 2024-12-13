import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;

public class UserThreadConcurrent implements Runnable {
    private BlockingQueue<Request> requests;
    private LinkedList<Request> userRequests;


    public UserThreadConcurrent(BlockingQueue<Request> requests, LinkedList<Request> userRequests) {
        this.requests = requests;
        this.userRequests = userRequests;
    }


    public int writeToRequests() {
        try {
            Request request = userRequests.poll();
            requests.add(request);
            return 1;
        }
        catch (Exception e) {
            return 0;}
    }


    public void setUserRequests(LinkedList<Request> userRequests) {
        if (userRequests != null) {
            this.userRequests = userRequests;
        } else {
            this.userRequests = new LinkedList<>(); // Default to an empty list if null
        }
    }


    @Override
    public void run() {
        int result = writeToRequests();
        OutputHandel handler = new OutputHandel();
        handler.setResponse(result);
        handler.printMessage();
    }
}
