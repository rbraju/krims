import React, { Component } from "react";
import ReactDOM from "react-dom"
import '../css/Main.css'

class Navigation extends Component {

    constructor(props) {
        super(props)
    }

    disp() {
        alert("test");
    }

    render() {
        return(
            <div id="main">
                <h4>Navigation.jsx</h4>
                <button onClick="disp()">Nav</button>
            </div>
        );
    }
}

ReactDOM.render(
    <Navigation />,
    document.getElementById('navigation')
);
