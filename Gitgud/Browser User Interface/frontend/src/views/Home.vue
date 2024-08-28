<template>
  <div class="Home">
    <v-main>
      <DataTable />
    </v-main>
  </div>
</template>

<script>
import DataTable from '../components/DataTable'

export default {
  name: 'Home',
  components: {
    DataTable
  }
}
</script>

<style lang="scss">

</style>
