package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.model.output.GameOutputModel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller()
public class Publisher implements IUpdateClient {
    private final SimpMessagingTemplate template;

    public Publisher(SimpMessagingTemplate template) {
        this.template = template;
    }

    public void updateClientWith(GameOutputModel gameData) {
        template.convertAndSend("/update/score", gameData);
    }
}
