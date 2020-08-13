import React from 'react'


export class TimeIsOverScreen extends React.Component {

    getCorrectPresentation() {
        if (this.props.winner === null) {
            return (
                <h3 className="congratulationsInfo">Congratulations, nobody lost!</h3>
            )
        } else {
            return (
                <div>
                    <h2 className="congratulations">Congratulations Team: {this.props.winner.name}!</h2>
                    <h3 className="congratulationsInfo">You won this match!</h3>
                </div>
            )
        }
    }

    render() {
        return (

            <div>
                <div className="victoryContainer fastDropIn">
                    <h1>The Match Time is Over!</h1>
                    {this.getCorrectPresentation()}
                </div>
            </div>
        );
    }
}
