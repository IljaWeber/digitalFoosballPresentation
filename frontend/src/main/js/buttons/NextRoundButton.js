import React from 'react';
import "../../css/styles.scss"
import {properties} from "../../resources/properties";

export default class NextRoundButton extends React.Component {
    handleSubmit = async (event) => {
        event.preventDefault();
        const url = properties.userCommandsUrl + 'newRound';
        const requestOptions = {
            method: 'POST',
            Authorization: properties.auth,
            credentials: 'include'
        };
        const response = await fetch(url, requestOptions);
        const json = await response.json();
        this.props.nextRoundHandler(json);
    };

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <input type="submit" value="Changeover" className={this.props.className + " button"} onSubmit={this.handleSubmit}/>
            </form>
        );
    }
}
