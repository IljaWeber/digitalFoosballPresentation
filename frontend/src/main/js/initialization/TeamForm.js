import React from 'react';
import AutoCompletion from './AutoCompletion';
import "../../css/styles.scss"

export default class TeamForm extends React.Component {
    constructor(props){
        super(props);
        this.reference = React.createRef()
    }
    state = {
        name: "",
        nameOfPlayerOne: "",
        nameOfPlayerTwo: "",
        showSuggestions: false
    };

    handleChange = () => {
        let json = {
            name: this.state.name,
            nameOfPlayerOne: this.state.nameOfPlayerOne,
            nameOfPlayerTwo: this.state.nameOfPlayerTwo
        };

        this.props.onChange(json);
    };

    componentDidMount() {
        document.addEventListener('click', this.handleClick, false)
    }

    componentWillUnmount() {
        document.removeEventListener('click', this.handleClick, false)
    }

    handleClick = e => {
        const node = this.reference.current;

        if (node && !node.contains(e.target)){
            this.setState({showSuggestions: false})
        }
    };

    render() {
        return (
            <div className="teamContainer" ref={this.reference}>
                <div className="teamContainer_TeamInputContainer">
                    <input className={"teamContainer_TeamInputContainer_TeamInput teamInput" + this.props.number} value={this.state.name}
                           onChange={this.updateName}
                           placeholder="Team name"/>
                    {this.state.showSuggestions && <AutoCompletion userInput={this.state.name} teams={this.props.teams}
                                                             selectedTeam={this.selectTeam}/>}
                </div>
                <input className={"teamContainer_TeamComponents teamInput" + this.props.number} value={this.state.nameOfPlayerOne}
                       onChange={this.updatePlayerOne} placeholder="Player One"/>
                <input className={"teamContainer_TeamComponents teamInput" + this.props.number} value={this.state.nameOfPlayerTwo}
                       onChange={this.updatePlayerTwo} placeholder="Player Two"/>
            </div>
        )
    }

    updateName = (event) => {
        let name = String(event.target.value);
        this.checkForSuggestions(name);

        this.setState({name: name}, () => {
            this.handleChange()
        })
    };

    checkForSuggestions = (name) => {
        if (name.length >= 3) {
            this.setState({showSuggestions: true})
        }
    };

    updatePlayerOne = (event) => {
        this.setState({nameOfPlayerOne: event.target.value}, () => {
            this.handleChange()
        })
    };

    updatePlayerTwo = (event) => {
        this.setState({nameOfPlayerTwo: event.target.value}, () => {
            this.handleChange()
        })
    };

    selectTeam = (event) => {
        this.setState({name: event.name}, () => {
            this.handleChange()
        });

        this.setState({nameOfPlayerOne: event.playerOne}, () => {
            this.handleChange()
        });

        this.setState({nameOfPlayerTwo: event.playerTwo}, () => {
            this.handleChange()
        });

        this.setState({showSuggestions: false})
    }
}
