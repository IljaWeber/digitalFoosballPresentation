import React from 'react'

export class ScoreLimitReachedScreen extends React.Component {
    reset = (response) => {
        this.props.resetHandler(response);
    };

    render() {
        return (
            <div>
                <div className="victoryContainer fastDropIn">
                    <h2 className="congratulations">Congratulations Team: {this.props.winner.name}!</h2>
                    <h3 className="congratulationsInfo">You won this match!</h3>
                </div>
            </div>
        )
    }
}
