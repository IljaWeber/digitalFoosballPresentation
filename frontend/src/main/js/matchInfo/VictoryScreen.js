import React from 'react';
import '../../resources/properties'
import "../../css/styles.scss"
import ResetButton from "../buttons/ResetButton";
import UndoButton from "../buttons/UndoButton";

export default class VictoryScreen extends React.Component {
    reset = (response) => {
        this.props.resetHandler(response);
    };

    handleUndo = (response) => {
        this.props.undoHandler(response);
    };

    render() {
        return (
            <div>
                <div className="victoryContainer fastDropIn">
                    <h2 className="congratulations">Congratulations {this.props.winner.name}!</h2>
                    <h3 className="congratulationsInfo">You won this match!</h3>
                    <p className="playersRule">{this.props.winner.playerOne} and {this.props.winner.playerTwo} rule!</p>
                </div>
                <div className="buttonListVictory">
                    <UndoButton gameMode={this.props.gameMode} className="fastDropIn" undoHandler={this.handleUndo}/>
                    <ResetButton gameMode={this.props.gameMode} className="middleDropIn" resetHandler={this.reset}/>
                </div>
            </div>
        )
    }
}
