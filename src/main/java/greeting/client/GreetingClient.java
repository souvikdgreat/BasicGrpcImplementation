package greeting.client;


import com.proto.greeting.GreetingRequest;
import com.proto.greeting.GreetingResponse;
import com.proto.greeting.GreetingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingClient {
    public static void main(String[] args) {
        System.out.println("Starting the client");
        int port = 50051;
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", port)
                .usePlaintext()
                .build();

        // greet(channel);
        greetManyTimes(channel);

        System.out.println("Shutting down the client");
        channel.shutdown();
    }

    public static void greet(ManagedChannel channel) {
        GreetingRequest request = GreetingRequest.newBuilder()
                .setFirstName("Souvik")
                .build();

        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);
        GreetingResponse greet = stub.greet(request);
        System.out.println(greet.getResult());
    }

    public static void greetManyTimes(ManagedChannel channel) {
        GreetingRequest request = GreetingRequest.newBuilder()
                .setFirstName("Souvik")
                .build();

        GreetingServiceGrpc.GreetingServiceBlockingStub stub = GreetingServiceGrpc.newBlockingStub(channel);
        stub.greetManyTimes(request)
                .forEachRemaining(response -> System.out.println(response.getResult()));


    }
}
