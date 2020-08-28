import React from 'react';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Dashboard from "./components/dashboard/Dashboard";
import SignIn from "./components/login/SignIn";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import {useSelector} from "react-redux";

function App() {
    const token = useSelector(state => state.user.token);
    const sock = new SockJS('http://localhost:9000/websocket',{
        transports: ['xhr-streaming'],
        headers: {'Authorization': token}
    });
    let stompClient = Stomp.over(sock);
    sock.onopen = function() {
        console.log('open');
    };
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/notifications/data', function (greeting) {
            console.log(greeting);
            //you can execute any function here
        });
    });
    return (
        <>
            <Router>
                <Switch>
                    <Route exact path="/auth">
                        <SignIn/>
                    </Route>
                    <Route path="/dashboard">
                        <Dashboard/>
                    </Route>
                    <Route path="/">
                        <Dashboard/>
                    </Route>
                </Switch>
            </Router>
            <button onClick={() =>  stompClient.send("/app/top-up2", {'Authorization': token}, 'hello')}>hello</button>

        </>
    );
}

export default App;
