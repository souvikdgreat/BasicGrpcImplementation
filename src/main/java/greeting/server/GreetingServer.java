package greeting.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GreetingServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051;
        System.out.println("Starting Greeting Server");

        Server server = ServerBuilder.forPort(port)
                .addService(new GreetingServiceImpl())
                .build();

        server.start();
        System.out.println("Greeting Server Started");
        System.out.println("Listening on port " + port);

        Runtime.getRuntime()
                .addShutdownHook(new Thread(
                        () -> {
                            System.out.println("received shutdown request");
                            server.shutdown();
                            System.out.println("Server Stopped");
                        }
                ));

        //server will wait for termination
        server.awaitTermination();
    }
}
