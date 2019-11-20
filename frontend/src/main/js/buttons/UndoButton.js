import React from 'react';
import "../../css/styles.scss"
import {properties} from "../../resources/properties";

export default class UndoButton extends React.Component {
    handleSubmit = async(event) => {
        event.preventDefault();
        const url = properties.url + 'undo';
        const response = await fetch(url, {
            method: 'PUT',
            headers: {
                Accepts: 'application/json',
            },
        });
        const json = await response.json();
        this.props.undoHandler(json);
    };

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <input type="submit" value="Undo Last Goal" className={this.props.className + " button"} onSubmit={this.handleSubmit}/>
            </form>
        );
    }
}