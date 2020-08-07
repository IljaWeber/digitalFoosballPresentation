import React from 'react';
import "../../css/styles.scss"
import {properties} from "../../resources/properties";

export default class RaiseButton extends React.Component {

    handleSubmit = (event) => {
        event.preventDefault();

        const url = properties.userCommandsUrl + 'raspi/raise';

        let myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Authorization", properties.auth);
        myHeaders.append("credentials", "include");

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: JSON.stringify(this.props.teamNo),
            redirect: 'follow'
        };

        fetch(url, requestOptions);
    };

    render() {
        return (
            <form onSubmit={this.handleSubmit} className="raiseContainer">
                <input type="submit" value="Raise Score" className="raiseButton button"/>
            </form>
        );
    }
}
