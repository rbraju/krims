import React, { Component } from "react";
import { render } from 'react-dom';

import {AgGridColumn, AgGridReact} from 'ag-grid-react';
// import 'ag-grid-community/dist/styles/ag-grid.css';
// import 'ag-grid-community/dist/styles/ag-theme-alpine.css';

// const App = () => {
//     const rowData = [
//         { make: 'Honda', model: 'Odyssey', year: 2016, price: 23000 },
//         { make: 'Honda', model: 'Odyssey', year: 2011, price: 16500 },
//         { make: 'Honda', model: 'Odyssey', year: 2014, price: 18999 },
//         { make: 'Honda', model: 'Odyssey', year: 2020, price: 37000 }
//     ];
//
//     return (
//         <div className="ag-theme-alpine" style={{height:400, width: 600}}>
//             <AgGridReact rowData = {rowData}>
//                 <AgGridColumn field="make"></AgGridColumn>
//                 <AgGridColumn field="model"></AgGridColumn>
//                 <AgGridColumn field="year"></AgGridColumn>
//                 <AgGridColumn field="price"></AgGridColumn>
//             </AgGridReact>
//         </div>
//     );
// };

class Events extends Component {
    render() {
        if (!this.props.events) {
            return <div>No events yet..</div>
        }
        return (
            <ul id="events-list">
                {
                    this.props.events.map(event => (
                            <div class="row-header">
                                <div id="row">
                                    <div id="col">{event.event_id}</div>
                                    <div id="col">{event.source}</div>
                                    <div id="col">{event.severity}</div>
                                    <div id="col">{event.description}</div>
                                    <div id="col">{event.type}</div>
                                </div>
                            </div>
                        )
                    )
                }
            </ul>
        );
    }
}

export default Events;

// ReactDOM.render(
//     <Events />,
//     document.getElementById('event-grid')
// );