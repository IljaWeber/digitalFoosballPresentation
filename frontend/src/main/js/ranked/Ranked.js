import React from 'react'
import SignInForm from "../initialization/SignInForm";
import ClassicGameMatchInfo from "../matchInfo/ClassicGameMatchInfo";

export class Ranked extends React.Component {
    state = {
        showSignIn: true,
        showMatchInfo: false,
        teams: {}
    }

    submitHandler = (teams) => {
        this.setState({showSignIn: false})
        this.setState({showMatchInfo: true})
        this.setState({teams: teams})
    }

    render() {
        return (
            <div>
                <div>
                    {this.state.showSignIn && <SignInForm submitHandler={this.submitHandler}/>}
                </div>

                {this.state.showMatchInfo && <ClassicGameMatchInfo teams={this.state.teams} gameMode={"ranked/"}
                                                                   resetHandler={this.props.resetHandler}/>}
            </div>
        )
    }
}
