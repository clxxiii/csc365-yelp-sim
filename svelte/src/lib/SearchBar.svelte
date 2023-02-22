<script lang="ts">
</script>

<label for="
">
<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
  <path stroke-linecap="round" stroke-linejoin="round" d="M21 21l-5.197-5.197m0 0A7.5 7.5 0 105.196 5.196a7.5 7.5 0 0010.607 10.607z" />
</svg>

<input type="text" placeholder="Recommend me a restaurant like...">
</label>

<style>
  label {
    display: grid;
    width: 90%;
    margin: auto;
    margin-top: 30px;
    background-color: white;
    grid-template-columns: 50px calc(100% - 50px);
    place-items: center;
    padding: 4px;
    height: 50px;
  }
  label svg {
    stroke: #ccc;
    /* stroke-width: 3px; */
    height: 40px;
  }
  input {
    width: 100%;
    height: 50px;
    border: 0;
    padding: 0;
    font-family: 'Quicksand';
    font-size: 30px;
    color: #000;
    outline: none;
  }

  input::placeholder {
    color: #ccc;
  }
</style>
