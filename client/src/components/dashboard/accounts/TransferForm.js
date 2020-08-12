import React, {useEffect, useState} from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';
import InputLabel from '@material-ui/core/InputLabel';
import MenuItem from '@material-ui/core/MenuItem';
import FormHelperText from '@material-ui/core/FormHelperText';
import FormControl from '@material-ui/core/FormControl';
import Select from '@material-ui/core/Select';
import makeStyles from "@material-ui/core/styles/makeStyles";
const useStyles = makeStyles((theme) => ({
    formControl: {
        minWidth: '100%',
    }
}));
const TransferForm = ({open, setOpen, submit, accounts}) => {
    const classes = useStyles();
    const [sender, setSender] = useState('');
    const [receiver, setReceiver] = useState('');
    const [amount, setAmount] = useState(0);


    const handleClose = () => {
        setOpen(false);
    };
    const handleSubmit = () => submit({sender, receiver, amount});
    return (
        <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">Transfer Money</DialogTitle>
            <DialogContent>
                <FormControl className={classes.formControl}>
                    <InputLabel id="currency">Account</InputLabel>
                    <Select
                        labelId="From account"
                        id="sender"
                        value={sender}
                        onChange={(e) => setSender(e.target.value)}
                        autoWidth
                    >
                        {
                            accounts ? accounts.map(account =>
                                <MenuItem key={account.number} value={account.number}>
                                    <b>*{account.number.slice(-4)}</b> , {account.balance} {account.currency}</MenuItem>)
                                : null
                        }
                    </Select>
                </FormControl>
                <TextField
                    margin="dense"
                    id="receiver"
                    label="Destination account number"
                    type="text"
                    value={ receiver }
                    onChange={ (e) => setReceiver(e.target.value) }
                    fullWidth
                />
                <TextField
                    margin="dense"
                    id="amount"
                    label="amount"
                    type="number"
                    value={ amount }
                    onChange={ (e) => setAmount(e.target.value) }
                    fullWidth
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={handleClose} color="primary">
                    Cancel
                </Button>
                <Button onClick={handleSubmit} color="primary">
                    Submit
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default TransferForm;
