import RaiseButton from "../buttons/RaiseButton";
import React from 'react';
import "../../css/styles.scss"

export default class TeamComponent extends React.Component {
    render() {
        let number = this.props.number;
        let team = this.props.team;
        return (
            <li className={`team team${number}`}>
                <ul className={`score score${number}`}>{team.score}
                    <li className="roundWins">Won rounds: {team.setWins}</li>
                    <RaiseButton teamNo={number}/>
                </ul>
                <ul className="name">{team.name}</ul>
                <ul className="player">{team.playerOne + ", "}</ul>
                <ul className="player">{team.playerTwo}</ul>
            </li>
        )
    }
}
