import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import OrganisationView from '../views/OrganisationView.vue'
import StagiaireView from '@/views/StagiaireView.vue'
import TuteurView from '@/views/TuteurView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/about',
      name: 'about',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/AboutView.vue'),
    },
    {
      path: '/organisations',
      name: 'organisations',
      component: OrganisationView,
    },
    {
      path: '/stagiaires',
      name: 'stagiaires',
      component: StagiaireView,
    },
    {
      path: '/tuteurs',
      name: 'tuteurs',
      component: TuteurView,
    },
  ],
})

export default router
