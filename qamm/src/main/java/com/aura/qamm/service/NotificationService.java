package com.aura.qamm.service;

import com.aura.qamm.dao.NotificationDao;
import com.aura.qamm.dto.Notification;
import com.aura.qamm.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationDao notificationDao;

    public List<Notification> getNotifications(String email) throws BusinessException {
        return notificationDao.getNotifications(email);
    }

    public Notification getNotification(String email,String uuid) throws BusinessException{
        return notificationDao.getNotification(email,uuid);
    }

    public void updateNotification(String email, String uuid) throws BusinessException{
         notificationDao.updateNotification(email,uuid);
    }

    public void deleteNotification(String email, String uuid) throws BusinessException{
        notificationDao.deleteNotification(email,uuid);
    }
}
