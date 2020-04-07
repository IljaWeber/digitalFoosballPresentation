import React from 'react';
import NextRoundButton from "../buttons/NextRoundButton";
import UndoButton from "../buttons/UndoButton";
import "../../css/styles.scss"

export default class RoundEndScreen extends React.Component {

    handleUndo = (response) => {
        this.props.undoHandler(response);
    };

    nextRound = (response) => {
        this.props.nextRoundHandler(response);
    };

    render() {
        return (
            <div>
                <div className="roundEndContainer fastDropIn">
                    <h2 className="congratulations">Congratulations {this.props.team.name}</h2>
                    <h4 className="congratulationsInfo">You won this round - to win the match you have to win two
                                                        rounds!</h4>
                    <h4 className="congratulationsInfo">Please press 'Changeover' to switch sides</h4>
                </div>
                <div className="buttonListRoundEnd">
                    <NextRoundButton className="fastDropIn" nextRoundHandler={this.nextRound}/>
                    <UndoButton className="middleDropIn" undoHandler={this.handleUndo}/>
                </div>
            </div>
        )
    }
}