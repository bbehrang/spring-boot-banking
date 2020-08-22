import React, {useEffect, useState} from 'react';
import {useLocation} from "react-router-dom";
import Button from '@material-ui/core/Button';
import Link from '@material-ui/core/Link';
import Typography from '@material-ui/core/Typography';
import {makeStyles} from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import LoginPopUp from "./PopupWindow";
import {OAUTH_WINDOW_NAME} from "../../constants";
import {useDispatch} from "react-redux";
import {loginGithub, setError} from "../../store/userSlice";

function Copyright() {
    return (
        <Typography variant="body2" color="textSecondary" align="center">
            {'Copyright © '}
            <Link color="inherit" href="https://material-ui.com/">
                Your Website
            </Link>{' '}
            {new Date().getFullYear()}
            {'.'}
        </Typography>
    );
}

const useStyles = makeStyles((theme) => ({
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
}));

export default function SignIn() {
    const classes = useStyles();
    const [result, setResult] = useState({});

    useEffect(() => {
        if(result.error) submitError(result.error);
        if(result.code) submitSuccessfulLogin(result);
    }, [result]);

    const dispatch = useDispatch();

    const submitError = (error) => dispatch(setError(error));

    const submitSuccessfulLogin = (code) => dispatch(loginGithub(code));

    const handleSubmit = async (e) => {
        e.preventDefault();
        await handleGithubLogin();
    };
    const handleGithubLogin = async () => {
        const qParams = [
            `redirect_uri=http://localhost:3000/auth/github/login`,
            `scope=user:read`,
            `state=github`
        ].join("&");
        try {
            const response = await fetch(`http://localhost:9000/api/v0/auth/github/url?${qParams}`);
            const url = await response.text();
            LoginPopUp(url, OAUTH_WINDOW_NAME, setResult);
        } catch (e) {
            console.error(e);
        }
    };
    return (
        <Container component="main" maxWidth="xs">
            <Button
                type="submit"
                fullWidth
                variant="contained"
                color="primary"
                className={classes.submit}
                onClick={handleSubmit}
            >
                Sign In
            </Button>
        </Container>
    );
}
