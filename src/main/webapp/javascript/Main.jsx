import React, { Component } from "react";
import ReactDOM from "react-dom"
import Events from './Events'
import '../css/Main.css'

class Main extends Component {
    constructor(props) {
        super(props)
        this.state = {
            events: []
        }
        this.disp = this.disp.bind(this);
    }

    disp() {
        alert("test");
    }

    componentDidMount() {
        fetch("/krims/events")
            .then(res => res.json())
            .then(
                (response) => {
                    this.setState({
                        events: response
                    });
                },
                (error) => {
                    alert(error);
                }
            )
    }

    render() {
        return(
            <div id="main">
                <Events events = { this.state.events } />
                <button onClick={this.disp}>onClick disp()</button>
            </div>
        );
    }
}

ReactDOM.render(
    <Main />,
    document.getElementById('react-mountpoint')
);
