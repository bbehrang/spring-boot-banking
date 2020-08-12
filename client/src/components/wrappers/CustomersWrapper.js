import React, {useEffect} from 'react';
import {useDispatch, useSelector} from "react-redux";
import {fetchCustomers, getAll} from "../../store/customer/slice";
import {selectAllCustomers} from "../../store/customer/slice";
import {status as storeStatus} from "../../store/status";
import CircularProgress from '@material-ui/core/CircularProgress';
import Customers from "../dashboard/customer/List";
import Error from "../Common/Error";


const CustomersWrapper = props => {
    const dispatch = useDispatch();
    const status = useSelector(state => state.customers.status);
    const customers = useSelector(selectAllCustomers);
    const error = useSelector(state => state.customers.error);

    useEffect(() => {
        if(status === storeStatus.IDLE){
            dispatch(fetchCustomers());
        }
    }, [status, dispatch]);
    if(error && status === storeStatus.FAILED) return <Error message={error}/>;
    if(status === storeStatus.LOADING || status === storeStatus.IDLE) return <CircularProgress/>;
    return <Customers customers={customers}/>;
};

export default CustomersWrapper;
