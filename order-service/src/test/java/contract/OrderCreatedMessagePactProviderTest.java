package contract;

import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.order.out.messaging.OrderCreatedMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;

// Provider-side Pact verification. order-service PRODUCES the "order created"
// message, so it is the Pact PROVIDER here - it must prove its real message
// still matches what inventory-service (the consumer) recorded as a contract.
//
// @PactFolder points at the local folder where inventory-service's consumer
// test generated the contract .json file. In a real company setup this would
// be @PactBroker pointing at a shared server instead of a local folder - kept
// local here for learning, since we don't have a Pact Broker running.
@Provider("order-service")
@PactFolder("../inventory-service/target/pacts")
@ExtendWith(PactVerificationInvocationContextProvider.class)
public class OrderCreatedMessagePactProviderTest {

    @BeforeEach
    void setTarget(PactVerificationContext context) {
        context.setTarget(new MessageTestTarget());
    }

    // Matches the provider state inventory-service recorded via .given(...)
    // in its consumer test. No real setup needed for this simple example -
    // this method just has to exist so Pact's state-change check passes.
    @State("an order was created")
    public void toAnOrderWasCreatedState() {
    }

    // Runs once per interaction recorded in the contract file.
    @TestTemplate
    void verifyContract(PactVerificationContext context) {
        context.verifyInteraction();
    }

    // Builds the same message order-service's real code (OrderUseCase via
    // OrderMessagePublisher) currently produces, so Pact can compare it
    // against the contract recorded by inventory-service. No real RabbitMQ
    // involved here either.
    @PactVerifyProvider("an order created event")
    public String verifyOrderCreatedMessage() throws Exception {
        OrderCreatedMessage message = new OrderCreatedMessage(1L, "Camera", 5);
        return new ObjectMapper().writeValueAsString(message);
    }
}
