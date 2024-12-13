import java.util.LinkedList;
import java.util.Queue;

class ItemThreadManager implements Runnable {
    private final Library library;
    private Queue<Request> requests;

    public ItemThreadManager(Library library) {
        this.library = library;
        this.requests = new LinkedList<>(); // Initialize the queue to avoid NullPointerException
    }


    public void setRequests(Queue<Request> requests) {
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
