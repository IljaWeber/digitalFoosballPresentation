import React from 'react';
import "../../css/styles.scss"
import {properties} from "../../resources/properties";


export default class GameModeScreen extends React.Component {

    handleAdHocInit = async (event) => {
        event.preventDefault();
        const url = properties.url + "/initAdHocMatch";

        const requestOptions = {
            method: 'GET',
            credentials: 'include',
            Authorization: properties.auth,
            headers: {
                'Content-Type': 'application/json'
            },
        };

        const response = await fetch(url, requestOptions);
    };

    handleRegularGameInit = async (event) => {

    }

    render() {
        return (
            <div className="gameModeContainer">
                <form onSubmit={this.handleAdHocInit} className="init_adHocGame">
                    <input type="submit" value="AdHoc Game" className="button slowDropIn"/>
                </form>

                <form onSubmit={this.handleRegularGameInit} className="init_regularGame">
                    <input type="submit" value="Regular Game" className="button slowDropIn"/>
                </form>
            </div>
        );
    }
}
