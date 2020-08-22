import React, {useEffect} from 'react';
import {useLocation} from "react-router-dom";
import {loginGithub, setError} from "../../store/userSlice";
import CircularProgress from "@material-ui/core/CircularProgress/CircularProgress";
import {useDispatch} from "react-redux";
const queryString = require('query-string');

const TokenExchanger = props => {
    const location = useLocation();
    const dispatch = useDispatch();
    const submitError = (error) => dispatch(setError(error));
    const submitSuccessfulLogin = (code) => dispatch(loginGithub(code));
    useEffect(() => {
        console.log("exchanging");
        const result = queryString.parse(location);
        console.log(result);
        result.error ? submitError({result}) : submitSuccessfulLogin(result);
    }, [location]);

    return <CircularProgress/>;
};

export default TokenExchanger;
