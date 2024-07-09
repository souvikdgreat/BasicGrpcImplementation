package average.server;

import com.proto.average.AverageCalculatorGrpc;
import com.proto.average.AverageRequest;
import com.proto.average.AverageResponse;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public class AverageCalculatorServiceImpl extends AverageCalculatorGrpc.AverageCalculatorImplBase {
    @Override
    public StreamObserver<AverageRequest> getAverage(StreamObserver<AverageResponse> responseObserver) {
        System.out.println("Inside Average Calculate method");

        List<Integer> numbers = new ArrayList<>();

        return new StreamObserver<>() {
            @Override
            public void onNext(AverageRequest averageRequest) {
                System.out.println("inside on Next");
                numbers.add(averageRequest.getNum());
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("inside on Error");
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                System.out.println("Inside on Completed");
                double average = numbers
                        .stream()
                        .mapToInt(i -> i)
                        .average()
                        .orElse(0.0);

                AverageResponse averageResponse = AverageResponse.newBuilder()
                        .setAvg(average)
                        .build();
                System.out.printf("Average Calculated %f", average);
                responseObserver.onNext(averageResponse);
                responseObserver.onCompleted();
            }
        };
    }
}
