package com.valtech.raspiController;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class RequestSenderShould {

    @Test
    void notify_status_of_ok_when_score_has_raised() throws IOException {
        RequestSender requestSender = new RequestSender();

        int actual = requestSender.sendRaise(1);

        assertThat(actual).isEqualTo(200);
    }
}
