package com.valtech.digitalFoosball.api.notification;

import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.ports.INotifyAboutStateChanges;
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

    public void notifyAboutStateChange(GameOutputModel gameData) {
        template.convertAndSend("/update/score", gameData);
    }
}
