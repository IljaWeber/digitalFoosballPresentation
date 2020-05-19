package com.valtech.digitalFoosball.api.driven.notification;

import com.valtech.digitalFoosball.domain.gameModes.models.BaseOutputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller()
public class Publisher implements INotifyAboutStateChanges {
    private final SimpMessagingTemplate template;

    @Autowired
    public Publisher(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void notifyAboutStateChange(BaseOutputModel gameData) {
        template.convertAndSend("/update/score", gameData);
    }

    @Override
    public void update(BaseOutputModel baseOutputModel) {
        notifyAboutStateChange(baseOutputModel);
    }
}
