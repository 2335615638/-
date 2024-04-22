import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'layoutLayout',
    component: () => import('../components/LayoutLayout.vue'),
    children: [
      {
        path: "/home",
        name: "home",
        component: () => import('../views/HomeView.vue')
      },
      {
        path: "/user",
        name: "user",
        component: () => import('../views/UserView.vue')
      },
      {
        path: "/knowledge",
        name: "knowledge",
        component: () => import('../views/HealthKnowledgeView.vue')
      },
      {
        path: "/userKnowledge",
        name: "userKnowledge",
        component: () => import('../views/UserKnowLedgeView.vue')
      },
      {
        path: "/userKnowledge/detail",
        name: "userKnowledgeDetail",
        component: () => import('../views/UserKnowLedgeDetailView.vue')
      },
      {
        path: "/calculator",
        name: "calculator",
        component: () => import('../views/CalculatorView.vue')
      },
      {
        path: "/userCalculator",
        name: "userCalculator",
        component: () => import('../views/UserCalculatorView.vue')
      },
      {
        path: "/userHealthMeasurements",
        name: "userHealthMeasurements",
        component: () => import('../views/UserHealthMeasurementsView.vue')
      },
      {
        path: "/userHealthMeasurements/doctorSuggest",
        name: "userHealthMeasurementsDoctorSuggest",
        component: () => import('../views/DoctorSuggestView.vue')
      },
      {
        path: "/healthRecords",
        name: "healthRecords",
        component: () => import('../views/HealthRecordsUserView.vue')
      },
      {
        path: "/healthRecords/statistic",
        name: "healthRecordsStatistic",
        component: () => import('../views/HealthRecordStatistic.vue')
      },
      {
        path: "/userDoctor",
        name: "userDoctor",
        component: () => import('../views/UserDoctorView.vue')
      },
      {
        path: "/userDoctor/reply",
        name: "userDoctorReply",
        component: () => import('../views/UserDoctorReplyView.vue')
      },
      {
        path: "/physicalFunctions",
        name: "physicalFunctions",
        component: () => import('../views/PhysicalFunctionsView.vue')
      },
      {
        path: "/note",
        name: "note",
        component: () => import('../views/NoteView.vue')
      },
      
      {
        path: "/doctor/ask",
        name: "doctorAsk",
        component: () => import('../views/DoctorReplyView.vue')
      },
    ]
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/LoginView.vue')
  },{
    path: '*',
    name: 'NotFoundView',
    component: () => import('../views/NotFoundView.vue')
  }
  
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
