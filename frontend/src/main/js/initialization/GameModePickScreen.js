import React from 'react'
import foosballPic from "../../resources/foosball.jpeg";
import {AdHoc} from "../adHoc/AdHoc";
import {Ranked} from "../ranked/Ranked";
import {TimeGame} from "../TimeGame/TimeGame";

export class GameModePickScreen extends React.Component {
    state = {
        showMenu: true,
        showAdHoc: false,
        showRanked: false,
        showTimeGame: false
    }

    resetHandler = () => {
        this.setState({showAdHoc: false});
        this.setState({showRanked: false});
        this.setState({showTimeGame: false});
        this.setState({showMenu: true});
    }

    submitAdHocGame = (event) => {
        event.preventDefault();
        this.setState({showAdHoc: true, showMenu: false})
    }

    submitRanked = (event) => {
        event.preventDefault()
        this.setState({showRanked: true, showMenu: false})
    }

    submitTimeGame = (event) => {
        event.preventDefault()
        this.setState({showTimeGame: true, showMenu: false})
    }


    render() {
        return (
            <div className="app_foosballImage" style={{backgroundImage: `url(${foosballPic})`}}>
                {this.state.showMenu &&
                <form onSubmit={this.submitAdHocGame}>
                    <input type="submit" value="AdHoc Game" className={this.props.className + " button"}/>
                </form>}
                {this.state.showAdHoc && <AdHoc resetHandler={this.resetHandler}/>}

                {this.state.showMenu &&
                <form onSubmit={this.submitRanked}>
                    <input type="submit" value="Ranked Game" className={this.props.className + " button"}/>
                </form>}
                {this.state.showRanked && <Ranked resetHandler={this.resetHandler}/>}

                {this.state.showMenu &&
                <form onSubmit={this.submitTimeGame}>
                    <input type="submit" value="Time Game" className={this.props.classname + " button"}/>
                </form>
                }
                {this.state.showTimeGame && <TimeGame resetHandler={this.resetHandler}/>}
            </div>
        )
    }
}
