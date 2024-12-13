import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public class ThreadManagerConcurrent {
    private BlockingQueue<Request> requests;;
    private final ItemThreadManager itemThreadManager;
    private final UserThread userThread;


    public ThreadManagerConcurrent (Library library) {
        requests = new LinkedBlockingDeque<Request>();
        itemThreadManager = new ItemThreadManager(library);
        itemThreadManager.setRequests(requests);
        userThread = new UserThread(requests);
    }


    public void writeToMemory(LinkedList<Request> requests) {
        userThread.setUserRequests(requests);
        try {
            userThread.run();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void readFromMemory() {
        try {
            itemThreadManager.run();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}


