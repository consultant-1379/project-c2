<template>
  <v-snackbar
      v-model="snackbar" multi-line
  >
    {{ text }}

    <template v-slot:action="{ attrs }">
      <v-btn
          color="red"
          text
          v-bind="attrs"
          @click="snackbar = false"
      >
        Close
      </v-btn>
    </template>
  </v-snackbar>
</template>

<script>
export default {
  name: "SnackBar",
  props:{
    show: {type: Boolean, default: false},
    text: {type: String, default: ""},
  },
  data: () => ({
    snackbar: false,
  }),
  created() {
    this.snackbar = this.show
  },
  watch: {
    show(value) {
      this.snackbar = value
    }
  }

}
</script>

<style scoped>

</style>