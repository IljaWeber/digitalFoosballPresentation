package com.valtech.digitalFoosball.domain.common.identifier;

import com.valtech.digitalFoosball.domain.common.session.SessionIdentifier;

public class RaiseScoreIdentifier {
    private int teamNo;
    private SessionIdentifier identifier;

    public void setIdentifier(SessionIdentifier identifier) {
        this.identifier = identifier;
    }

    public SessionIdentifier getIdentifier() {
        return identifier;
    }

    public int getTeamNo() {
        return teamNo;
    }

    public void setTeamNo(int teamNo) {
        this.teamNo = teamNo;
    }
}
