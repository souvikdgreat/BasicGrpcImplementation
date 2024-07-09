package max.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class MaxServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 8080;
        Server server = ServerBuilder
                .forPort(port)
                .addService(new GetMaxServiceImpl())
                .build();

        System.out.println("Starting Server");
        server.start();
        System.out.println("Server Started");

        Runtime
                .getRuntime()
                .addShutdownHook(new Thread(() -> {
                    System.out.println("server shutdown initiated");
                    server.shutdown();
                }));

        server.awaitTermination();

    }
}
