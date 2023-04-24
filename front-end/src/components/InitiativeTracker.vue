<template>
    <ul class="combat mt-2">
      <li v-for="(combatant) in combatants" :key="combatant.id">{{ combatant.id }}. {{ combatant.name }}</li>
    </ul>
</template>

<script lang="ts">
  import { defineComponent } from 'vue'
  import { Combatant } from '@/models/Combatant'
  import agent from '@/api/agent'

  export default defineComponent({
    data() {
      return {
        combatants: [] as Combatant[],
        delay: 1000,
        timer: undefined as number | undefined
      }
    },
    methods: {
      async getCombatants() {
        await agent.combat.getCombatants().then((data) => {
          this.combatants = data;
        });

        console.log("here");

        clearTimeout(this.timer);
        this.timer = setTimeout(() => {
        this.getCombatants();
      }, this.delay)
      }
    },
    async mounted() {
      this.getCombatants();
    }
  })
</script>

<style>
  .combat {
    list-style-type: none;
    padding: 0;
    margin: auto;
    font-size: 3em;
    text-align: left;
    grid-template-columns: repeat(3, 33%);
    display: grid;
    grid-auto-flow: row;
    grid-row-gap: 25px;
  }
</style>