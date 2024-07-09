package calculator.client;

import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.SumRequest;
import com.proto.calculator.SumResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {
    public static void main(String[] args) {
        System.out.println("starting Calculator client");
        int port = 50051;
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", port)
                .usePlaintext()
                .build();

        sum(channel);

    }

    public static void sum(ManagedChannel channel) {
        SumRequest request = SumRequest.newBuilder()
                .setNum1(1)
                .setNum2(2)
                .build();

        CalculatorServiceGrpc.CalculatorServiceBlockingStub stub = CalculatorServiceGrpc.newBlockingStub(channel);
        SumResponse response = stub.sum(request);
        System.out.println(String.format("Sum is %d", response.getSum()));

    }
}
