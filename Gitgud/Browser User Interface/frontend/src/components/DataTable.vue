<template>
  <v-container>
    <v-card class="ma-1 elevation-2">
      <v-card-title dense>
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
                dense solo outlined
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
        <v-text-field v-model="email" placeholder="Search by Email" dense solo outlined append-icon="mdi-magnify"
                      class="ml-2"/>
        <v-spacer/>


        <v-btn dense solo primary class="ml-2 mb-7" @click="filterCommits"> Search</v-btn>

      </v-card-title>
      <v-card-text>
        <v-data-table
            v-if="filteredCommits"
            :headers="headers"
            :items="filteredCommits"
            :sort-by="['contributor_email']"
            class="elevation-1"
        >
          <template v-slot:item.contributor_name="{item}">
            <span>{{ getContributorName(item.contributor_email) }}</span>
          </template>
          <template v-slot:item.date_submitted="{item}">
            <span>{{ dateFormat(item.date_submitted) }}</span>
          </template>
        </v-data-table>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import apiService from "@/plugins/axios"

export default {
  name: 'DataTable',

  data: () => ({
    menu: false,
    dates: ['2019-09-10', '2019-09-20'],
    email: null,
    dateFormatted: null,
    filteredCommits: null,

    headers: [
      {
        text: 'Email',
        align: 'start',
        sortable: true,
        value: 'contributor_email',
      },
      {text: 'Name', value: 'contributor_name'},
      {text: 'No. Insertions', value: 'insertions'},
      {text: 'No. Deletions', value: 'deletions'},
      {text: 'Files Changed', value: 'files_changed'},
      {text: 'Submitted Date', value: 'date_submitted', sortable: true},
    ],
    commits: null,
    contributors: null,

  }),


  async created() {
    this.dateFormatted = this.formatDate(this.dates)
    this.getContributors()
    this.getCommits()

  },


  watch: {
    dates() {
      this.dateFormatted = this.formatDate(this.dates)
    },
  },

  methods: {
    getContributorName(email) {
      const contributorArray = this.contributors.find((contributor) => contributor.contributor_email === email)
      return contributorArray?.contributor_name || 'No Name'
    },
    async getContributors() {
      try {

        const contributorResponse = await apiService.get("contributors")
        this.contributors = contributorResponse.data
      } catch (e) {
        console.error(e)
      }
    },


    async getCommits() {
      try {
        const response = await apiService.get("commits")
        this.commits = Object.freeze(response.data)
        this.filteredCommits = response.data
      } catch (e) {
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

    filterCommits() {
      let filteredCommits = this.commits.filter((commit) => {
        let date = commit.date_submitted.split('T')
        let startDate = this.dates && this.dates[0] ? this.dates[0] : null
        let endDate = this.dates && this.dates[1] ? this.dates[1] : null

        if (this.email && commit.contributor_email.indexOf(this.email) === -1)
          return false
        if (startDate && startDate > date)
          return false
        if (endDate && endDate < date)
          return false
        else return commit
      })
      this.filteredCommits = filteredCommits
    }
  },

}
</script>
