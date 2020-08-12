import React from 'react'
import ResetButton from "../buttons/ResetButton";
import NextRoundButton from "../buttons/NextRoundButton";

export class HalftimeScreen extends React.Component {
    state = {
        showHalfTimeScreen: true
    }

    componentDidMount() {
        setTimeout(() => this.setState({showHalfTimeScreen: false}), 3000)
    }

    reset = (response) => {
        this.props.resetHandler(response);
    };

    nextRoundHandler = () => {
        this.props.changeToSecondHalf();
    }

    render() {
        return (
            <div>
                {this.state.showHalfTimeScreen &&
                <div className="init__container">
                    <h1 className="init__container__headline">Half Time</h1>
                </div>
                }
                <div className="scoreScreenContainer">
                    <div className="teamContainer">
                        {this.getTeams()}
                    </div>
                    <ul className="buttonListScore">
                        <li><ResetButton className="middleDropIn" resetHandler={this.reset} gameMode="time/"/></li>
                        <li><NextRoundButton className="slowDropInWithOutDelay"
                                             nextRoundHandler={this.nextRoundHandler} gameMode="time/"/></li>
                    </ul>
                </div>
            </div>
        )
    }

    getTeams() {
        return (
            <ul className="teams">
                {this.props.teams.map((team, index) => {
                    return (
                        <li className={`team team${index + 1}`} key={index}>
                            <ul className={`score score${index + 1}`}>{team.score}</ul>
                            <ul className="name">{team.name}</ul>
                            <ul className="player">{team.playerOne + ", "}</ul>
                            <ul className="player">{team.playerTwo}</ul>
                        </li>
                    )
                })}
            </ul>
        );
    };


}
