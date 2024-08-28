<template>
  <div>
    <v-overlay
        :value="sidebar"
        z-index="4"
    >
    </v-overlay>
    <v-navigation-drawer v-if="$vuetify.breakpoint.smAndDown" v-model="sidebar" app clipped :style="{ top: $vuetify.application.top + 'px', zIndex: 4 }">
      <v-list>
        <v-list-item v-for="item in items" :key="item.text" :to="item.link">
          <v-list-item-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-action>
          <v-list-item-content>{{ item.text }}</v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar class="AppBar elevation-2" app dark dense clipped-left>
      <span v-show="$vuetify.breakpoint.smAndDown">
          <v-app-bar-nav-icon @click="sidebar = !sidebar"></v-app-bar-nav-icon>
      </span>
      <v-img :src="require('../assets/logo.png')" width="40" max-width="40"/>
      <h2 @click="$router.push({ path: 'home' })">{{ $t('appname') }}</h2>
      <v-spacer></v-spacer>

      <v-toolbar-items v-if="!$vuetify.breakpoint.smAndDown">
        <v-btn text v-for="item in items" :key="item.text" :to="item.link">
          <v-icon left dark>{{ item.icon }}</v-icon>
          {{ item.text }}
        </v-btn>
      </v-toolbar-items>

    </v-app-bar>

  </div>

</template>

<script>

export default {
  name: 'AppBar',

  data: () => ({
    sidebar: false,
    items:
        [{icon: 'mdi-home', text: 'Home', link: '/'},

          {icon: 'mdi-file-chart-outline', text: 'Report', link: '/report'}]
  }),
  mounted() {
    console.log(this.$vuetify.breakpoint)
  },
}
</script>

<style lang="scss">

</style>
