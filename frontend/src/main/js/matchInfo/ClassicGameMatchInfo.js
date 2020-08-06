import React from 'react';
import VictoryScreen from "./VictoryScreen";
import ScoreScreen from "./ScoreScreen";
import {properties} from "../../resources/properties";
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';
import RoundEndScreen from "./RoundEndScreen";

export default class ClassicGameMatchInfo extends React.Component {
    state = {
        teams: [],
        winnerOfSet: "NO_TEAM",
        matchWinner: "NO_TEAM"
    };

    stompClient;

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

    updateGameStatus(message) {
        const json = JSON.parse(message.body);
        this.setState({teams: json.teams});
        this.setState({winnerOfSet: json.winnerOfSet});
        this.setState({matchWinner: json.matchWinner});
    }

    undo = (response) => {
        this.setState({teams: [...response.teams], winnerOfSet: "NO_TEAM", matchWinner: "NO_TEAM"})
    };

    reset = (response) => {
        this.setState({teams: [], winnerOfSet: "NO_TEAM", matchWinner: "NO_TEAM"});
        this.props.resetHandler(response);
    };

    redo = (response) => {
        this.setState({
            teams: [...response.teams],
            winnerOfSet: response.winnerOfSet,
            matchWinner: response.matchWinner
        })
    };

    nextRound = (response) => {
        this.setState({teams: [...response.teams], winnerOfSet: "NO_TEAM"})
    };

    getWinningTeam = () => {
        if (this.state.matchWinner === "ONE") {
            return this.state.teams[0];
        }

        return this.state.teams[1];
    };

    getRoundWinningTeam = () => {
        if (this.state.winnerOfSet === "ONE") {
            return this.state.teams[0];
        }

        return this.state.teams[1];

    };

    async updateState() {
        const url = properties.hostAndPort + this.props.gameMode + "/game";

        const requestOptions = {
            method: 'GET',
            Authorization: properties.auth,
            credentials: 'include'
        };

        const response = await fetch(url, requestOptions);
        const json = await response.json();
        this.setState({teams: [...json.teams]});

        const winnerOfSet = json.winnerOfSet.toString();
        const matchWinner = json.matchWinner.toString();
        this.setState({winnerOfSet: [winnerOfSet]});
        this.setState({matchWinner: [matchWinner]});
    }

    componentWillUnmount() {
        this.stompClient.disconnect();
    }

    render() {
        const noTeam = "NO_TEAM".toString();

        let matchWinner = this.state.matchWinner.toString();
        if (matchWinner !== noTeam) {
            return (
                <VictoryScreen winner={this.getWinningTeam()} resetHandler={this.reset} undoHandler={this.undo}/>
            )
        }

        let winnerOfSet = this.state.winnerOfSet.toString();
        if (winnerOfSet !== noTeam) {
            return (
                <RoundEndScreen team={this.getRoundWinningTeam()} undoHandler={this.undo}
                                nextRoundHandler={this.nextRound}/>
            )
        } else {
            return (
                <ScoreScreen resetHandler={this.reset} undoHandler={this.undo} redoHandler={this.redo}
                             teams={this.state.teams}/>
            )
        }
    }
}
