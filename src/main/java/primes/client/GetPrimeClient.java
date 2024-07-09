package primes.client;

import com.proto.primes.PrimesGetterServiceGrpc;
import com.proto.primes.PrimesRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GetPrimeClient {
    public static void main(String[] args) {
        int port = 8080;
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", port)
                .usePlaintext()
                .build();
        getPrimes(channel);
        channel.shutdown();
    }


    public static void getPrimes(ManagedChannel channel) {
        PrimesGetterServiceGrpc.PrimesGetterServiceBlockingStub stub = PrimesGetterServiceGrpc.newBlockingStub(channel);
        PrimesRequest request = PrimesRequest.newBuilder()
                .setNum(1)
                .build();
        System.out.println("factors :");
        stub.getPrimeNumbers(request)
                .forEachRemaining(response -> System.out.print(response.getFactor() + " "));
    }
}
