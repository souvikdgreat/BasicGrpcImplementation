package calculator.client;

import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.SumRequest;
import com.proto.calculator.SumResponse;
import io.grpc.*;
import lombok.Getter;


public class CalculatorClient {
    public static void main(String[] args) {
        System.out.println("starting Calculator client");
        try (CustomChannel customChannel = new CustomChannel()) {
            sum(customChannel);
        }
    }

    public static void sum(CustomChannel channel) {
        SumRequest request = SumRequest.newBuilder()
                .setNum1(1)
                .setNum2(2)
                .build();

        CalculatorServiceGrpc.CalculatorServiceBlockingStub stub = CalculatorServiceGrpc.newBlockingStub(channel.getChannel());
        SumResponse response = stub.sum(request);
        System.out.println(String.format("Sum is %d", response.getSum()));

    }

    @Getter
    static class CustomChannel implements AutoCloseable {
        static final int PORT = 50051;
        private final ManagedChannel channel;

        public CustomChannel() {
            this.channel = ManagedChannelBuilder
                    .forAddress("localhost", PORT)
                    .usePlaintext()
                    .build();
        }

        @Override
        public void close() {
            System.out.println("Channel Shutdown Initiated");
            channel.shutdown();
            System.out.println("Channel Shutdown Completed");
        }
    }
}