<template>
  <NavigationComponent />
  <LoginForm />
  <img alt="Vue logo" src="./assets/logo.png">
  <HelloWorld msg="Welcome to Your Vue.js App"/>
</template>

<script>
import HelloWorld from './components/HelloWorld.vue'
import NavigationComponent from './components/NavigationComponent.vue';
import LoginForm from './components/LoginForm.vue';

export default {
  name: 'App',
  components: {
    HelloWorld,
    NavigationComponent,
    LoginForm
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
