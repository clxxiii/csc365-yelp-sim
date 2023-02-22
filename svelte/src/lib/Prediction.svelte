<script lang="ts">
  import {createEventDispatcher} from 'svelte';

  const dispatch = createEventDispatcher();

  export let text: string; 
  export let selected = false;

  let bg = "#5555";
  
  $: bg = selected ? "#9994" : "#5555";

  const mouseenter = () => selected = true;
  const mouseleave = () => selected = false;
</script>

<div 
  on:mouseenter={mouseenter} 
  on:mouseleave={mouseleave} 
  on:click={() => dispatch('selected', {text})}
  on:keydown={(key) => {key.key == "Enter" ? dispatch('selected', {text}) : ""}}
  class="prediction" 
  style="--bg: {bg}">
  {text}
</div>

<style>
  .prediction {
    cursor: pointer;
    width: calc(100% - 4px*2);
    height: 40px;
    font-size: 30px;
    color: white; 
    padding: 4px;
    background: var(--bg);
  }
</style>
