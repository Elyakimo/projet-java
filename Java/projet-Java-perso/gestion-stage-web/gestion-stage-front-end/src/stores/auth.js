import { ref } from 'vue'

const token = ref(localStorage.getItem('token') || null)

function connecter(nouveauToken) {
    token.value = nouveauToken
    localStorage.setItem('token', nouveauToken)
}

function deconnecter() {
    token.value = null
    localStorage.removeItem('token')
}

function estConnecte() {
    return token.value !== null
}
async function apiFetch(url, options = {}) {
    const headers = {
        ...options.headers,
        ...(token.value ? { 'Authorization': `Bearer ${token.value}` } : {}),
    }

    return fetch(url, {
        ...options,
        headers
    })
}

export {
    token,
    connecter,
    deconnecter,
    estConnecte,
    apiFetch
}