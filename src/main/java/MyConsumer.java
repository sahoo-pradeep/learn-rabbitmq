import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.nio.charset.StandardCharsets;

public class MyConsumer {

  public static void main(String[] args) {
    consume();
  }

  private static void consume() {
    try {

      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("localhost");
      factory.setPort(5672);
      Connection connection = factory.newConnection();
      Channel channel = connection.createChannel();

      DeclareOk jobs = channel.queueDeclare("jobs", false, false, false, null);

      Consumer consumer = new DefaultConsumer(channel) {
        @Override
        public void handleDelivery(String consumerTag, Envelope envelope,
            BasicProperties properties, byte[] body) {
          String message = new String(body, StandardCharsets.UTF_8);
          System.out.println("Job received: " + message);
        }
      };

      channel.basicConsume("jobs", true, consumer);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
