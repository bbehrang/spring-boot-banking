import React from 'react';
import {BrowserRouter as Router, Route, Switch} from "react-router-dom";
import Dashboard from "./components/dashboard/Dashboard";
import SignIn from "./components/login/SignIn";
import OAuthCallbackWindow from "./components/login/OAuthCallbackWindow";
import TokenExchanger from "./components/login/TokenExchanger";
import {CookiesProvider} from 'react-cookie';

function App() {
    return (
        <CookiesProvider>
            <Router>
                <Switch>
                    <Route exact path="/auth">
                        <SignIn/>
                    </Route>
                    <Route path="/auth/github/login">
                        <OAuthCallbackWindow/>
                    </Route>
                    <Route path="/auth/github/exchange">
                        <TokenExchanger/>
                    </Route>
                    <Route path="/dashboard">
                        <Dashboard/>
                    </Route>
                    <Route path="/">
                        <Dashboard/>
                    </Route>
                </Switch>
            </Router>
        </CookiesProvider>

    );
}

export default App;
