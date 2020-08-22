import React, {useEffect} from "react";
import CircularProgress from "@material-ui/core/CircularProgress/CircularProgress";
import {loginGithub, setError} from "../../store/userSlice";

export default () => {

    useEffect(() => {
        const params = window.location.search;
        if (window.opener) {
            window.opener.postMessage({payload: params, source: window.name});
            window.close();
        }
    });
    return <CircularProgress/>;
};
