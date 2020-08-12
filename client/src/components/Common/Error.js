import React from 'react';

const Error = ({message}) => {

    return (
        <p>{typeof message !== 'object' ? message : "Something went wrong, please try again"}</p>
    );
};

export default Error;
