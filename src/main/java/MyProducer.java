import com.rabbitmq.client.AMQP.Queue.DeclareOk;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class MyProducer {

  public static void main(String[] args) {
    publish();
  }

  private static void publish() {
    try {

      ConnectionFactory factory = new ConnectionFactory();
      factory.setHost("localhost");
      factory.setPort(5672);
      Connection connection = factory.newConnection();
      Channel channel = connection.createChannel();

      DeclareOk jobs = channel.queueDeclare("jobs", false, false, false, null);

      String message = "10";
      channel.basicPublish("", "jobs", null, message.getBytes());

      channel.close();
      connection.close();

      System.out.println("Job sent successfully: " + message);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
