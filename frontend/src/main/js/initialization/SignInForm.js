import React from 'react';
import TeamForm from "./TeamForm";
import "../../css/styles.scss"
import {properties} from "../../resources/properties";

export default class SignInForm extends React.Component {
    state = {
        teamOne: {},
        teamTwo: {}
    };

    teams;

    componentDidMount = async () => {
        const url = properties.url + "allTeams";

        const requestOptions = {
            method: 'GET',
            credentials: 'include',
            Authorization: properties.auth,
        };

        const response = await fetch(url, requestOptions);
        this.teams = await response.json();
    };

    setUpTeamOne = (team) => {
        this.setState({teamOne: team})
    };
    setUpTeamTwo = (team) => {
        this.setState({teamTwo: team})
    };

    submitAdHoc = async (event) => {
        event.preventDefault();
        const url = properties.url + "init/adhoc";

        const requestOptions = {
            method: 'POST',
            credentials: 'include',
            Authorization: properties.auth,
            headers: {
                'Content-Type': 'application/json'
            }
        };

        const response = await fetch(url, requestOptions);
        const json = await response.json();

        if (!response.ok) {
            alert(json.errorMessage)
        } else {
            this.props.submitHandler(json)
        }
    };

    sendForm = async (event) => {
        event.preventDefault();
        const url = properties.url + "init/ranked";

        const requestOptions = {
            method: 'POST',
            credentials: 'include',
            Authorization: properties.auth,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.state)
        };

        const response = await fetch(url, requestOptions);
        const json = await response.json();

        if (!response.ok) {
            alert(json.errorMessage)
        } else {
            this.props.submitHandler(json)
        }
    };

    render() {
        return (
            <div>
                <form onSubmit={this.sendForm} className="teamSignIn">
                    <div className="teamSignIn_Forms">
                        <div className="teamSignIn_Forms_TeamOne">
                            <h2>Green</h2>
                            <TeamForm number="1" onChange={this.setUpTeamOne} teams={this.teams}/>
                        </div>
                        <div className="teamSignIn_Forms_TeamTwo">
                            <h2>Orange</h2>
                            <TeamForm number="2" onChange={this.setUpTeamTwo} teams={this.teams}/>
                        </div>
                    </div>

                    <div className="teamSignIn_Buttons">
                        <div className="teamSignIn_Submit">
                            <input type="submit" value="Submit" className="button slowDropIn"/>
                        </div>
                        <h2>
                            OR
                        </h2>
                    </div>
                </form>

                <form onSubmit={this.submitAdHoc} className="teamSignInAdHoc">
                    <input type="submit" value="Ad-Hoc" className="button slowDropIn"/>
                </form>

            </div>
        )
    }
}
