import React from 'react';
import "../../css/styles.scss"
import '../../resources/properties'
import UndoButton from "../buttons/UndoButton";
import ResetButton from "../buttons/ResetButton";
import RedoButton from "../buttons/RedoButton";
import TeamComponent from "./TeamComponent";

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
                    <ul className="teams">
                        <TeamComponent number="1" team={this.props.teams[0]}/>
                        <TeamComponent number="2" team={this.props.teams[1]}/>
                    </ul>
                </div>
                <ul className="buttonListScore">
                    <li><UndoButton gameMode={this.props.gameMode} className="fastDropIn" undoHandler={this.undo}/></li>
                    <li><ResetButton gameMode={this.props.gameMode} className="middleDropIn" resetHandler={this.reset}/>
                    </li>
                    <li><RedoButton gameMode={this.props.gameMode} className="slowDropInWithOutDelay"
                                    redoHandler={this.redo}/></li>
                </ul>
            </div>
        )
    }
}