import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {status} from "../status";
import Api from "../../services/Api";


export const fetchCustomers = createAsyncThunk('customers/fetchCustomers', async () => {
    const response = await Api.sendRequest('/customers', 'GET');
    return response.data;
});
export const deleteCustomer = createAsyncThunk('customers/deleteCustomerById', async (id) => {
    const response = await Api.sendRequest(`/customers/${id}`, 'DELETE');
    return {response: response.data, id: id}
});
export const addCustomer = createAsyncThunk('customers/addCustomer', async (customer) => {
    const response = await Api.sendRequest(`/customers`, 'POST', customer);
    return response.data;
});
export const updateCustomer = createAsyncThunk('customers/updateCustomer', async (customer) => {
    const response = await Api.sendRequest(`/customers/${customer.id}`, 'PUT', customer);
    return response.data;
});
export const withdraw = createAsyncThunk('accounts/withdraw', async (account) => {
    const {number, amount} = account;
    const response = await Api.sendRequest(`/accounts/withdrawal/${number}`, 'PUT', {amount});
    return response.data;
});
export const topUp = createAsyncThunk('accounts/topUp', async (account) => {
    const {number, amount} = account;
    const response = await Api.sendRequest(`/accounts/top-up/${number}`, 'PUT', {amount});
    return response.data;
});
export const transfer = createAsyncThunk('accounts/transfer', async (req) => {
    const {customerId, transferRequest} = req;
    const {sender, receiver, amount} = transferRequest;
    const response = await Api.sendRequest(`/accounts/transfer/${sender}/${receiver}`,
        'PUT', {amount});
    return {account: response.data, customerId, receiver, amount};
});
export const deleteAccount = createAsyncThunk('accounts/delete', async (payload) => {
    const {customerId, id} = payload;
    const response = await Api.sendRequest(`/customers/${customerId}/accounts/${id}`, 'DELETE');
    return {data: response.data, customerId, accountId: id};
});
export const addAccount = createAsyncThunk('accounts/add', async (payload) => {
    const {id, account} = payload;
    const response = await Api.sendRequest(`/customers/${id}/accounts`, 'POST', account);
    return {account: response.data, customerId: id};
});

export const customerSlice = createSlice({
    name: 'customers',
    initialState: {
        status: status.IDLE,
        items: [],
        error: null
    },
    reducers: {
        getAll: state => state.items
    },
    extraReducers: {
        [fetchCustomers.pending]: state => {
            state.status = status.LOADING
        },
        [fetchCustomers.fulfilled]: (state, action) => {
            state.status = status.SUCCEEDED;
            state.items = action.payload;
        },
        [fetchCustomers.rejected]: (state, action) => {
            state.status = status.FAILED;
            state.error = action.error;
        },
        [deleteCustomer.pending]: state => {
            state.status = status.LOADING
        },
        [deleteCustomer.fulfilled]: (state, action) => {
            state.status = status.SUCCEEDED;
            const id = action.payload.id;
            state.items.splice(state.items.findIndex(item => item.id === id), 1);
        },
        [deleteCustomer.rejected]: (state, action) => {
            state.status = status.FAILED;
            state.error = action.error ? action.error.message : 'Something went wrong, try again!';
        },
        [addCustomer.pending]: state => {
            state.status = status.LOADING
        },
        [addCustomer.fulfilled]: (state, action) => {
            state.status = status.SUCCEEDED;
            state.items.unshift(action.payload);
        },
        [addCustomer.rejected]: (state, action) => {
            state.status = status.FAILED;
            state.error = action.error ? action.error.message : 'Something went wrong, try again!';
        },
        [updateCustomer.pending]: state => {
            state.status = status.LOADING
        },
        [updateCustomer.fulfilled]: (state, action) => {
            state.status = status.SUCCEEDED;
            const customer = action.payload;
            console.log(state);
            const index = state.items.findIndex(item => item.id === customer.id);
            state.items[index] = customer;
        },
        [updateCustomer.rejected]: (state, action) => {
            state.status = status.FAILED;
            state.error = action.error ? action.error.message : 'Something went wrong, try again!';
        },
        [withdraw.pending]: state => {
            state.status = status.LOADING
        },
        [withdraw.fulfilled]: (state, action) => {
            state.status = status.SUCCEEDED;
            const index = state.items.accounts.findIndex(acc => acc.id === action.payload.id);
            state.items.accounts[index] = action.payload;
        },
        [withdraw.rejected]: (state, action) => {
            state.status = status.FAILED;
            state.error = action.error ? action.error.message : 'Something went wrong, try again!';
        },
        [topUp.pending]: state => {
            state.status = status.LOADING
        },
        [topUp.fulfilled]: (state, action) => {
            state.status = status.SUCCEEDED;
            const index = state.items.accounts.findIndex(acc => acc.id === action.payload.id);
            state.items.accounts[index] = action.payload;
        },
        [topUp.rejected]: (state, action) => {
            state.status = status.FAILED;
            state.error = action.error ? action.error.message : 'Something went wrong, try again!';
        },
        [transfer.pending]: state => {
            state.status = status.LOADING
        },
        [transfer.fulfilled]: (state, action) => {
            state.status = status.SUCCEEDED;
            const {account, customerId, receiver, amount} = action.payload;
            const senderIndex = state.items.findIndex(item => item.id === customerId);
            const receiverIndex = state.items.findIndex(item => item.number === receiver);
            state.items[senderIndex].accounts[findIndex(acc => acc.id === account.id)] = account;

        },
        [transfer.rejected]: (state, action) => {
            state.status = status.FAILED;
            state.error = action.error ? action.error.message : 'Something went wrong, try again!';
        },
        [deleteAccount.pending]: state => {
            state.status = status.LOADING
        },
        [deleteAccount.fulfilled]: (state, action) => {
            state.status = status.SUCCEEDED;
            const {customerId, accountId} = action.payload;
            const customerIndex = state.items.findIndex(item => item.id === customerId);
            console.log(customerIndex, customerId);
            state.items[customerIndex].accounts.splice(state.items[customerIndex].accounts.findIndex(acc => acc.id === accountId), 1);
        },
        [deleteAccount.rejected]: (state, action) => {
            state.status = status.FAILED;
            state.error = action.error ? action.error.message : 'Something went wrong, try again!';
        },
        [addAccount.pending]: state => {
            state.status = status.LOADING
        },
        [addAccount.fulfilled]: (state, action) => {
            const {customerId, account} = action.payload;
            const customerIndex = state.items.findIndex(item => item.id === customerId);
            state.status = status.SUCCEEDED;
            state.items[customerIndex].accounts.unshift(account);
        },
        [addAccount.rejected]: (state, action) => {
            state.status = status.FAILED;
            state.error = action.error ? action.error.message : 'Something went wrong, try again!';
        },
    }
});

//actions
export const {getAll} = customerSlice.actions;

//reducer
export default customerSlice.reducer;

//selectors
export const selectAllCustomers = state => state.customers.items;


