import axios from "axios"

const apiService = axios.create({
  baseURL: "http://localhost:8080/",
  timeout: 5000
})
apiService.defaults.headers.common['Access-Control-Allow-Origin'] = '*'
export default apiService