import React from 'react'
import {properties} from "../../resources/properties";
import {TimeGameMatchInfo} from "./TimeGameMatchInfo";

export class TimeGame extends React.Component {
    state = {
        teams: {}
    }

    async componentDidMount() {
        const url = properties.userCommandsUrl + 'timegame/init';
        const requestOptions = {
            method: 'POST',
            Authorization: properties.auth,
            credentials: 'include'
        };
        const response = await fetch(url, requestOptions);
        const json = await response.json();
        this.setState({teams: json})
    }

    render() {
        return (
            <div>
                <TimeGameMatchInfo teams={this.state.teams} gameMode={"timegame"}/>
            </div>
        )
    }

}
