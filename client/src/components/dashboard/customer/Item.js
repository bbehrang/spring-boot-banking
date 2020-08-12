import React, {useState} from 'react';
import Box from "@material-ui/core/Box";
import Typography from "@material-ui/core/Typography";
import {Divider} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import DeleteOutlineIcon from '@material-ui/icons/DeleteOutline';
import CreateOutlinedIcon from '@material-ui/icons/CreateOutlined';
import CreditCardIcon from '@material-ui/icons/CreditCard';
import AddCircleIcon from '@material-ui/icons/AddCircle';
import SendIcon from '@material-ui/icons/Send';
import AccountList from "../accounts/List";
import {useDispatch} from "react-redux";
import {addAccount, addCustomer, deleteCustomer, transfer} from "../../../store/customer/slice";
import AddForm from "../accounts/AddForm";
import BalanceForm from "../accounts/BalanceForm";
import TransferForm from "../accounts/TransferForm";

const Item = ({ customer, openDialogForEdit }) => {
    const [isHidden, setIsHidden] = useState(true);

    const [isAddFormVisible, setAddFormVisibilty] = useState(false);
    const [isBalanceFormVisible, setBalanceFormVisibilty] = useState(false);
    const [isTransferFormVisible, setTransferFormVisibilty] = useState(false);
    const [balanceEditType, setBalanceEditType] = useState(null);

    const dispatch = useDispatch();

    const handleAddAccount = (account) => {
        return dispatch(addAccount({id: customer.id, account}));
    };

    const handleBalance = () => {

    };

    const handleTransfer = (transferRequest) => {
        return dispatch(transfer({customerId: customer.id, transferRequest}));
    };


    return (
        <>
            <Box py={1} display='flex' alignItems='center' justifyContent='space-between' flexWrap='wrap'>
                <Box mx={1} mt={1}>
                    <Typography variant="body1" component="h6" gutterBottom>
                        <b>Id:</b> {customer.id}
                    </Typography>
                </Box>
                <Divider orientation="vertical" flexItem/>
                <Box mx={1} mt={1}>
                    <Typography variant="body1" component="h6" gutterBottom>
                        <b>Name:</b> {customer.name}
                    </Typography>
                </Box>
                <Divider orientation="vertical" flexItem/>
                <Box mx={1} mt={1}>
                    <Typography variant="body1" component="h6" gutterBottom>
                        <b>Email:</b> {customer.email}
                    </Typography>
                </Box>
                <Divider orientation="vertical" flexItem/>
                <Box mx={1} mt={1}>
                    <Typography variant="body1" component="h6" gutterBottom>
                        <b>Age:</b> {customer.age}
                    </Typography>
                </Box>
                <Box display='flex'>
                    <Box mx={1}>
                        <Button
                            variant="contained"
                            startIcon={<CreditCardIcon/>}
                            onClick={() => setIsHidden(!isHidden)}
                        >
                            Browse Accounts
                        </Button>
                    </Box>
                    <Box mx={1}>
                        <Button
                            variant="contained"
                            color="secondary"
                            startIcon={<DeleteOutlineIcon/>}
                            onClick={() => dispatch(deleteCustomer(customer.id))}
                        >
                            Delete
                        </Button>
                    </Box>
                    <Box mx={1}>
                        <Button
                            variant="contained"
                            color="primary"
                            startIcon={<CreateOutlinedIcon/>}
                            onClick={() => openDialogForEdit(customer)}
                        >
                            Edit
                        </Button>
                    </Box>
                </Box>
            </Box>
            <Divider/>
            <Box my={2} display={isHidden ? "none" : "block"}>
                <Typography variant='h6' component='p'>
                    Accounts
                </Typography>
                <AccountList accounts={customer.accounts} customerId={customer.id}/>
                <Box mt={1} display='flex' justifyContent='space-between'>
                    <Button
                        variant="contained"
                        color="primary"
                        startIcon={<AddCircleIcon/>}
                        onClick = {() => setAddFormVisibilty(true)}
                    >
                        Add a new account
                    </Button>
                    <Button
                        variant="contained"
                        color="primary"
                        startIcon={<SendIcon/>}
                        onClick = {() => setTransferFormVisibilty(true)}
                    >
                        Transfer money
                    </Button>
                </Box>
            </Box>
            <AddForm open={isAddFormVisible} setOpen={setAddFormVisibilty} submit={handleAddAccount}/>
            <BalanceForm open={isBalanceFormVisible} setOpen={setBalanceFormVisibilty} submit={handleBalance}/>
            <TransferForm open={isTransferFormVisible}
                          setOpen={setTransferFormVisibilty}
                          submit={handleTransfer}
                          accounts={customer.accounts}
            />
        </>
    );
};

export default React.memo(Item);
