package average.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GetAverageServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8080;

        Server server = ServerBuilder
                .forPort(port)
                .addService(new AverageCalculatorServiceImpl())
                .build();

        System.out.println("Starting server");
        server.start();

        Runtime.getRuntime()
                .addShutdownHook(new Thread(() -> {
                    System.out.println("shutdown initiated");
                    server.shutdown();
                    System.out.println("shutdown done");
                }));

        server.awaitTermination();
    }
}
