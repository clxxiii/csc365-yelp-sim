<script lang="ts">
  import Spinner from "./Spinner.svelte";

  export let visible = true;

  let dark: HTMLDivElement;
  export const hide = () => {
   dark.style.opacity = "0" 
   setTimeout(() => visible = false, 300)
  }
</script>

{#if visible}
  <div bind:this={dark} class="wrapper">
    <Spinner />
   <div class="text">Loading Recommendations...</div> 
  </div>
{/if}

<style>
  .wrapper {
    transition: 0.3s;
    width: max-content;
    margin: auto;
    margin-top: 10px;
    padding: 10px;
    border-radius: 10px;
    background-color: #bbb;
    display: grid;
    place-items: center;
    align-content: center;
  }

  .text {
    font-size: 36px;
    color: #fff;
  }
</style>
