package contract;

import au.com.dius.pact.consumer.dsl.PactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.V4Interaction;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.inventory.in.messaging.OrderCreatedMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Consumer-side Pact test. inventory-service depends on the shape of the
// "order created" message, so it is the Pact CONSUMER here even though
// order-service sends the message first.
@ExtendWith(PactConsumerTestExt.class)
public class OrderCreatedMessagePactConsumerTest {

    // Defines the message shape inventory-service expects to receive.
    // Running this test method is what generates the contract .json file
    // under target/pacts/ - we never hand-write that file.
    // NOTE: method signature must be exactly "V4Pact methodName(PactBuilder)" -
    // Pact 4.6.x enforces this exact shape for @Pact methods.
    @Pact(consumer = "inventory-service", provider = "order-service")
    public V4Pact createPact(PactBuilder builder) {
        PactDslJsonBody expectedBody = new PactDslJsonBody()
                .numberType("orderId", 1)
                .stringType("product", "Camera")
                .numberType("quantity", 5);

        return builder
                .usingLegacyMessageDsl()
                .given("an order was created")
                .expectsToReceive("an order created event")
                .withContent(expectedBody)
                .toPact();
    }

    // Feeds a message built from the contract above into inventory-service's
    // own OrderCreatedMessage class, proving inventory-service can correctly
    // read a message of this shape. No real RabbitMQ involved.
    @Test
    @PactTestFor(pactMethod = "createPact", providerType = ProviderType.ASYNCH)
    void inventoryCanReadOrderCreatedMessage(List<V4Interaction.AsynchronousMessage> messages) throws Exception {
        V4Interaction.AsynchronousMessage message = messages.get(0);
        byte[] content = message.contentsAsBytes();

        OrderCreatedMessage received = new ObjectMapper().readValue(content, OrderCreatedMessage.class);

        assertEquals("Camera", received.getProduct());
        assertEquals(5, received.getQuantity());
    }
}
