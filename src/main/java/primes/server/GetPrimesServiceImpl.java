package primes.server;

import com.proto.primes.PrimesGetterServiceGrpc;
import com.proto.primes.PrimesRequest;
import com.proto.primes.PrimesResponse;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public class GetPrimesServiceImpl extends PrimesGetterServiceGrpc.PrimesGetterServiceImplBase {
    @Override
    public void getPrimeNumbers(PrimesRequest request, StreamObserver<PrimesResponse> responseObserver) {
        System.out.println("request received for getting prime numbers");
        List<Integer> primesForANum = getPrimesForNum(request.getNum());
        for (Integer factor : primesForANum) {
            PrimesResponse response = PrimesResponse.newBuilder()
                    .setFactor(factor)
                    .build();
            responseObserver.onNext(response);
        }
        responseObserver.onCompleted();
        System.out.println("request completed for getting prime numbers");
    }

    public List<Integer> getPrimesForNum(int num) {
        List<Integer> results = new ArrayList<>();
        for (int i = 2; i <= num; i++) {
            while (num % i == 0) {
                results.add(i);
                num = num / i;
            }
            if (num == 1) {
                break;
            }
        }
        return results;
    }
}
