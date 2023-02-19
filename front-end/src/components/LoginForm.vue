<template>
    <form class="container container">
        <div class="form-group">
            <label for="username">Username</label>
            <input type="email" class="form-control" id="username" aria-describedby="emailHelp" placeholder="Username">
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <input type="password" class="form-control" id="password" aria-describedby="passwordHelp"  placeholder="Password">
            <small id="passwordHelp" class="form-text text-muted">I don't know how to encypt a password yet, so...</small>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</template>

<script>
    export default {
        name: 'LoginForm'
    }
</script>
