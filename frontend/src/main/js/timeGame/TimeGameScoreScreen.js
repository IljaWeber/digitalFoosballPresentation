import React from 'react'
import RaiseButton from "../buttons/RaiseButton";

export class TimeGameScoreScreen extends React.Component {

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
