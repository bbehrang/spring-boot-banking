import {createAsyncThunk, createSlice} from "@reduxjs/toolkit";
import {status} from "./status";
import Api from "./../services/Api";

export const loginGithub = createAsyncThunk('login/github', async (payload) => {
    console.log("loging", payload);
    const {code, state} = payload;
    const response = await Api.sendRequest(`/auth/github/login`, 'GET');
    return response.data;
});
export const userSlice = createSlice({
    name: 'user',
    initialState: {
        token: null,
        username: '',
        status: status.IDLE,
    },
    reducers: {
        getToken: state => state.token,
        setError : (state, action) => state.error = action.payload
    },
    extraReducers:{
        [loginGithub.pending]: state => {
            state.status = status.LOADING
        },
        [loginGithub.fulfilled]: (state, action) => {
            state.status = status.SUCCEEDED;
            state.items = action.payload;
        },
        [loginGithub.rejected]: (state, action) => {
            state.status = status.FAILED;
            state.error = action.error;
        },
    }
});
export const {setError} = userSlice.actions;

//reducer
export default userSlice.reducer;
