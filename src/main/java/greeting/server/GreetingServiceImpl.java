package greeting.server;

import com.proto.greeting.GreetingRequest;
import com.proto.greeting.GreetingResponse;
import com.proto.greeting.GreetingServiceGrpc;
import io.grpc.stub.StreamObserver;

public class GreetingServiceImpl extends GreetingServiceGrpc.GreetingServiceImplBase {
    @Override
    public void greet(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        System.out.println("Inside greet Server method");
        GreetingResponse greetingResponse = GreetingResponse.newBuilder()
                .setResult(String.format("hello %s", request.getFirstName()))
                .build();

        responseObserver.onNext(greetingResponse);
        responseObserver.onCompleted();
        System.out.println("Complete greet Server method");

    }
}
