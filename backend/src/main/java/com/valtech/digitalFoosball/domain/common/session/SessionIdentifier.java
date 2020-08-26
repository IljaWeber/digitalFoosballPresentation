package com.valtech.digitalFoosball.domain.common.session;

import java.util.UUID;

public class SessionIdentifier {
    private UUID identifier;

    public void setId(UUID registerRaspberryPiWithId) {
        identifier = registerRaspberryPiWithId;
    }

    public UUID getIdentifier() {
        return identifier;
    }
}
