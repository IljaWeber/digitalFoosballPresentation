import React from 'react';
import "../css/styles.scss"
import SignInForm from './initialization/SignInForm';
import MatchInfo from "./matchInfo/MatchInfo";
import foosballPic from '../resources/foosball.jpeg'
import InitScreen from "./initialization/InitScreen";

export default class App extends React.Component {
    state = {
        showNameForm: true,
        showMatchInfo: false,
        showWelcomeScreen: true,
    };

    submit = () => {
        this.setState({showNameForm: false, showMatchInfo: true});
    };

    reset = () => {
        this.setState({showNameForm: true, showMatchInfo: false});
        this.setState({showWelcomeScreen: true});
        setTimeout(() => this.setState({showWelcomeScreen: false}), 1600);
    };

    componentDidMount() {
        setTimeout(() => this.setState({showWelcomeScreen: false}), 3000);
    }

    render() {
        return (
            <div className="app">
                {this.state.showWelcomeScreen && <InitScreen/>}

                <div className="app_foosballImage" style={{backgroundImage: `url(${foosballPic})`}}>
                    {this.state.showNameForm &&

                    <div className="app_containerSignIn">
                        <div>
                            <h1 className="app_containerSignIn_Headline">Sign In</h1>
                            <SignInForm submitHandler={this.submit}/>
                        </div>
                    </div>
                    }
                </div>

                {this.state.showMatchInfo &&
                <div className="app_matchInfo">
                    <MatchInfo resetHandler={this.reset}/>
                </div>}
            </div>
        );
    }
}
