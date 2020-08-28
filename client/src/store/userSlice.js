import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {status} from "./status";
import Api from "./../services/Api";

export const login = createAsyncThunk('login', async (payload) => {
    const response = await Api.sendRequest(`auth/login`, 'POST', payload);
    return response.data;
});
export const userSlice = createSlice({
    name: 'user',
    initialState: {
        token: 'eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbWFpbDEiLCJleHAiOjE1OTk0Mjg4OTF9.Y7eLq9cImK-tas4IWzke5VrblNU_L4vj09to7DpO9ZJ9dFf516VqekQQ770Xs54Xh9v62XE1CKzrntY-xDJTFA',
        status: status.IDLE,
    },
    reducers: {
        getToken: state => state.token,
        setError : (state, action) => state.error = action.payload
    },
    extraReducers:{
        [login.pending]: state => {
            state.status = status.LOADING
        },
        [login.fulfilled]: (state, action) => {
            state.status = status.SUCCEEDED;
            state.token = action.payload;
        },
        [login.rejected]: (state, action) => {
            state.status = status.FAILED;
            state.error = action.error;
        },
    }
});
export const {setError} = userSlice.actions;

//reducer
export default userSlice.reducer;
