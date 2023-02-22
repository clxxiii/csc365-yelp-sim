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
  <div bind:this={dark} class="dark">
    <Spinner />
   <div class="text">Loading Restaurants...</div> 
  </div>
{/if}

<style>
  .dark {
    transition: 0.3s;
    position: absolute;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    z-index: 2;
    background-color: #aaa;
    display: grid;
    place-items: center;
    align-content: center;
  }

  .text {
    font-size: 36px;
    color: #fff;
  }
</style>
