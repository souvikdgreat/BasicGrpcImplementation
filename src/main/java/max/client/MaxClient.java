package max.client;

import com.proto.max.GetMaxServiceGrpc;
import com.proto.max.MaxRequest;
import com.proto.max.MaxResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MaxClient {
    public static void main(String[] args) throws InterruptedException {
        int port = 8080;
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", port)
                .usePlaintext()
                .build();

        printMax(channel);

        channel.shutdown();
    }

    public static void printMax(ManagedChannel channel) throws InterruptedException {
        GetMaxServiceGrpc.GetMaxServiceStub stub = GetMaxServiceGrpc.newStub(channel);
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<MaxRequest> maxRequestStream = stub.getMax(new StreamObserver<>() {
            @Override
            public void onNext(MaxResponse maxResponse) {
                System.out.printf("Maximum till now is %d%n", maxResponse.getMaximum());
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        });

        List.of(1, 5, 3, 6, 2, 20)
                .forEach(num -> {
                    MaxRequest request = MaxRequest.newBuilder()
                            .setNum(num)
                            .build();
                    maxRequestStream.onNext(request);
                });
        maxRequestStream.onCompleted();
        latch.await();
    }
}
