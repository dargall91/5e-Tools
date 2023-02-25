import { LoginRegisterRequest } from '@/models/LoginRegisterRequest';
import { defineStore } from 'pinia'
import { User } from 'src/models/User'
import agent from '@/api/agent';

export const useUserStore = defineStore({
    id: 'user',
    state: () => ({
        user: null as User | null
    }),
    actions: {
        async login(request: LoginRegisterRequest) {
            await agent.user.login(request).then((data) => {
                this.user = data;
            });
        },
        logout() {
            this.user = null;
        },
        async register(request: LoginRegisterRequest) {
            agent.user.register(request).then((data) => {
                this.user = data;
            });
        },
    },
    persist: true
})