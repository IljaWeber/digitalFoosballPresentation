import React from 'react'
import Stomp from "stomp-websocket";
import SockJS from "sockjs-client";
import {properties} from "../../resources/properties";
import {TimeGameScoreScreen} from "./TimeGameScoreScreen";


export class TimeGameMatchInfo extends React.Component {
    state = {
        teams: [],
        matchWinner: "NO_TEAM",
        actualGameSequence: "First Half"
    }

    async componentDidMount() {
        await this.updateState();

        this.stompClient = this.connect();
    }

    connect() {
        const stompClient = Stomp.over(new SockJS(properties.hostAndPort + 'socket-registry'));

        stompClient.connect({}, () => {
            stompClient.subscribe('/update/score', (gameDataModel) => {
                this.updateGameStatus(gameDataModel);
            });
        });

        return stompClient;
    }

    async updateState() {
        const url = properties.userCommandsUrl + this.props.gameMode + "/game";

        const requestOptions = {
            method: 'GET',
            Authorization: properties.auth,
            credentials: 'include'
        };

        const response = await fetch(url, requestOptions);
        const json = await response.json();
        this.setState({teams: [...json.teams]});

        const matchWinner = json.matchWinner.toString();
        const actualGameSequence = json.actualGameSequence.toString();
        this.setState({actualGameSequence: actualGameSequence})
        this.setState({matchWinner: [matchWinner]});
    }

    updateGameStatus(message) {
        const json = JSON.parse(message.body);
        this.setState({teams: json.teams});
        this.setState({actualGameSequence: json.actualGameSequence})
        this.setState({matchWinner: json.matchWinner});
    }

    reset = (response) => {
        this.props.resetHandler(response);
    };

    undo = (response) => {
        this.setState({teams: [...response.teams], matchWinner: "NO_TEAM"})
    };

    redo = (response) => {
        this.setState({
            teams: [...response.teams],
            matchWinner: response.matchWinner
        })
    };

    render() {
        return (
            <TimeGameScoreScreen resetHandler={this.reset} undoHandler={this.undo} redoHandler={this.redo}
                                 teams={this.state.teams}/>
        )
    }
}
