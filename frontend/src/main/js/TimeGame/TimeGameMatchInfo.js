import React from 'react'
import Stomp from "stomp-websocket";
import SockJS from "sockjs-client";
import {properties} from "../../resources/properties";
import {TimeGameScoreScreen} from "./TimeGameScoreScreen";
import {HalftimeScreen} from "./HalftimeScreen";
import VictoryScreen from "../matchInfo/VictoryScreen";


export class TimeGameMatchInfo extends React.Component {
    state = {
        teams: [],
        winner: "NO_TEAM",
        actualGameSequence: "First Half"
    }


    async componentDidMount() {
        this.stompClient = await this.connect();
        await this.updateState();
        this.setState({teamsLoaded: true})
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
        const url = properties.hostAndPort + this.props.gameMode + "game";

        const requestOptions = {
            method: 'GET',
            Authorization: properties.auth,
            credentials: 'include'
        };

        const response = await fetch(url, requestOptions);
        const json = await response.json();
        this.setState({teams: [...json.teams]});
        this.setState({actualGameSequence: json.actualGameSequence})
        const matchWinner = json.matchWinner.toString();
        this.setState({winner: [matchWinner]});
    }

    updateGameStatus(message) {
        const json = JSON.parse(message.body);
        this.setState({teams: json.teams});
        this.setState({actualGameSequence: json.actualGameSequence})
        this.setState({winner: json.matchWinner});
    }

    reset = (response) => {
        this.props.resetHandler(response);
    };

    undo = (response) => {
        this.setState({
            teams: [...response.teams],
            actualGameSequence: response.actualGameSequence,
            winner: response.matchWinner
        })
    };

    redo = (response) => {
        this.setState({
            teams: [...response.teams],
            actualGameSequence: response.actualGameSequence,
            matchWinner: response.matchWinner
        })
    };

    getWinningTeam = () => {
        if (this.state.matchWinner === "ONE") {
            return this.state.teams[0];
        }

        return this.state.teams[1];
    };

    changeToHalfTime = () => {
        this.setState({actualGameSequence: 'Half Time'});
    }

    changeToSecondHalf = () => {
        this.setState({actualGameSequence: 'Second Half'})
    }

    render() {
        if (this.state.actualGameSequence === 'End by Score Limit') {
            return (
                <div>
                    <VictoryScreen winner={this.getWinningTeam()} resetHandler={this.reset} undo={this.undo}
                                   gameMode={this.props.gameMode}>
                    </VictoryScreen>
                </div>
            )
        }

        if (this.state.actualGameSequence === 'Half Time') {
            return (
                <div>
                    <HalftimeScreen resetHandler={this.reset} changeToSecondHalf={this.changeToSecondHalf}
                                    teams={this.state.teams}/>
                </div>
            )
        } else {
            return (
                <div>
                    <div>
                        <TimeGameScoreScreen gameMode={this.props.gameMode} resetHandler={this.reset}
                                             undoHandler={this.undo} redoHandler={this.redo}
                                             teams={this.state.teams}/>
                    </div>
                    <div>
                        <form onSubmit={this.changeToHalfTime}>
                            <input type="submit" value="HalfTime" className={this.props.className + " button"}/>
                        </form>
                    </div>
                </div>
            )
        }

    }
}
