package calculator.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class CalculatorServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 50051;

        Server server = ServerBuilder
                .forPort(port)
                .addService(new CalculatorServiceImpl())
                .build();

        System.out.println("Starting Server");
        server.start();
        System.out.println("Starting started");


        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> {
                    System.out.println("received shutdown request");
                    server.shutdown();
                    System.out.println("shutdown complete");
                }));
        server.awaitTermination();

    }
}
