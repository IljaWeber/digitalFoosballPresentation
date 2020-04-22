import React from 'react';
import "../../css/styles.scss"
import '../../resources/properties'
import UndoButton from "../buttons/UndoButton";
import ResetButton from "../buttons/ResetButton";
import RedoButton from "../buttons/RedoButton";
import RaiseButton from "../buttons/RaiseButton";

export default class ScoreScreen extends React.Component {
    reset = (response) => {
        this.props.resetHandler(response);
    };

    undo = (response) => {
        this.props.undoHandler(response);
    };

    redo = (response) => {
        this.props.redoHandler(response)
    };

    render() {
        return (
            <div className="scoreScreenContainer">
                <div className="teamContainer">
                    {this.getTeams()}
                </div>
                <ul className="buttonListScore">
                    <li><UndoButton className="fastDropIn" undoHandler={this.undo}/></li>
                    <li><ResetButton className="middleDropIn" resetHandler={this.reset}/></li>
                    <li><RedoButton className="slowDropInWithOutDelay" redoHandler={this.redo}/></li>
                </ul>
            </div>
        )
    }

    getTeams() {
        return (
            <ul className="teams">
                {this.props.teams.map((team, index) => {
                    return (
                        <li className={`team team${index + 1}`} key={index}>
                            <ul className={`score score${index + 1}`}>{team.score}
                                <li className="roundWins">Won rounds: {team.setWins}</li>
                                <RaiseButton teamNo={index + 1}/>
                            </ul>
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
