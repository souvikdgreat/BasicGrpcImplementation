package primes.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;


public class GetPrimeServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8080;
        Server server = ServerBuilder
                .forPort(port)
                .addService(new GetPrimesServiceImpl())
                .build();

        System.out.println("Server started");

        server.start();

        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> {
                    System.out.println("Shutdown initiated");
                    server.shutdown();
                }));

        server.awaitTermination();
    }
}
