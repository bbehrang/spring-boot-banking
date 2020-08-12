import React, {useEffect, useState} from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';

const AddDialog = ({open, setOpen, submit, customer}) => {

    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [age, setAge] = useState(0);

    useEffect(() => {
        if(customer){
            setName(customer.name);
            setEmail(customer.email);
            setAge(customer.age);
        } else {
            setName('');
            setEmail('');
            setAge(0);
        }
    }, [customer]);

    const handleClose = () => {
        setOpen(false);
    };
    const handleSubmit = () => submit({name, email, age});
    return (
        <Dialog open={open} onClose={handleClose} aria-labelledby="form-dialog-title">
            <DialogTitle id="form-dialog-title">Add customer</DialogTitle>
            <DialogContent>
                <TextField
                    autoFocus
                    margin="dense"
                    id="name"
                    label="name"
                    type="text"
                    value={ name }
                    onChange={ (e) => setName(e.target.value) }
                    fullWidth
                />
                <TextField
                    margin="dense"
                    id="email"
                    label="email"
                    type="email"
                    value={ email }
                    onChange={ (e) => setEmail(e.target.value) }
                    fullWidth
                />
                <TextField
                    margin="dense"
                    id="age"
                    label="age"
                    type="number"
                    value={ age }
                    onChange={ (e) => setAge(e.target.value) }
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
