import React from "react";
import { Route, Redirect } from 'react-router-dom';
import AuthService from "../service/AuthService";



const PrivateRoute = ({component: Component, ...rest}) => {
    return (
        <Route {...rest} render={props => (
            AuthService.getCurrentUser() ?
                <Component {...props} />
                : <Redirect to={{
                    pathname: `/login`,
                    state: { from: props.location }
                }} />
        )} />
    );
};

export default PrivateRoute;