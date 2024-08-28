<template>
  <v-app>
    <app-bar />
    <router-view />
  </v-app>
</template>

<script>
import AppBar from "./components/AppBar";
export default {
  name: 'App',
  components: {
    AppBar,
  }
}
</script>

<style lang="scss">
html { // vuetify reset
  overflow-y: auto !important;
  overflow-x: auto !important;
}
</style>
