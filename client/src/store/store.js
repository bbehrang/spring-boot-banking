import { configureStore } from '@reduxjs/toolkit'
import customerReducer from './customer/slice';

export default configureStore({
    reducer: {
        customers: customerReducer
    }
})
