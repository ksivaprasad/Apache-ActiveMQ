package com.activemq;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class MessageSend {
	public static void main(String[] args) {
		Connection connection = null;
		Session session = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue("TestQueue");
			MessageProducer producer = session.createProducer(queue);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			while(true) {
				System.out.print("Enter your message('quit' to exit): ");
				String input = br.readLine();
				if(input.equals("quit")) {
					System.out.println("Process terminated \n ------------------------------------------ ");
					System.exit(0);
				}
				TextMessage message = session.createTextMessage(input);
				producer.send(message);
				System.out.println("Message Sent. \n ------------------------------------------");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		if(connection != null) {
			try {
				connection.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(session != null) {
			try {
				session.close();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
