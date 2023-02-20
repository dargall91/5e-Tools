<template>
  <NavigationComponent />
  <LoginForm />
</template>

<script>
import NavigationComponent from './components/NavigationComponent.vue';
import LoginForm from './components/LoginForm.vue';

export default {
  name: 'App',
  components: {
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
  color: #2c3e50;
  margin-top: 60px;
}
</style>
