package com.boilerplate.kafka.service;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import com.boilerplate.kafka.model.Message;

public class MessageListener {

        public CountDownLatch latch = new CountDownLatch(2);

        public CountDownLatch messageLatch = new CountDownLatch(1);

        @KafkaListener(topics = "segundo", groupId = "foo", containerFactory = "fooKafkaListenerContainerFactory")
        public void listenGroupFoo(String message) {
            System.out.println("Received Message in group 'foo': " + message);
            latch.countDown();
        }

        @KafkaListener(topics = "segundo", groupId = "bar", containerFactory = "barKafkaListenerContainerFactory")
        public void listenGroupBar(String message) {
            System.out.println("Received Message in group 'bar': " + message);
            latch.countDown();
        }

        @KafkaListener(topics = "segundo", containerFactory = "headersKafkaListenerContainerFactory")
        public void listenWithHeaders(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
            System.out.println("Received Message: " + message + " from partition: " + partition);
            latch.countDown();
        }

        @KafkaListener(topics = "mensagem", containerFactory = "messageKafkaListenerContainerFactory")
        public void greetingListener(Message greeting) {
            System.out.println("Received greeting message: " + greeting);
            this.messageLatch.countDown();
        }

    }