package jmaster.io.project2.service;

import jmaster.io.project2.dto.MessageDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class KafkaService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    @KafkaListener(id = "notificationGroup", topics = "notification")
    public void listen(MessageDTO messageDTO) {
        log.info("Received: " + messageDTO.getToName());
    }
}
