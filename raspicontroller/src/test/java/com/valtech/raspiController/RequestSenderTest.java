package com.valtech.raspiController;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class RequestSenderTest {

    @Test
    void sendRaise_whenSendingHttpRequest_thenResponseHasStatusOk() throws IOException {
        RequestSender requestSender = new RequestSender();

        int actual = requestSender.sendRaise(1);

        assertThat(actual).isEqualTo(200);
    }
}