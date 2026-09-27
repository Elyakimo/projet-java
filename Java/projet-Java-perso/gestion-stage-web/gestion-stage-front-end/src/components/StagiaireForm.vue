<script setup>
    import { ref, watch } from 'vue'

    const props = defineProps({
        stagiaireAModifier: {
            type: Object,
            default: null
        }
    })
    const emit = defineEmits(['created', 'updated'])

    const nom = ref('')
    const prenom = ref('')
    const mailEtudiant = ref('')
    const telephoneEtudiant = ref('')
    const adresseEtudiant = ref('')
    const classe = ref('')

    watch(() => props.stagiaireAModifier, (stagiaire) => {
        if (stagiaire) {
            nom.value = stagiaire.nom
            prenom.value = stagiaire.prenom
            mailEtudiant.value = stagiaire.mailEtudiant
            telephoneEtudiant.value = stagiaire.telephoneEtudiant
            adresseEtudiant.value = stagiaire.adresseEtudiant
            classe.value = stagiaire.classe
        } else {
            nom.value = ''
            prenom.value = ''
            mailEtudiant.value = ''
            telephoneEtudiant.value = ''
            adresseEtudiant.value = ''
            classe.value = ''
        }
    })

    async function soumettreFormulaire() {
        const donnees = {
            nom: nom.value,
            prenom: prenom.value,
            mailEtudiant: mailEtudiant.value,
            telephoneEtudiant: telephoneEtudiant.value,
            adresseEtudiant: adresseEtudiant.value,
            classe: classe.value
        }
    
      if (props.stagiaireAModifier) {
        await fetch(`http://localhost:8080/api/stagiaires/${props.stagiaireAModifier.id}`, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(donnees)
        })
        emit('updated')
    } else {
        await fetch('http://localhost:8080/api/stagiaires', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(donnees)
        })
        emit('created')
      }

        nom.value = ''
        prenom.value = ''
        mailEtudiant.value = ''
        telephoneEtudiant.value = ''
        adresseEtudiant.value = ''
        classe.value = ''
    } 
</script>

<template>
  <form @submit.prevent="soumettreFormulaire">
    <input v-model="nom" placeholder="Nom du stagiaire" required id="nom-etudiant"/>
    <input v-model="prenom" placeholder="Prenom du stagiaire" required id="prenom-etudiant"/>
    <input v-model="mailEtudiant" placeholder="Mail" required id="mail-etudiant"/>
    <input v-model="telephoneEtudiant" placeholder="Téléphone" type="number" required id="telephone-etudiant"/>
    <input v-model="adresseEtudiant" placeholder="Adresse" required id="adresse-etudiant"/>
    <input v-model="classe" placeholder="Classe" required id="classe-etudiant"/>
    <button type="submit">{{ stagiaireAModifier ? 'Modifier' : 'Créer' }}</button>
  </form>
</template>