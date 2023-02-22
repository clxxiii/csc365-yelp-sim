<script lang="ts">
  export let width = 100
  export let thickness = 7
</script>

<div class="spinner" style="--width: {width}px; --thickness: {thickness}px">
  <div class="out">
    <div class="square"></div>
    <div class="in"></div>
  </div>
</div>

<style>
  .spinner {
    width: var(--width);
    height: var(--width);
    border-radius: 9999px;
    overflow: hidden;
    animation: spin 1s linear infinite; 
  }
  .out {
    width: 100%;
    height: 100%;
    background-color: #fff;
    position: relative;
    display: grid;
    border-radius: 9999px;
    place-items: center;
    z-index: 0;
  }
  .in {
    position: absolute;
    inset: var(--thickness);
    border-radius: 9999px;
    background-color: #aaa;
    z-index: 2;
  }
  .square {
    position: absolute;
    bottom: 50%;
    left: 50%;
    width: calc(var(--width) / 2);
    height: calc(var(--width) / 2);
    background-color: #aaa;
    z-index: 3;
  }
  @keyframes spin {
    from {
      transform: rotate(0deg);
    }
    to {
      transform: rotate(360deg);
    }
  }
</style>
