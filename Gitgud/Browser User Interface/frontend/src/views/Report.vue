<template>
  <div class="Dashboard">
    <v-main class="text-center">
      <v-card class="ma-10">
        <v-card-title>
          <v-spacer />
          <v-menu
              ref="menu"
              v-model="menu"
              :close-on-content-click="false"
              transition="scale-transition"
              offset-y
              max-width="290px"
              min-width="auto"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                  v-model="dateFormatted"
                  label="Date"
                  append-icon="mdi-calendar"
                  v-bind="attrs"
                  dense solo outlined hide-details
                  class="ml-2 shrink"
                  @blur="date = parseDate(dateFormatted)"
                  v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker
                v-model="dates"
                no-title
                range
                @input="menu= false"
            ></v-date-picker>
          </v-menu>

          <v-btn color="primary" class="ml-2" @click="getRepoData">Get Repo data</v-btn>
        </v-card-title>
        <v-card-text >
          <v-row>
            <v-col cols="12" md="6">
              <PieChart v-if="showPieChart" :labels="pieLabels" :data="pieData" />
              <span v-else> No Data to display</span>
            </v-col>
            <v-col cols="12" md="6">
              <PieChart v-if="showPieChart" :labels="pieContrLabel" :data="pieContrData" />
              <span v-else> No Data to display</span>
            </v-col>
            <v-col cols="12" md="6">
              <LineChart v-if="showChart" :labels="labels" :data="data" />
            </v-col>
            <v-col cols="12" md="6" class="mt-7">
              <v-data-table
                  :headers="headers"
                  :items="files"
                   class="elevation-1"
              ></v-data-table>
            </v-col>
          </v-row>

        </v-card-text>
      </v-card>

      <SnackBar :show="apiError.show" :text="apiError.text" />






    </v-main>
  </div>
</template>

<script>
import LineChart from "../components/LineChart";
import PieChart from "../components/PieChart";
import apiService from "@/plugins/axios"
import SnackBar from "../components/SnackBar";

export default {
  name: 'Dashboard',
  components: {
    LineChart,
    PieChart,
    SnackBar,
  },


  data: () => ({
    commits: null,
    labels: [1,2,3,4,5,6,7,8,9,10],
    pieLabels : null,
    avoidableKeys:['contributionsPerContributors'],
    pieContrLabel: null,
    pieContrData:[],
    pieData: [],
    dates: ['2018-03-20', '2018-03-31'],
    dateFormatted: null,
    data: [],
    showChart: false,
    menu:false,
    showPieChart: false,
    apiError: {
      show: false,
      text: "",
      files:null
    },
    headers: [
      {
        text: 'File Name',
        align: 'start',
        sortable: true,
        value: 'file_name',
      },
      {text: 'Hunk Count', value: 'hunk_count'}

    ],
  }),





  created() {
    this.dateFormatted = this.formatDate(this.dates)
    this.getCommits()
    this.getRepoData()
    this.getFiles()
  },

  watch: {
    dates() {
      this.dateFormatted = this.formatDate(this.dates)
    },
  },

  methods: {
    async getCommits() {
      try {
        this.showChart = false
        const response = await apiService.get("commits")
        this.commits  = Object.freeze(response.data)
        let insertions = []
        let deletions = []
        let changes = []
        for(const commit of this.commits) {
          insertions.push(commit.insertions)
          deletions.push(commit.deletions)
          changes.push(commit.files_changed)
        }
        this.data.push(insertions)
        this.data.push(deletions)
        this.data.push(changes)
        this.showChart = true
      } catch (e) {
        console.error(e)
      }
    },
    async getRepoData(){
      this.showPieChart = false
      const params = {
        date1: this.dates && this.dates[0] ? this.dates[0]:null,
        date2: this.dates && this.dates[1] ? this.dates[1]:null,
      };
      try {
        const response = await apiService.get("commits/repoData",  { params })
        if([200, 201].includes(response.status)){
          this.pieData = []
          this.pieLabels = []
          this.pieLabels = Object.keys(response.data).filter((key)=> !this.avoidableKeys.includes(key))
          this.pieLabels.forEach((ele)=> {
            this.pieData.push(response.data[ele])
          })


          this.pieContrLabel = response.data.contributionsPerContributors? Object.keys(response.data.contributionsPerContributors): []
          this.pieContrData = response.data.contributionsPerContributors? Object.values(response.data.contributionsPerContributors): []

          this.showPieChart = true
        } else {
          this.apiError = {
            show: true, text: `${response.status} - ${response.statusText}`
          }
        }

      } catch (e) {
        console.log(e)
        this.apiError = {
          show: true, text: `Request failed`
        }
        console.error(e)
      }
    },
    formatDate(dates) {
      let newDates = []
      if (!dates) return null
      for (const date of dates) {

        newDates.push(this.dateFormat(date))
      }
      return newDates.join(' ~ ')

    },
    dateFormat(date, time = false) {
      const timeString = date.split('T')[1]
      date = date.split('T')[0]

      const [year, month, day] = date.split('-')
      return time ? `${month}/${day}/${year} ${timeString}` : `${month}/${day}/${year}`
    },
    parseDate(date) {
      if (!date) return null

      const [month, day, year] = date.split('/')
      return `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`
    },
    async getFiles() {
      try {

        const fileResponse = await apiService.get("file")
        this.files = fileResponse.data
      } catch (e) {
        console.error(e)
      }
    },

  },
}
</script>

<style lang="scss">
//.Dashboard {

//}
</style>
