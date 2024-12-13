import java.util.LinkedList;
import java.util.Queue;


class UserThread implements Runnable {
    private final Queue<Request> requests; // Shared memory
    private LinkedList<Request> userRequests; // User-provided requests


    public UserThread(Queue<Request> requests) {
        this.requests = requests;
        this.userRequests = new LinkedList<>();
    }


    public void setUserRequests(LinkedList<Request> userRequests) {
        if (userRequests != null) {
            this.userRequests = userRequests;
        } else {
            this.userRequests = new LinkedList<>(); // Default to an empty list if null
        }
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


    @Override
    public void run() {
        int result = writeToRequests();
        OutputHandel handler = new OutputHandel();
        handler.setResponse(result);
        handler.printMessage();

    }
}
