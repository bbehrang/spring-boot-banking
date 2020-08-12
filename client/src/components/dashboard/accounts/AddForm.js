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
        minWidth: 120,
    }
}));
const AddDialog = ({open, setOpen, submit}) => {
    const classes = useStyles();
    const [currency, setCurrency] = useState('');
    const [balance, setBalance] = useState(0);


    const handleClose = () => {
        setOpen(false);
    };
    const handleSubmit = () => submit({currency, balance});
    return (
        <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">Add an Account</DialogTitle>
            <DialogContent>
                <FormControl className={classes.formControl}>
                    <InputLabel id="currency">Currency</InputLabel>
                    <Select
                        labelId="currency"
                        id="currency"
                        value={currency}
                        onChange={(e) => setCurrency(e.target.value)}
                        autoWidth
                    >
                        <MenuItem value={"USD"}>USD</MenuItem>
                        <MenuItem value={"EUR"}>EUR</MenuItem>
                        <MenuItem value={"UAH"}>UAH</MenuItem>
                        <MenuItem value={"CHF"}>CHF</MenuItem>
                        <MenuItem value={"GBP"}>GBP</MenuItem>

                    </Select>
                </FormControl>
                <TextField
                    margin="dense"
                    id="balance"
                    label="balance"
                    type="text"
                    value={ balance }
                    onChange={ (e) => setBalance(e.target.value) }
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

export default AddDialog;
