import React from 'react';
import VictoryScreen from "./VictoryScreen";
import ScoreScreen from "./ScoreScreen";
import {properties} from "../../resources/properties";
import SockJS from 'sockjs-client';
import Stomp from 'stomp-websocket';
import RoundEndScreen from "./RoundEndScreen";

export default class MatchInfo extends React.Component {
    state = {
        teams: [],
        roundWinner: 0,
        matchWinner: 0
    };

    stompClient;

    connect() {
        const stompClient = Stomp.over(new SockJS(properties.ipAndPort + 'socket-registry'));

        stompClient.connect({}, () => {
            stompClient.subscribe('/update/score', (message) => {
                const json = JSON.parse(message.body);
                this.setState({teams: json.teams});
                this.setState({roundWinner: json.roundWinner});
                this.setState({matchWinner: json.matchWinner});
            });
        });

        return stompClient;
    }

    undo = (response) => {
        this.setState({teams: [...response.teams], roundWinner: 0, matchWinner: 0})
    };

    reset = (response) => {
        this.setState({teams: [], roundWinner: 0, matchWinner: 0});
        this.props.resetHandler(response);
    };

    redo = (response) =>{
        this.setState({teams: [...response.teams], roundWinner: response.roundWinner, matchWinner: response.matchWinner})
    };

    nextRound = (response) => {
        this.setState({teams: [...response.teams], roundWinner: 0})
    };

    getWinningTeam = () => {
        let teamNumber = this.state.matchWinner - 1;

        return this.state.teams[teamNumber];
    };

    getRoundWinningTeam = () => {
        let teamNumber = this.state.roundWinner - 1;

        return this.state.teams[teamNumber];
    };

    async componentDidMount() {
        await this.updateState();

        this.stompClient = this.connect();
    }

    async updateState() {
        const url = properties.url + 'game';

        const requestOptions = {
            method: 'GET',
            Authorization: properties.auth,
            credentials: 'include'
        };

        const response = await fetch(url, requestOptions);
        const json = await response.json();
        this.setState({teams: [...json.teams]});
    }

    componentWillUnmount() {
        this.stompClient.disconnect();
    }

    render() {
        if (this.state.matchWinner !== 0) {
            return (
                <VictoryScreen winner={this.getWinningTeam()} resetHandler={this.reset} undoHandler={this.undo}/>
            )
        }

        if (this.state.roundWinner !== 0) {
            return (
                <RoundEndScreen team={this.getRoundWinningTeam()} undoHandler={this.undo} nextRoundHandler={this.nextRound} />
            )
        } else {
            return (
                <ScoreScreen resetHandler={this.reset} undoHandler={this.undo} redoHandler={this.redo} teams={this.state.teams}/>
            )
        }
    }
}
