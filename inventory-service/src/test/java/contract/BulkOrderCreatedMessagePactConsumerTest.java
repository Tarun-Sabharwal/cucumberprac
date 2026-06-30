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

// Second consumer-side Pact message test. Same pattern as OrderCreatedMessagePactConsumerTest
// but for a bulk order scenario with a large quantity.
// inventory-service is still the Pact CONSUMER — it receives and depends on the message shape.
@ExtendWith(PactConsumerTestExt.class)
public class BulkOrderCreatedMessagePactConsumerTest {

    // Defines the expected message shape for a bulk order event.
    // Generates a separate contract .json file under target/pacts/.
    @Pact(consumer = "inventory-service", provider = "order-service")
    public V4Pact createBulkPact(PactBuilder builder) {
        PactDslJsonBody expectedBody = new PactDslJsonBody()
                .numberType("orderId", 999)
                .stringType("product", "Laptop")
                .numberType("quantity", 50);

        return builder
                .usingLegacyMessageDsl()
                .given("a bulk order was created")
                .expectsToReceive("a bulk order created event")
                .withContent(expectedBody)
                .toPact();
    }

    // Feeds the bulk order message into inventory-service's own handler code,
    // proving it can correctly read a bulk order message too.
    // No real RabbitMQ or running service needed.
    @Test
    @PactTestFor(pactMethod = "createBulkPact", providerType = ProviderType.ASYNCH)
    void inventoryCanReadBulkOrderCreatedMessage(List<V4Interaction.AsynchronousMessage> messages) throws Exception {
        V4Interaction.AsynchronousMessage message = messages.get(0);
        byte[] content = message.contentsAsBytes();

        OrderCreatedMessage received = new ObjectMapper().readValue(content, OrderCreatedMessage.class);

        assertEquals("Laptop", received.getProduct());
        assertEquals(50, received.getQuantity());
    }
}
