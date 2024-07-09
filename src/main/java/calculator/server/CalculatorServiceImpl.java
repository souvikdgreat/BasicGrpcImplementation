package calculator.server;

import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.SumRequest;
import com.proto.calculator.SumResponse;
import io.grpc.stub.StreamObserver;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {
    @Override
    public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver) {
        System.out.println("inside server method");


        SumResponse response = SumResponse.newBuilder()
                .setSum(request.getNum1() + request.getNum2())
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        System.out.println("task completed");
    }
}
