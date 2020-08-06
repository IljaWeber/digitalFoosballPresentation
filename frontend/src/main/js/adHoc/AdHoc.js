import React from 'react'
import ClassicGameMatchInfo from "../matchInfo/ClassicGameMatchInfo";
import {properties} from "../../resources/properties";

export class AdHoc extends React.Component {


    componentDidMount() {
        const url = properties.userCommandsUrl + 'adhoc/init';
        const requestOptions = {
            method: 'POST',
            Authorization: properties.auth,
            credentials: 'include'
        };
        fetch(url, requestOptions);
    }


    render() {
        return (
            <div>
                <ClassicGameMatchInfo gameMode={"adhoc"}/>
            </div>
        )
    }

}
