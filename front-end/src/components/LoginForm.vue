<template>
    <div class="container">
        <div v-if="!isLoggedIn" id="loginForm">
            <h2 class="mt-2">Login</h2>

            <CButtonGroup role="group">
                <CFormCheck type="radio" :button="{color: 'dark', variant: 'outline'}" name="loginRegister" @click="loginClicked" id="login" autocomplete="off" label="Login" checked />
                <CFormCheck type="radio" :button="{color: 'dark', variant: 'outline'}" name="loginRegister" @click="registerClicked" id="register" autocomplete="off" label="Register" />
            </CButtonGroup>

            <CForm @submit.prevent="loginRegister">
                <CFormFloating class="mb-3 mt-3">
                    <CFormInput v-model="loginForm.username" required type="username" class="form-control" id="username" placeholder="Username" />
                    <CFormLabel for="username">Username</CFormLabel>
                </CFormFloating>

                <CFormFloating class="mb-3">
                    <CFormInput v-model="loginForm.password" required type="password" class="form-control" id="password" placeholder="Password" />
                    <CFormLabel for="password">Password</CFormLabel>
                </CFormFloating>

                <CFormCheck class="mb-3" label="Remember Me" type="checkbox" name="remember" />

                <CButton color="dark" type="submit" class="btn btn-primary">Submit</CButton>
            </CForm>
        </div>
        <div v-else>
            <h3 class="mt-2">Logged in as {{ user.username }}</h3>

            <CForm @submit.prevent="logout">
                <CButton color="dark" type="submit" class="btn btn-primary">Logout</CButton>
            </CForm>
        </div>
    </div>
</template>

<script>
    import { CFormFloating, CForm, CFormInput, CFormLabel, CFormCheck, CButton, CButtonGroup } from '@coreui/vue'
    import axios from 'axios'

    export default {
        name: "LoginForm",
        components: { CFormFloating, CForm, CFormInput, CFormLabel, CFormCheck, CButton, CButtonGroup },
        data() {
            return {
                login: true,
                loginForm: {
                    username: "",
                    password: ""
                },
                user: {
                    id: 0,
                    username: "",
                    admin: false
                },
                isLoggedIn: false
            }
        },
        methods: {
            loginRegister() {
                if (this.login === true) {
                    axios.post('http://localhost:8080/5eTools/api/user/login', this.loginForm)
                    .then(response => {
                        this.user.id = response.data.id;
                        this.user.username = response.data.username;
                        this.isLoggedIn = true;
                    })
                    .catch(error => console.log(error.message));
                } else {
                    axios.put('http://localhost:8080/5eTools/api/user/register', this.loginForm)
                    .then(response => {
                        this.user.id = response.data.id;
                        this.user.username = response.data.username
                    })
                    .catch(error => console.log(error.message));
                }
                this.isLoggedIn = true;
            },
            logout() {
                this.user = { id: 0, username: "", admin: false };
                this.isLoggedIn = false;
            },
            loginClicked() {
                this.login = true;
            },
            registerClicked() {
                this.login = false;
            }
        }
    }
</script>
