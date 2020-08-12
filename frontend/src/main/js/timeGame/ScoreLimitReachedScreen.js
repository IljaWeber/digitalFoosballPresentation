import React from 'react'
import UndoButton from "../buttons/UndoButton";
import ResetButton from "../buttons/ResetButton";

export class ScoreLimitReachedScreen extends React.Component {
    reset = (response) => {
        this.props.resetHandler(response);
    };

    handleUndo = (response) => {
        this.props.undo(response);
    };

    render() {
        return (
            <div>
                <div className="victoryContainer fastDropIn">
                    <h2 className="congratulations">Congratulations Team: {this.props.winner.name}!</h2>
                    <h3 className="congratulationsInfo">You won this match!</h3>
                </div>
                <div className="buttonListVictory">
                    <UndoButton gameMode={this.props.gameMode} className="fastDropIn" undoHandler={this.handleUndo}/>
                    <ResetButton gameMode={this.props.gameMode} className="middleDropIn" resetHandler={this.reset}/>
                </div>
            </div>
        )
    }
}
