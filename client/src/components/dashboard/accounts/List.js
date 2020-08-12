import React from 'react';
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell/TableCell";
import TableBody from "@material-ui/core/TableBody";
import Table from "@material-ui/core/Table";
import IconButton from "@material-ui/core/IconButton";
import EvStationIcon from '@material-ui/icons/EvStation';
import DeleteOutlineIcon from '@material-ui/icons/DeleteOutline';
import ShoppingCartIcon from '@material-ui/icons/ShoppingCart';
import {useDispatch} from "react-redux";
import {deleteAccount, deleteCustomer} from "../../../store/customer/slice";

const List = ({accounts, customerId}) => {
    const dispatch = useDispatch();
    return (
        <Table size="small">
            <TableHead>
                <TableRow>
                    <TableCell>Number</TableCell>
                    <TableCell>Balance</TableCell>
                    <TableCell>Currency</TableCell>
                    <TableCell>Top up</TableCell>
                    <TableCell>Withdraw</TableCell>
                    <TableCell>Delete</TableCell>
                </TableRow>
            </TableHead>
            <TableBody>
                {accounts && accounts.length > 0 ? accounts.map((account) => (
                    <TableRow key={account.id}>
                        <TableCell>{account.number}</TableCell>
                        <TableCell>{account.balance}</TableCell>
                        <TableCell>{account.currency}</TableCell>
                        <TableCell>
                            <IconButton aria-label="top up account">
                                <EvStationIcon/>
                            </IconButton>
                        </TableCell>
                        <TableCell>
                            <IconButton aria-label="Withdraw">
                                <ShoppingCartIcon/>
                            </IconButton>
                        </TableCell>
                        <TableCell>
                            <IconButton aria-label="delete" onClick={() => dispatch(deleteAccount({customerId, id:account.id}))}>
                                <DeleteOutlineIcon/>
                            </IconButton>
                        </TableCell>
                    </TableRow>
                )) : null}
            </TableBody>
        </Table>
    );
};

export default List;
