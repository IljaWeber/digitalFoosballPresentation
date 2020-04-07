import React from 'react';
import "../../css/styles.scss"

export default class AutoCompletion extends React.Component {
    getSuggestion() {
        let suggestions = this.props.teams.map((team, index) => {
                let suggestion = null;

                let teamName = String(team.name).toLowerCase();
                let userInput = String(this.props.userInput).toLowerCase();

                if (teamName.startsWith(userInput)) {
                    suggestion = this.addSuggestion(index, team);
                }

                return suggestion;
            }
        );

        return suggestions
    }

    addSuggestion(index, team) {
        return (
            <li className="suggestion" key={index} onClick={() => {
                this.props.selectedTeam(team);
            }}>
                {team.name}
            </li>)
    }

    render() {
        return (
            <nav>
                <ul className="suggestionList">{this.getSuggestion()}</ul>
            </nav>
        )
    }
}
