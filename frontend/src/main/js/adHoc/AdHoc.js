import React from 'react'
import ClassicGameMatchInfo from "../matchInfo/ClassicGameMatchInfo";
import {properties} from "../../resources/properties";

export class AdHoc extends React.Component {
    state = {};

    async componentDidMount() {
        const url = properties.userCommandsUrl + 'adhoc/init';
        const requestOptions = {
            method: 'POST',
            Authorization: properties.auth,
            credentials: 'include'
        };
        const response = await fetch(url, requestOptions);
        let json = await response.json();
        this.setState(json)
    }

    render() {
        return (
            <div>
                <ClassicGameMatchInfo teams={this.state.teams} resetHandler={this.props.resetHandler}
                                      gameMode={"adhoc/"}/>
            </div>
        )
    }

}
