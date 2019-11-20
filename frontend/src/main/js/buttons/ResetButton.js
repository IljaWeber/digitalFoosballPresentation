import React from 'react';
import "../../css/styles.scss"
import {properties} from "../../resources/properties";

export default class ResetButton extends React.Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        event.preventDefault();
        const url = properties.url + 'reset';
        fetch(url, {
            method: 'DELETE',
        }).then(response => {
                this.props.resetHandler(response)
            })
    };

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
            <input type="submit" value="Start New Match" className={this.props.className + " button"} onSubmit={this.handleSubmit}/>
            </form>
        );
    }
}