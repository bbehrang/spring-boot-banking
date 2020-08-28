import { configureStore } from '@reduxjs/toolkit'
import customerReducer from './customer/slice';
import userReducer from "./userSlice";
export default configureStore({
    reducer: {
        customers: customerReducer,
        user : userReducer
    }
})
