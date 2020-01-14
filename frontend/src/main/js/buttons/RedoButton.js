import React from 'react';
import "../../css/styles.scss"
import {properties} from "../../resources/properties";

export default class RedoButton extends React.Component {

    handleSubmit = async(event) => {
        event.preventDefault();
        const url = properties.url + 'redo';
        const requestOptions = {
            method: 'PUT',
            headers: {'Authorization': properties.auth},
        };
        const response = await fetch(url, requestOptions);
        const json = await response.json();
        this.props.redoHandler(json);
    };

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <input type="submit" value="Redo Goal" className={this.props.className + " button"} onSubmit={this.handleSubmit}/>
            </form>
        );
    }
}
