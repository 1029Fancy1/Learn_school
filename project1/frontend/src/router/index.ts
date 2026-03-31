import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import Home from '../components/Home.vue'
import MajorLibrary from '../components/MajorLibrary.vue'
import QnA from '../components/QnA.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/major-library',
    name: 'MajorLibrary',
    component: MajorLibrary
  },
  {
    path: '/qna',
    name: 'QnA',
    component: QnA
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router