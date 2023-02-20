<template>
    <div class="container">
        <h2 class="mt-2">Login</h2>
        <CForm @submit.prevent="submit" method="POST">
            <CFormFloating class="mb-3 mt-3">
                <CFormInput v-model="loginForm.username" required type="username" class="form-control" id="username" placeholder="Username" />
                <CFormLabel for="username">Username</CFormLabel>
            </CFormFloating>

            <CFormFloating class="mb-3">
                <CFormInput v-model="loginForm.password" required type="password" class="form-control" id="password" placeholder="Password" />
                <CFormLabel for="password">Password</CFormLabel>
            </CFormFloating>

            <CFormCheck class="mb-3" label="Remember Me" type="checkbox" name="remember" />

            <CButton type="submit" class="btn btn-primary">Submit</CButton>
        </CForm>
    </div>
</template>

<script>
    import { CFormFloating, CForm, CFormInput, CFormLabel, CFormCheck, CButton } from '@coreui/vue'
    import axios from 'axios'

    export default {
        name: "LoginForm",
        components: { CFormFloating, CForm, CFormInput, CFormLabel, CFormCheck, CButton },
        data() {
            return {
                loginForm: {
                    username: "",
                    password: ""
                },
                result: {
                    username: "",
                    password: ""
                },

            }
        },
        methods: {
            submit() {
                axios.post('http://localhost:8080/5eTools/api/user/login', this.loginForm)
                .then(response => {
                    this.result.username = response.data.username
                    this.result.password = response.data.password
                })
                .catch(error => console.log(error.message));
            }
        }
    }
</script>
