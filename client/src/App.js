import React from 'react';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";
import Dashboard from "./components/dashboard/Dashboard";
import SignIn from "./components/login/SignIn";
import OAuthCallbackWindow from "./components/login/OAuthCallbackWindow";
import TokenExchanger from "./components/login/TokenExchanger";
function App() {
  return (
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

  );
}

export default App;
