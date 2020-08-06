import React from 'react';
import "../css/styles.scss"
import InitScreen from "./initialization/InitScreen";
import {GameModePickScreen} from "./initialization/GameModePickScreen";

export default class App extends React.Component {

    state = {
        showWelcomeScreen: true,
    };

    componentDidMount() {
        setTimeout(() => this.setState({showWelcomeScreen: false}), 3000);
    }

    render() {
        return (
            <div className="app">
                {this.state.showWelcomeScreen && <InitScreen/>}
                {<GameModePickScreen/>}
            </div>
        );
    }
}
