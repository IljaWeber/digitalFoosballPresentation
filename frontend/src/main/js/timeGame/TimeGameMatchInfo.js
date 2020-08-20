import React from 'react'
import Stomp from "stomp-websocket";
import SockJS from "sockjs-client";
import {properties} from "../../resources/properties";
import {TimeGameScoreScreen} from "./TimeGameScoreScreen";
import {HalftimeScreen} from "./HalftimeScreen";
import {ScoreLimitReachedScreen} from "./ScoreLimitReachedScreen";
import {TimeIsOverScreen} from "./TimeIsOverScreen";
import UndoButton from "../buttons/UndoButton";
import ResetButton from "../buttons/ResetButton";
import RedoButton from "../buttons/RedoButton";


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

    componentWillUnmount() {
        this.stompClient.disconnect();
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

        this.setState({
            teams: [...json.teams],
            actualGameSequence: json.actualGameSequence,
            winner: json.matchWinner
        })
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
            winner: response.matchWinner
        })
    };

    getWinningTeam = () => {
        if (this.state.winner === "NO_TEAM") {
            return null
        }

        if (this.state.winner === "ONE") {
            return this.state.teams[0];
        }

        return this.state.teams[1];
    };

    changeToSecondHalf = () => {
        this.setState({actualGameSequence: 'Second Half'})
    }

    render() {
        return (
            <div>
                {this.getScreen()}
                <ul className="buttonListScore">
                    <li><UndoButton className="fastDropIn" gameMode={this.props.gameMode} undoHandler={this.undo}/></li>
                    <li><ResetButton className="middleDropIn" resetHandler={this.reset} gameMode={this.props.gameMode}/>
                    </li>
                    <li><RedoButton className="slowDropInWithOutDelay" gameMode={this.props.gameMode}
                                    redoHandler={this.redo}/></li>
                </ul>
            </div>

        )
    }

    getScreen() {
        if (this.state.actualGameSequence === 'End By Score') {
            return (
                <div>
                    <ScoreLimitReachedScreen winner={this.getWinningTeam()} resetHandler={this.reset} undo={this.undo}
                                             gameMode={this.props.gameMode}>
                    </ScoreLimitReachedScreen>
                </div>
            )
        }

        if (this.state.actualGameSequence === 'End By Time') {
            return (
                <div>
                    <TimeIsOverScreen winner={this.getWinningTeam()} resetHandler={this.reset} undo={this.undo}
                                      gameMode={this.props.gameMode}>
                    </TimeIsOverScreen>
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
                </div>
            )
        }
    }
}
