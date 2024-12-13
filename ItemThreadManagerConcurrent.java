import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;


public class ItemThreadManagerConcurrent implements Runnable {
    private final Library library;
    private BlockingQueue<Request> requests;

    public ItemThreadManagerConcurrent(Library library) {
        this.library = library;
        this.requests = new LinkedBlockingDeque<Request>();
    }


    public void setRequests(BlockingQueue<Request> requests) {
        this.requests = requests;
    }


    public int readFromMemory() {
        int response = -1;
        if (requests == null || requests.isEmpty()) {
            return 0;
        } else {
            Request request = requests.poll();
            if (request.getType() == Request.Type.Borrow) {
                response = library.borrowItem(request.getTitle());
            } else if (request.getType() == Request.Type.Return) {
                response = library.returnItem(request.getTitle());
                request.setType(Request.Type.None);
            }
            return response;
        }
    }


    @Override
    public void run() {
        OutputHandel outputHandel = new OutputHandel();
        int response = readFromMemory();
        outputHandel.setResponse(response);
        outputHandel.printMessage();
    }
}
