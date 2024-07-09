package max.server;

import com.proto.max.GetMaxServiceGrpc;
import com.proto.max.MaxRequest;
import com.proto.max.MaxResponse;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.atomic.AtomicInteger;

public class GetMaxServiceImpl extends GetMaxServiceGrpc.GetMaxServiceImplBase {
    @Override
    public StreamObserver<MaxRequest> getMax(StreamObserver<MaxResponse> responseObserver) {
        AtomicInteger max = new AtomicInteger(-999);
        return new StreamObserver<>() {
            @Override
            public void onNext(MaxRequest maxRequest) {
                max.set(
                        Math.max(max.get(), maxRequest.getNum())
                );
                responseObserver.onNext(MaxResponse.newBuilder().setMaximum(max.get()).build());
            }

            @Override
            public void onError(Throwable throwable) {
                responseObserver.onError(throwable);
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
