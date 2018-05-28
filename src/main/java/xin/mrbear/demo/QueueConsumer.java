package xin.mrbear.demo;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class QueueConsumer {
    public static void main(String[] args) throws JMSException, IOException {
        //1.创建连接工厂
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.135:61616");
        //2.获取连接
        Connection connection = connectionFactory.createConnection();
        //3.启动
        connection.start();
        //4.获得 核心session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.创建队列对象
        Queue queue = session.createQueue("test-queue");
        //6.创建消费对象
        MessageConsumer consumer = session.createConsumer(queue);
        //7.监听消息
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("提取到消息："+ textMessage.getText());
                }catch (JMSException e){
                    e.printStackTrace();
                }
            }
        });

        //8.等待键盘输入
        System.in.read();
        //9.关闭资源
        consumer.close();
        session.close();
        connection.close();


    }
}
