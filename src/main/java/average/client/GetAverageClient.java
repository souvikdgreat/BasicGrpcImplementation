package average.client;

import com.proto.average.AverageCalculatorGrpc;
import com.proto.average.AverageRequest;
import com.proto.average.AverageResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class GetAverageClient {
    public static void main(String[] args) throws InterruptedException {
        int port = 8080;

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", port)
                .usePlaintext()
                .build();

        getAverage(channel);

        channel.shutdown();

    }

    public static void getAverage(ManagedChannel channel) throws InterruptedException {
        AverageCalculatorGrpc.AverageCalculatorStub stub = AverageCalculatorGrpc.newStub(channel);
        List<Integer> numlist = List.of(1, 2, 3, 4);

        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<AverageRequest> requestStreamObserver = stub.getAverage(new StreamObserver<>() {
            @Override
            public void onNext(AverageResponse averageResponse) {
                System.out.printf("Response from the server %f%n", averageResponse.getAvg());
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        });

        numlist.forEach(num -> {
            requestStreamObserver.onNext(AverageRequest.newBuilder().setNum(num).build());
        });

        requestStreamObserver.onCompleted();


        // latch.await(3, TimeUnit.SECONDS);
        latch.await();
    }
}
