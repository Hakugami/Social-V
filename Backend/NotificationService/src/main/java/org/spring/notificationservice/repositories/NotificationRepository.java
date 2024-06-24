package org.spring.notificationservice.repositories;

import org.spring.notificationservice.models.NotificationModel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<NotificationModel, String> {

    List<NotificationModel> findByReceiverUsername(String username);
}
