<script setup>
    import { ref } from 'vue'
    import { useRouter } from 'vue-router'
    import { connecter } from '@/stores/auth'

    const router = useRouter()
    const email = ref('')
    const motDePasse = ref('')
    const erreur = ref('')

    async function seConnecter() {
        erreur.value =''

        const response = await fetch('http://localhost:8080/api/auth/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email: email.value, motDePasse: motDePasse.value })
        })

        if (!response.ok) {
            erreur.value = 'Email ou mot de passe incorrect'
            return
        }

        const data = await response.json()
        connecter(data.token)
        router.push('/stages')
    }
</script>
<template>
    <div class="login">
        <h1>Connexion</h1>
        <form @submit.prevent="seConnecter">
            <input v-model="email" type="email" placeholder="Email" required id="email"/>
            <input v-model="motDePasse" type="password" placeholder="Mot de passe" required id="motDePasse"/>
            <button type="submit">Se connecter</button>
        </form>
        <p v-if="erreur" class="erreur">{{ erreur }}</p>
    </div>
</template>
<style scoped>
.login {
  max-width: 320px;
  margin: 3rem auto 0;
}

.login form {
  flex-direction: column;
}

.erreur {
  color: var(--danger);
  margin-top: 0.75rem;
}
</style>