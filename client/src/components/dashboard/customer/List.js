import React, {useState} from 'react';
import Box from "@material-ui/core/Box";
import {Divider} from '@material-ui/core';
import IconButton from "@material-ui/core/IconButton";
import AddCircleIcon from '@material-ui/icons/AddCircle';
import {useDispatch} from "react-redux";
import {addCustomer, updateCustomer} from "../../../store/customer/slice";
import AddDialog from "./AddDialog";
import Title from '../Title';
import CustomerListItem from "./Item";

function List({customers}) {
    const dispatch = useDispatch();
    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const [customerToEdit, setCustomerToEdit] = useState(null);

    const openDialogForEdit = (customer) => {
        setCustomerToEdit(customer);
        setIsDialogOpen(true);
    };
    const openDialogForAdd = () => {
      setCustomerToEdit(null);
      setIsDialogOpen(true);
    };
    const submitCustomer = (customer) => {

        return customerToEdit ? dispatch(updateCustomer({id:customerToEdit.id, ...customer}))
            : dispatch(addCustomer(customer));
    };
    return (
        <React.Fragment>
            <Box display='flex' alignItems='center'>
                <Title>Customers</Title>
                <IconButton aria-label="add customer" onClick={() => openDialogForAdd()}>
                    <AddCircleIcon/>
                </IconButton>
            </Box>

            {customers && customers.length > 0 ? customers.map((customer) => (
                <Box my={2} key={customer.id}>
                    <CustomerListItem customer={customer} openDialogForEdit={openDialogForEdit}/>
                    <Divider/>
                </Box>
            )) : <p>No customers to show</p>}
            <AddDialog open={isDialogOpen} setOpen={setIsDialogOpen} customer={customerToEdit}
                       submit={submitCustomer}/>
        </React.Fragment>
    );
}
export default React.memo(List);
