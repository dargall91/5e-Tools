import { defineStore } from 'pinia'
import { User } from 'src/models/User'

export const userStore = defineStore({
    id: 'user',
    state: () => ({user: null}),
    actions: {
        login(username: string, password: string) {

        }
    }
})